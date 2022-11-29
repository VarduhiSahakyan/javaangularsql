import { Component, OnInit } from '@angular/core';
import {EditService} from "./edit.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import * as saveAs from 'file-saver';
import {Location} from "@angular/common";
import {Customitem} from "../customitem";
import Swal from "sweetalert2";

@Component({
  selector: 'app-edit',
  templateUrl: './customitem.edit.component.html',
  styleUrls: ['./customitem.edit.component.css']
})
export class CustomitemEditComponent implements OnInit {

  filename: string = "";
  customItemEditForm: FormGroup;
  customItem: Customitem = {} as Customitem;
  customItemEdited: Customitem = {} as Customitem;
  isEdited: boolean = false;

  constructor(private editService: EditService, private location: Location, private formBuilder: FormBuilder) {

    this.customItem = <Customitem>this.location.getState();

    this.customItemEditForm = this.formBuilder.group({
      id:[this.customItem.id],
      // code: [this.customItem.code],
      name: [this.customItem.name],
      // label: [this.customItem.label],
      description: [this.customItem.description],
      createdBy: [this.customItem.createdBy],
      creationDate: [this.customItem.creationDate],
      updateState: [this.customItem.updateState],
      value: [this.customItem.value],
      ordinal: [this.customItem.ordinal]
      //, availableAuthentMeans: [this.customItem.availableAuthentMeans]
    });
  }

  ngOnInit(): void { }

  editCustomItem(){
    this.customItemEdited.id = this.customItem.id;

    if(this.customItem.name != this.customItemEditForm.value['name']){
      this.customItemEdited.name = this.customItemEditForm.value['name'];
      this.isEdited= true;
    }
    if(this.customItem.value != this.customItemEditForm.value['value']){
      this.customItemEdited.value = this.customItemEditForm.value['value'];
      this.isEdited= true;
    }
    // if(this.customItem.description != this.customItemEditForm.value['description']){
    //   this.customItemEdited.description = this.customItemEditForm.value['description'];
    // this.isEdited= true;
    // }
    if(this.customItem.createdBy != this.customItemEditForm.value['createdBy']){
      this.customItemEdited.createdBy = this.customItemEditForm.value['createdBy'];
      this.isEdited= true;
    }
    if(this.customItem.updateState != this.customItemEditForm.value['updateState']){
      this.customItemEdited.updateState = this.customItemEditForm.value['updateState'];
      this.isEdited= true;
    }
    if(this.customItem.ordinal != this.customItemEditForm.value['ordinal']){
      this.customItemEdited.ordinal = this.customItemEditForm.value['ordinal'];
      this.isEdited= true;
    }
    if(this.customItem.creationDate != this.customItemEditForm.value['creationDate']){
      this.customItemEdited.creationDate = this.customItemEditForm.value['creationDate'];
      this.isEdited= true;
    }

    if (this.isEdited) {
      this.editService.edit(this.customItemEdited).subscribe(response => {
        this.filename = response;
      })
    }
    else {
      Swal.fire('There is no any change to create update script');
    }
    // reset/clear edited values in order to send last edited values next time
    this.isEdited = false;
    this.customItemEdited = {} as any;
    this.customItem = this.customItemEditForm.getRawValue();
  }

  downloadFile() {
    this.editService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }

}
