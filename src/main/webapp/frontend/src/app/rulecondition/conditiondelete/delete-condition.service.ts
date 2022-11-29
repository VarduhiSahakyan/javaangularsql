import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RuleCondition} from "../rule-condition";

@Injectable({
  providedIn: 'root'
})
export class DeleteConditionService {

  constructor(private http: HttpClient) { }

  deleteRuleCondition( id: number, ruleId: number){
    return this.http.delete('/condition/' + id + '/' + ruleId, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/condition/script/download/' + filename, {responseType: 'blob'});
  }

  getById(id: number){
    return this.http.get<RuleCondition>('/condition/' + id);
  }
}
