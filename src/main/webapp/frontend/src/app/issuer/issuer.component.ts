import { Component, OnInit } from '@angular/core';
import {IssuerService} from "./issuer.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {Issuer} from "./issuer";

@Component({
  selector: 'app-issuer',
  templateUrl: './issuer.component.html',
  styleUrls: ['./issuer.component.css']
})
export class IssuerComponent implements OnInit {

  filename: string ="";
  issuerForm: FormGroup;
  issuer: Issuer = {} as Issuer;
  issuers: Issuer[] = [] as Issuer[];
  page: number = 1;
  total: number = 1;

  constructor(private issuerService: IssuerService, private formBuilder: FormBuilder) {
    this.issuerForm = this.formBuilder.group({
      code: ['', Validators.required],
      createdBy: ['', Validators.required],
      name: ['', Validators.required],
      updateState:['',Validators.required],
      label:['',Validators.required],
      availaibleAuthentMeans: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllIssuer();
  }

  getAllIssuer(){
    this.issuerService.getAllIssuers(this.page - 1)
      .subscribe((response: any) => {
      this.issuers = response.content;
      this.total = response.totalElements;
    });
  }

  pageChangeEvent(event: number){
    this.page = event;
    this.getAllIssuer();
  }

  addNewIssuer(){
    this.issuer = this.issuerForm.getRawValue();
    this.issuerService.addNewIssuer(this.issuer).subscribe(response => {
        this.issuerForm.reset();
        this.filename = response;
      },
      error => {
        return (error);
      }
    )
  }

  downloadFile() {
    this.issuerService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
