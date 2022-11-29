import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup} from '@angular/forms';
import { saveAs } from 'file-saver';
import { ProfileEditServiceService } from './profile-edit-service.service';
import {Profile} from "../profile";
import {Location} from "@angular/common";
import Swal from "sweetalert2";

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.css']
})
export class ProfileEditComponent implements OnInit {

  fileName: string = "";
  profileEditForm: FormGroup;
  profile: Profile = {} as Profile;
  profileEdited: Profile = {} as Profile;
  isEdited: boolean = false;

  constructor(private service: ProfileEditServiceService, private location: Location, private fb: FormBuilder){

    this.profile = <Profile>this.location.getState();

    this.profileEditForm = this.fb.group({
      id: [this.profile.id],
      createdBy: [this.profile.createdBy],
      description: [this.profile.description],
      lastUpdateBy:  [this.profile.lastUpdateBy],
      name: [this.profile.name],
      updateState:[this.profile.updateState],
      maxAttempts:[this.profile.maxAttempts],
      dataEntryFormat: [this.profile.dataEntryFormat],
      dataEntryAllowedPattern: [this.profile.dataEntryAllowedPattern]
    });
  }

  ngOnInit(): void { }

  editProfile() {

    this.profileEdited.id = this.profileEditForm.value['id'];

    if(this.profile.createdBy != this.profileEditForm.value['createdBy']){
      this.profileEdited.createdBy = this.profileEditForm.value['createdBy'];
      this.isEdited = true;
    }
    if(this.profile.description != this.profileEditForm.value['description']){
      this.profileEdited.description = this.profileEditForm.value['description'];
      this.isEdited = true;
    }
    if(this.profile.lastUpdateBy != this.profileEditForm.value['lastUpdateBy']){
      this.profileEdited.lastUpdateBy = this.profileEditForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if(this.profile.name != this.profileEditForm.value['name']){
      this.profileEdited.name = this.profileEditForm.value['name'];
      this.isEdited = true;
    }
    if(this.profile.updateState != this.profileEditForm.value['updateState']){
      this.profileEdited.updateState = this.profileEditForm.value['updateState'];
      this.isEdited = true;
    }
    if(this.profile.maxAttempts != this.profileEditForm.value['maxAttempts']){
      this.profileEdited.maxAttempts = this.profileEditForm.value['maxAttempts'];
      this.isEdited = true;
    }
    if(this.profile.dataEntryFormat != this.profileEditForm.value['dataEntryFormat']){
      this.profileEdited.dataEntryFormat = this.profileEditForm.value['dataEntryFormat'];
      this.isEdited = true;
    }
    if(this.profile.dataEntryAllowedPattern != this.profileEditForm.value['dataEntryAllowedPattern']){
      this.profileEdited.dataEntryAllowedPattern = this.profileEditForm.value['dataEntryAllowedPattern'];
      this.isEdited = true;
    }

    if (this.isEdited) {
      this.service.updateProfile(this.profileEdited).subscribe(response => {
        this.fileName = response;
      })
    }else {
      Swal.fire('There is no any change to create update script');
    }

    // reset/clear edited values in order to send last edited values next time
    this.isEdited = false;
    this.profileEdited = {} as any;
    this.profile = this.profileEditForm.getRawValue();
  }

  downloadFile(){
    this.service.downloadSqlFile(this.fileName).subscribe(file => saveAs(file,this.fileName));
  }
}
