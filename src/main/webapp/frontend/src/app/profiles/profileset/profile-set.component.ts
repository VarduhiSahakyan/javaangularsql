import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {saveAs} from 'file-saver';
import {SubIssuerService} from 'src/app/subissuer/subIssuer.service';
import {ProfileSetService} from "./profile-set.service";
import {ProfileSet} from "./profileset";
import {Subissuer} from "../../subissuer/subissuer";

@Component({
  selector: 'app-profileset',
  templateUrl: './profile-set.component.html',
  styleUrls: ['./profile-set.component.css']
})
export class ProfileSetComponent implements OnInit {

  filename: string = "";
  profileSetForm: FormGroup;
  profileSet: ProfileSet = {} as ProfileSet;
  profileSets: ProfileSet [] = [];
  subIssuers: Subissuer [] = [];
  validMessage: string = "";
  page: number = 1;
  total: number = 1;


  constructor(private profileSetService: ProfileSetService, private subIssuerService: SubIssuerService, private formBuilder: FormBuilder) {
    this.profileSetForm = this.formBuilder.group({
      createdBy: ['', Validators.required],
      description: ['', Validators.required],
      name: ['', Validators.required],
      updateState: ['', Validators.required],
      subIssuerId: [0, Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllProfileSets();
    this.getAllSubIssuers();
  }

  addNewProfileSet() {
    this.profileSet = this.profileSetForm.getRawValue();
    if (this.profileSetForm.valid) {
      this.profileSetService.addNewProfileSet(this.profileSet).subscribe(
        response => {
          this.profileSetForm.reset();
          this.filename = response;
          return true;
        },
        error => {
          return (error);
        }
      )
    } else {
      this.validMessage = "Please fill out the form before submitting!";
    }
  }

  getAllProfileSets() {
    this.profileSetService.getPagedProfileSets(this.page - 1)
      .subscribe((response: any) => {
        this.profileSets = response.content;
        this.total = response.totalElements;
      });
  }

  pageChangeEvent(event: number) {
    this.page = event;
    this.getAllProfileSets();
  }

  getAllSubIssuers() {
    this.subIssuerService.getAllSubIssuer().subscribe(response => {
      this.subIssuers = response
    });
  }

  downloadFile() {
    this.profileSetService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
