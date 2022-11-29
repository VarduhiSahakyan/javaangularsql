import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { saveAs } from 'file-saver';
import { AuthentMeanService } from 'src/app/authentmean/authent-mean.service';
import { IssuerService } from 'src/app/issuer/issuer.service';
import { SubIssuerService } from 'src/app/subissuer/subIssuer.service';
import { ProfileService } from './profile.service';
import {Profile} from "./profile";
import {Issuer} from "../../issuer/issuer";
import {Subissuer} from "../../subissuer/subissuer";
import {AuthentMeans} from "../../authentmean/authent-mean";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  filename: string = "";
  profile: Profile = {} as Profile;
  profileForm: FormGroup;
  issuers: Issuer[] = [] as Issuer[];
  subIssuers: Subissuer[] = [] as Subissuer[];
  authMeans: AuthentMeans[] =[] as AuthentMeans[];
  profiles: Profile[] = [] as Profile[];
  validMessage: string = "";
  page: number = 1;
  total: number = 1;

  constructor(private issuerService: IssuerService, private authentMeansService: AuthentMeanService, private subIssuerService: SubIssuerService,
              private profileService: ProfileService, private formBuilder: FormBuilder) {

    this.profileForm = this.formBuilder.group({
    createdBy: ['', Validators.required],
    description: ['', Validators.required],
    name: ['', Validators.required],
    updateState:['',Validators.required],
    maxAttempts:['',Validators.required],
    dataEntryFormat: ['', Validators.required],
    dataEntryAllowedPattern: ['', Validators.required],
    authentMeansId: ['' ,Validators.required],
    subIssuerId:['',Validators.required]
   });

  }

  ngOnInit(): void {
    this.index();
    this.getPagedProfiles();

  }

  getPagedProfiles(){
    this.profileService.getAllProfile(this.page - 1)
      .subscribe((response: any)=>{
        this.profiles = response.content;
        this.total = response.totalElements;
      });
  }

  pageChangeEvent(event: number){
    this.page = event;
    this.getPagedProfiles();
  }

  addNewProfile(){
    this.profile = this.profileForm.getRawValue();
    if (this.profileForm.valid) {
      this.profileService.addNewProfile(this.profile).subscribe(
        response => {
          this.profileForm.reset();
          this.filename = response;
          return true;
        },
        error => {
          return (error);
        }
      )
    }
  }

  index(){
    this.issuerService.getAllIssuer().subscribe(response => {this.issuers = response});
    this.authentMeansService.getAllAuthentMeans().subscribe(response => {this.authMeans = response});
    this.subIssuerService.getAllSubIssuer().subscribe(response => {this.subIssuers = response});
    this.profileService.getAllProfiles().subscribe(response => {
      this.profiles = response;
    });
  }

  downloadFile(){
    this.profileService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
