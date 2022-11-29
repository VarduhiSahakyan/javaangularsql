import { Component, OnInit } from '@angular/core';
import {EditRuleService} from "./edit-rule.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import * as saveAs from 'file-saver';
import {Location} from "@angular/common";
import {Rule} from "../rule";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-rule-edit',
  templateUrl: './edit-rule.component.html',
  styleUrls: ['./edit-rule.component.css']
})
export class EditRuleComponent implements OnInit {

  filename: string = "";
  editRuleForm: FormGroup;
  rule: Rule = {} as Rule;
  editRule: Rule = {} as Rule;
  isEdited: boolean = false;

  constructor(private editService: EditRuleService, private location: Location, private formBuilder: FormBuilder) {

    this.rule = <Rule>this.location.getState();

    this.editRuleForm = this.formBuilder.group({
      id: [this.rule.id],
      createdBy: [this.rule.createdBy],
      creationDate: [this.rule.creationDate],
      description: [this.rule.description],
      lastUpdateBy: [this.rule.lastUpdateBy],
      lastUpdateDate: [this.rule.lastUpdateDate],
      name: [this.rule.name],
      updateState: [this.rule.updateState],
      orderRule: [this.rule.orderRule],
      profileId:[this.rule.profileId]
    });

  }

  ngOnInit(): void { }

  updateRule() {
    this.editRule.id = this.rule.id;

    if (this.rule.createdBy != this.editRuleForm.value['createdBy']){
      this.editRule.createdBy = this.editRuleForm.value['createdBy'];
      this.isEdited = true;
    }
    if (this.rule.creationDate != this.editRuleForm.value['creationDate']){
      this.editRule.creationDate = this.editRuleForm.value['creationDate'];
      this.isEdited =true;
    }
    if (this.rule.description != this.editRuleForm.value['description']){
      this.editRule.description = this.editRuleForm.value['description'];
      this.isEdited = true;
    }
    if (this.rule.lastUpdateBy != this.editRuleForm.value['lastUpdateBy']){
      this.editRule.lastUpdateBy = this.editRuleForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if (this.rule.lastUpdateDate != this.editRuleForm.value['lastUpdateDate']){
      this.editRule.lastUpdateDate = this.editRuleForm.value['lastUpdateDate'];
      this.isEdited =true;
    }
    if (this.rule.name != this.editRuleForm.value['name']){
      this.editRule.name = this.editRuleForm.value['name'];
      this.isEdited = true;
    }
    if (this.rule.updateState != this.editRuleForm.value['updateState']){
      this.editRule.updateState = this.editRuleForm.value['updateState'];
      this.isEdited =true;
    }
    if (this.rule.orderRule != this.editRuleForm.value['orderRule']){
      this.editRule.orderRule = this.editRuleForm.value['orderRule'];
      this.isEdited = true;
    }
    if (this.rule.profileId != this.editRuleForm.value['profileId']){
      this.editRule.profileId = this.editRuleForm.value['profileId'];
      this.isEdited = true;
    }
    if (this.isEdited){
      this.editService.updateRule(this.editRule).subscribe(response => {
        this.filename = response;
      })
    }
    else {
      Swal.fire('There is no any change to create update script');
    }
    this.isEdited = false;
    this.editRule = {} as Rule;
    this.rule = this.editRuleForm.getRawValue();
  }

  downloadFile() {
    this.editService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
