import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RuleCondition} from "./rule-condition";

@Injectable({
  providedIn: 'root'
})
export class RuleConditionService {

  constructor(private http: HttpClient) { }

  getAllRuleConditions(){
    return this.http.get<any>('/condition' + 'list');
  }

  getPagedRuleConditions(page: number){
    return this.http.get<any>('/condition'+ '?page=' + page);
  }

  addNewRuleCondition(condition: RuleCondition){
    return this.http.post('/condition', condition, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/condition/script/download/' + filename, {responseType: 'blob'});
  }
}
