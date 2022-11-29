import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import * as saveAs from 'file-saver';
import { ProfileSet } from '../profileset';
import { ProfileSetEditService } from './profile-set-edit.service';
import {Location} from "@angular/common";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profileset-edit',
  templateUrl: './profile-set-edit.component.html',
  styleUrls: ['./profile-set-edit.component.css']
})
export class ProfileSetEditComponent implements OnInit {

  fileName: string = "";
  profileSetEditForm: FormGroup;
  profileSet: ProfileSet = {} as ProfileSet;
  profileSetEdited: ProfileSet = {} as ProfileSet;
  isEdited: boolean = false;

  constructor(private profileSetEditService: ProfileSetEditService, private location: Location, private fb: FormBuilder) {

    this.profileSet = <ProfileSet>this.location.getState();

    this.profileSetEditForm = this.fb.group({
      id: [this.profileSet.id],
      createdBy: [this.profileSet.createdBy],
      description: [this.profileSet.description],
      lastUpdateBy: [this.profileSet.lastUpdateBy],
      name: [this.profileSet.name],
      updateState: [this.profileSet.updateState]
    });
  }

  ngOnInit(): void {}

  editProfileSet(){

    this.profileSetEdited.id = this.profileSetEditForm.value['id'];

    if(this.profileSet.createdBy != this.profileSetEditForm.value['createdBy']){
      this.profileSetEdited.createdBy = this.profileSetEditForm.value['createdBy'];
      this.isEdited = true;
    }
    if(this.profileSet.description != this.profileSetEditForm.value['description']){
      this.profileSetEdited.description = this.profileSetEditForm.value['description'];
      this.isEdited = true;
    }
    if(this.profileSet.lastUpdateBy != this.profileSetEditForm.value['lastUpdateBy']){
      this.profileSetEdited.lastUpdateBy = this.profileSetEditForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if(this.profileSet.name != this.profileSetEditForm.value['name']){
      this.profileSetEdited.name = this.profileSetEditForm.value['name'];
      this.isEdited = true;
    }
    if(this.profileSet.updateState != this.profileSetEditForm.value['updateState']){
      this.profileSetEdited.updateState = this.profileSetEditForm.value['updateState'];
      this.isEdited = true;
    }

    if (this.isEdited) {
      this.profileSetEditService.editProfileSet(this.profileSetEdited).subscribe(response => {
        this.fileName = response;
      });
    }
    else {
      Swal.fire('There is no any change to create update script');
    }
    // reset/clear edited values in order to send last edited values next time
    this.isEdited = false;
    this.profileSetEdited = {} as ProfileSet;
    this.profileSet = this.profileSetEditForm.getRawValue();
  }


  downloadFile(){
    this.profileSetEditService.downloadSqlFile(this.fileName).subscribe(file => saveAs(file,this.fileName));
  }
}
