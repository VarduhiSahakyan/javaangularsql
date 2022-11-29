import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RuleCondition} from "../rule-condition";

@Injectable({
  providedIn: 'root'
})
export class EditConditionService {

  constructor(private http: HttpClient) { }

  updateRuleCondition(condition: RuleCondition){
    return this.http.put('/condition/', condition, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/condition/script/download/' + filename, {responseType: 'blob'});
  }
}
