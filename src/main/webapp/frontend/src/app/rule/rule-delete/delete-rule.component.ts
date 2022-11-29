import { Component, OnInit } from '@angular/core';
import {DeleteRuleService} from "./delete-rule.service";
import {ActivatedRoute} from "@angular/router";
import {saveAs} from "file-saver";
import {Rule} from "../rule";

@Component({
  selector: 'app-delete',
  templateUrl: './delete-rule.component.html',
  styleUrls: ['./delete-rule.component.css']
})
export class DeleteRuleComponent implements OnInit {

  id: number = 0;
  rule: Rule = {} as Rule;
  filename: string = "";

  constructor(private deleteService: DeleteRuleService, private router: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = Number(this.router.snapshot.paramMap.get("id"));
    this.getById(this.id);
  }

  deleteRule(){
    this.deleteService.deleteRule( this.id).subscribe(response =>{
      this.filename = response
    })
  }

  downloadFile() {
    this.deleteService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }

  getById(id: number){
    this.deleteService.getById(id).subscribe(response =>{
      this.rule = response
    })
  }
}
