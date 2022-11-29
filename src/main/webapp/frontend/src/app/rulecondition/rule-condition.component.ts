import {Component, OnInit} from '@angular/core';
import {RuleConditionService} from "./rule-condition.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {RuleService} from "../rule/rule.service";
import {RuleCondition} from "./rule-condition";
import {Rule} from "../rule/rule";
import {response} from "express";

@Component({
  selector: 'app-rulecondition',
  templateUrl: './rule-condition.component.html',
  styleUrls: ['./rule-condition.component.css']
})
export class RuleConditionComponent implements OnInit {

  filename: string = "";
  ruleConditionForm: FormGroup;
  ruleCondition: RuleCondition = {} as RuleCondition;
  ruleConditions: RuleCondition[] = [] as RuleCondition[];
  rules: Rule[] = [] as Rule[];
  validMessage: string = "";
  page: number = 1;
  total: number = 1;

  constructor(private conditionService: RuleConditionService, private formBuilder: FormBuilder, private ruleService: RuleService) {
    this.ruleConditionForm = this.formBuilder.group({

      id: [0, Validators.required],
      description: ['', Validators.required],
      name: ['', Validators.required],
      updateState: ['', Validators.required],
      ruleId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllRuleConditions()
    this.index();
  }

  getAllRuleConditions(){
    this.conditionService.getPagedRuleConditions(this.page - 1)
      .subscribe((response: any) =>{
      this.ruleConditions = response.content;
      this.total = response.totalElements;
    });
  }

  pageChangeEvent(event:number){
    this.page = event;
    this.getAllRuleConditions();
  }

  addNewRuleCondition(){
    this.ruleCondition = this.ruleConditionForm.getRawValue();
    if (this.ruleConditionForm.valid) {
      this.conditionService.addNewRuleCondition(this.ruleCondition).subscribe(
        response => {
          this.ruleConditionForm.reset();
          this.filename = response;
          return true;
        },
        error => {
          return (error);
        }
      )
    } else {
      this.validMessage = "Please fill out the form before submitting!";
    }
  }

  downloadFile() {
    this.conditionService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }

  index(){
    this.ruleService.getAllRules().subscribe(response => {this.rules = response});
  }
}
