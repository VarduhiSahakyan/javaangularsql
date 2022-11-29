import {Component, OnInit} from '@angular/core';
import {EditConditionService} from "./edit-condition.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import * as saveAs from 'file-saver';
import {Location} from "@angular/common";
import Swal from 'sweetalert2'
import {RuleCondition} from "../rule-condition";

@Component({
  selector: 'app-conditionedit',
  templateUrl: './edit-condition.component.html',
  styleUrls: ['./edit-condition.component.css']
})
export class EditConditionComponent implements OnInit {

  id: number = 0;
  filename: string = "";
  editRuleConditionForm: FormGroup;
  ruleCondition: RuleCondition = {} as RuleCondition;
  editRuleCondition: RuleCondition = {} as RuleCondition;
  isEdited: boolean =false;

  constructor(private editService: EditConditionService, private location: Location, private formBuilder: FormBuilder) {

    this.ruleCondition = <RuleCondition>this.location.getState();

    this.editRuleConditionForm = this.formBuilder.group({
      id: [this.ruleCondition.id],
      createdBy: [this.ruleCondition.createdBy],
      creationDate: [this.ruleCondition.creationDate],
      description: [this.ruleCondition.description],
      lastUpdateBy: [this.ruleCondition.lastUpdateBy],
      lastUpdateDate: [this.ruleCondition.lastUpdateDate],
      name: [this.ruleCondition.name],
      updateState: [this.ruleCondition.updateState],
      ruleId:[this.ruleCondition.ruleId]
    });
  }

  ngOnInit(): void { }

  updateRuleCondition() {
    this.editRuleCondition.id = this.ruleCondition.id;

    if (this.ruleCondition.createdBy != this.editRuleConditionForm.value['createdBy']){
      this.editRuleCondition.createdBy = this.editRuleConditionForm.value['createdBy'];
      this.isEdited = true;
    }
    if (this.ruleCondition.creationDate != this.editRuleConditionForm.value['creationDate']){
      this.editRuleCondition.creationDate = this.editRuleConditionForm.value['creationDate'];
      this.isEdited =true;
    }
    if (this.ruleCondition.description != this.editRuleConditionForm.value['description']){
      this.editRuleCondition.description = this.editRuleConditionForm.value['description'];
      this.isEdited = true;
    }
    if (this.ruleCondition.lastUpdateBy != this.editRuleConditionForm.value['lastUpdateBy']){
      this.editRuleCondition.lastUpdateBy = this.editRuleConditionForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if (this.ruleCondition.lastUpdateDate != this.editRuleConditionForm.value['lastUpdateDate']){
      this.editRuleCondition.lastUpdateDate = this.editRuleConditionForm.value['lastUpdateDate'];
      this.isEdited =true;
    }
    if (this.ruleCondition.name != this.editRuleConditionForm.value['name']){
      this.editRuleCondition.name = this.editRuleConditionForm.value['name'];
      this.isEdited = true;
    }
    if (this.ruleCondition.updateState != this.editRuleConditionForm.value['updateState']){
      this.editRuleCondition.updateState = this.editRuleConditionForm.value['updateState'];
      this.isEdited =true;
    }

    if (this.ruleCondition.ruleId != this.editRuleConditionForm.value['profileId']){
      this.editRuleCondition.ruleId = this.editRuleConditionForm.value['profileId'];
      this.isEdited = true;
    }
    if (this.isEdited){
      this.editService.updateRuleCondition(this.editRuleCondition).subscribe(response => {
        this.filename = response;
      })
    }
    else {
      Swal.fire('There is no any change to create update script');
    }
    this.isEdited = false;
    this.editRuleCondition = {} as RuleCondition;
    this.ruleCondition = this.editRuleConditionForm.getRawValue();
  }

  downloadFile() {
    this.editService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
