import { Component, OnInit } from '@angular/core';
import {Rule} from "./rule";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {Profile} from "../profiles/profile/profile";
import {RuleService} from "./rule.service";
import {ProfileService} from "../profiles/profile/profile.service";

@Component({
  selector: 'app-rule',
  templateUrl: './rule.component.html',
  styleUrls: ['./rule.component.css']
})
export class RuleComponent implements OnInit {

  filename: string = "";
  ruleForm: FormGroup;
  rule: Rule = {} as Rule;
  rules: Rule[] = [] as Rule[];
  profiles: Profile[] = [] as Profile[];
  validMessage: string = "";
  page: number = 1;
  total: number = 1;


  constructor( private ruleService: RuleService, private formBuilder: FormBuilder, private profileService: ProfileService) {
    this.ruleForm = this.formBuilder.group({
      id: [0, Validators.required],
      description: ['', Validators.required],
      name: ['', Validators.required],
      updateState: ['', Validators.required],
      orderRule: ['', Validators.required],
      profileId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllRules();
    this.index();
  }

  getAllRules() {
    this.ruleService.getPagedRules(this.page -1)
      .subscribe((response: any) => {
      this.rules = response.content;
      this.total = response.totalElements;
    });
  }

  pageChangeEvent(event: number){
    this.page = event;
    this.getAllRules();
  }

  addNewRule() {
    this.rule = this.ruleForm.getRawValue();
    if (this.ruleForm.valid) {
      this.ruleService.addNewRule(this.rule).subscribe(
        response => {
          this.ruleForm.reset();
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
    this.ruleService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }

  index(){
    this.profileService.getAllProfiles().subscribe(response => {this.profiles = response});
  }
}
