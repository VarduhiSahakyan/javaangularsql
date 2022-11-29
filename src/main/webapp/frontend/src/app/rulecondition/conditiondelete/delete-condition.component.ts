import {Component, OnInit} from "@angular/core";
import {DeleteConditionService} from "./delete-condition.service";
import {ActivatedRoute} from "@angular/router";
import {saveAs} from "file-saver";
import {RuleCondition} from "../rule-condition";

@Component({
  selector: 'app-conditiondelete',
  templateUrl: './delete-condition.component.html',
  styleUrls: ['./delete-condition.component.css']
})
export class DeleteConditionComponent implements OnInit {

  id: number = 0;
  ruleCondition: RuleCondition = {} as RuleCondition;
  filename: string = "";
  ruleId: number = 0;

  constructor(private deleteService: DeleteConditionService, private router: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = Number(this.router.snapshot.paramMap.get("id"));
    this.ruleId = Number(this.router.snapshot.paramMap.get("ruleId"));
    this.getById(this.id);
  }

  deleteRuleCondition() {
    this.deleteService.deleteRuleCondition(this.id, this.ruleId).subscribe(response => {
      this.filename = response
    })
  }

  downloadFile() {
    this.deleteService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }

  getById(id: number){
    this.deleteService.getById(id).subscribe(response =>{
      this.ruleCondition = response
    })
  }
}
