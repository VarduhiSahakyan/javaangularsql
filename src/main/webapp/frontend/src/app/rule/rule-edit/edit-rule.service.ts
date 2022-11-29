import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Rule} from "../rule";

@Injectable({
  providedIn: 'root'
})
export class EditRuleService {

  constructor(private http: HttpClient) { }

  updateRule(rule: Rule){
    return this.http.put('/rule/', rule, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/rule/script/download/' + filename, {responseType: 'blob'});
  }
}
