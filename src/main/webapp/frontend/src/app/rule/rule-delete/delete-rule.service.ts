
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Rule} from "../rule";

@Injectable({
  providedIn: 'root'
})
export class DeleteRuleService {

  constructor(private http: HttpClient) { }

  deleteRule( id: number){
    return this.http.delete('/rule/' + id , {responseType: 'text'});

  }

  downloadSqlFile(filename: String) {
    return this.http.get('/rule/script/download/' + filename, {responseType: 'blob'});
  }

  getById(id: number){
    return this.http.get<Rule>('/rule/' + id);
  }
}
