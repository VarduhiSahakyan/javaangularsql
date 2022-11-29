import {Component, OnInit} from '@angular/core';
import {EditImageService} from "./edit-image.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import * as saveAs from 'file-saver';
import {Location} from "@angular/common";
import {Image} from "../image";
import Swal from "sweetalert2";

@Component({
  selector: 'app-editimage',
  templateUrl: './edit-image.component.html',
  styleUrls: ['./edit-image.component.css']
})
export class EditImageComponent implements OnInit {

  filename: string = "";
  editImageForm: FormGroup;
  image: Image = {} as Image;
  editImage: Image = {} as Image;
  isEdited: boolean = false;

  constructor(private editService: EditImageService, private location: Location, private formBuilder: FormBuilder) {

    this.image = <Image>this.location.getState();

    this.editImageForm = this.formBuilder.group({
        id: [this.image.id],
        createdBy: [this.image.createdBy],
        creationDate: [this.image.creationDate],
        description: [this.image.description],
        lastUpdateBy: [this.image.lastUpdateBy],
        lastUpdateDate: [this.image.lastUpdateDate],
        name: [this.image.name],
        updateState: [this.image.updateState],
        binaryData: [this.image.binaryData],
        relativePath: [this.image.relativePath]
    });
  }

  ngOnInit(): void { }

  updateImage() {
    this.editImage.id = this.image.id;

    if (this.image.createdBy != this.editImageForm.value['createdBy']){
      this.editImage.createdBy = this.editImageForm.value['createdBy'];
      this.isEdited = true;
    }
    if (this.image.creationDate != this.editImageForm.value['creationDate']){
      this.editImage.creationDate = this.editImageForm.value['creationDate'];
      this.isEdited = true;
    }
    if (this.image.description != this.editImageForm.value['description']) {
      this.editImage.description = this.editImageForm.value['description'];
      this.isEdited = true;
    }
    if (this.image.lastUpdateBy != this.editImageForm.value['lastUpdateBy']){
      this.editImage.lastUpdateBy = this.editImageForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if (this.image.lastUpdateDate != this.editImageForm.value['lastUpdateDate']){
      this.editImage.lastUpdateDate = this.editImageForm.value['lastUpdateDate'];
      this.isEdited = true;
    }
    if (this.image.name != this.editImageForm.value['name']){
      this.editImage.name = this.editImageForm.value['name'];
      this.isEdited = true;
    }
    if (this.image.updateState != this.editImageForm.value['updateState']){
      this.editImage.updateState = this.editImageForm.value['updateState'];
      this.isEdited = true;
    }
    if (this.image.binaryData != this.editImageForm.value['binaryData']){
      this.editImage.binaryData = this.editImageForm.value['binaryData'];
      this.isEdited = true;
    }
    if (this.image.relativePath != this.editImageForm.value['relativePath']){
      this.editImage.relativePath = this.editImageForm.value['relativePath'];
      this.isEdited = true;
    }
    if (this.isEdited){
      this.editService.updateImage(this.editImage).subscribe(response => {
        this.filename = response;
        }
      )
    }
    else {
      Swal.fire('There is no any change to create update script');
    }

    this.isEdited =false;
    this.editImage = {} as Image;
    this.image = this.editImageForm.getRawValue();
  }

  downloadFile() {
    this.editService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
