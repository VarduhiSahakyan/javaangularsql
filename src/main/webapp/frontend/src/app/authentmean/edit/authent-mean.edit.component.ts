import {Component, OnInit} from '@angular/core';
import {AuthentMeanEditService} from "./authent-mean-edit.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import * as saveAs from 'file-saver';
import {Location} from "@angular/common";
import {AuthentMeans} from "../authent-mean";
import Swal from "sweetalert2";


@Component({
  selector: 'app-edit',
  templateUrl: './authent-mean.edit.component.html',
  styleUrls: ['./authent-mean.edit.component.css']
})
export class AuthentMeanEditComponent implements OnInit {

  filename: string = "";
  editAuthentMeansForm: FormGroup;
  authentMeans: AuthentMeans = {} as AuthentMeans;
  editedAuthentMeans: AuthentMeans = {} as AuthentMeans;
  isEdited: boolean = false;

  constructor(private editService: AuthentMeanEditService, private location: Location, private formBuilder: FormBuilder) {

    this.authentMeans = <AuthentMeans>this.location.getState();

    this.editAuthentMeansForm = this.formBuilder.group({
      id:[this.authentMeans.id],
      createdBy: [this.authentMeans.createdBy],
      lastUpdateDate:[this.authentMeans.lastUpdateDate],
      creationDate:[this.authentMeans.creationDate],
      description: [this.authentMeans.description],
      lastUpdateBy: [this.authentMeans.lastUpdateBy],
      name: [this.authentMeans.name],
      updateState: [this.authentMeans.updateState]
    });
  }

  ngOnInit(): void { }

  editAuthentMeans(){
    this.editedAuthentMeans.id = this.authentMeans.id;

    if (this.authentMeans.createdBy != this.editAuthentMeansForm.value['createdBy']){
      this.editedAuthentMeans.createdBy = this.editAuthentMeansForm.value['createdBy'];
      this.isEdited = true;
    }
    if (this.authentMeans.creationDate != this.editAuthentMeansForm.value['creationDate']){
      this.editedAuthentMeans.creationDate = this.editAuthentMeansForm.value['creationDate'];
      this.isEdited = true;
    }
    if (this.authentMeans.lastUpdateDate != this.editAuthentMeansForm.value['lastUpdateDate']){
      this.editedAuthentMeans.lastUpdateDate = this.editAuthentMeansForm.value['lastUpdateDate'];
      this.isEdited = true;
    }
    if (this.authentMeans.description != this.editAuthentMeansForm.value['description']){
      this.editedAuthentMeans.description = this.editAuthentMeansForm.value['description'];
      this.isEdited = true;
    }
    if (this.authentMeans.lastUpdateBy != this.editAuthentMeansForm.value['lastUpdateBy']){
      this.editedAuthentMeans.lastUpdateBy = this.editAuthentMeansForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if (this.authentMeans.name != this.editAuthentMeansForm.value['name']){
      this.editedAuthentMeans.name = this.editAuthentMeansForm.value['name'];
      this.isEdited = true;
    }
    if (this.authentMeans.updateState != this.editAuthentMeansForm.value['updateState']){
      this.editedAuthentMeans.updateState = this.editAuthentMeansForm.value['updateState'];
      this.isEdited = true;
    }
    if (this.isEdited) {
      this.editService.updateAuthentMeans(this.editedAuthentMeans).subscribe(response => {
        this.filename = response;
      });
    }
    else {
      Swal.fire('There is no any change to create update script');
    }

    this.isEdited = false;
    this.editedAuthentMeans = {} as AuthentMeans;
    this.authentMeans = this.editAuthentMeansForm.getRawValue();
  }
  downloadFile() {
    this.editService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
