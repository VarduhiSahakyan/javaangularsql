import { Component, OnInit } from '@angular/core';
import {EditIssuerService} from "./edit-issuer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import * as saveAs from 'file-saver';
import {Location} from "@angular/common";
import {Issuer} from "../issuer";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit',
  templateUrl: './edit-issuer.component.html',
  styleUrls: ['./edit-issuer.component.css']
})
export class EditIssuerComponent implements OnInit {

  filename: string = "";
  editIssuerForm: FormGroup;
  issuer: Issuer = {} as Issuer;
  editedIssuer: Issuer = {} as Issuer;
  isEdited: boolean = false;

  constructor(private editService: EditIssuerService, private location: Location, private formBuilder: FormBuilder) {

    this.issuer = <Issuer>this.location.getState();

    this.editIssuerForm = this.formBuilder.group({
      code: [this.issuer.code],
      name: [this.issuer.name],
      label: [this.issuer.label],
      description: [this.issuer.description],
      createdBy: [this.issuer.createdBy],
      creationDate: [this.issuer.creationDate],
      updateState: [this.issuer.updateState],
      availableAuthentMeans: [this.issuer.availaibleAuthentMeans]
    });
  }

  ngOnInit(): void { }

  editIssuer(){
    this.editedIssuer.code = this.issuer.code;

    if(this.issuer.name != this.editIssuerForm.value['name']){
      this.editedIssuer.name = this.editIssuerForm.value['name'];
      this.isEdited = true;
    }
    if(this.issuer.label != this.editIssuerForm.value['label']){
      this.editedIssuer.label = this.editIssuerForm.value['label'];
      this.isEdited = true;
    }
    if(this.issuer.description != this.editIssuerForm.value['description']){
      this.editedIssuer.description = this.editIssuerForm.value['description'];
      this.isEdited = true;
    }
    if(this.issuer.createdBy != this.editIssuerForm.value['createdBy']){
      this.editedIssuer.createdBy = this.editIssuerForm.value['createdBy'];
      this.isEdited = true;
    }
    if(this.issuer.updateState != this.editIssuerForm.value['updateState']){
      this.editedIssuer.updateState = this.editIssuerForm.value['updateState'];
      this.isEdited = true;
    }
    if(this.issuer.availaibleAuthentMeans != this.editIssuerForm.value['availableAuthentMeans']){
      this.editedIssuer.availaibleAuthentMeans = this.editIssuerForm.value['availableAuthentMeans'];
      this.isEdited = true;
    }
    if(this.issuer.creationDate != this.editIssuerForm.value['creationDate']){
      this.editedIssuer.creationDate = this.editIssuerForm.value['creationDate'];
      this.isEdited = true;
    }

    if(this.isEdited) {
      this.editService.updateIssuer(this.editedIssuer).subscribe(response => {
        this.filename = response;
      });
    }
    else {
      Swal.fire('There is no any change to create update script');
    }
    // reset/clear edited values in order to send last edited values next time
    this.isEdited = false;
    this.editedIssuer = {} as Issuer;
    this.issuer = this.editIssuerForm.getRawValue();
  }

  downloadFile() {
    this.editService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
