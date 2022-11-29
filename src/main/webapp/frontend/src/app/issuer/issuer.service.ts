import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Issuer} from "./issuer";


@Injectable({
  providedIn: 'root'
})
export class IssuerService {

  constructor(private http: HttpClient) { }

  getAllIssuers(page: number){
    return  this.http.get<Issuer[]>('/issuers' + '?page=' + page);
  }

  getAllIssuer(){
    return  this.http.get<Issuer[]>('/issuers' + '/list');
  }

  addNewIssuer(issuer: Issuer){
    return this.http.post('/issuers', issuer, { responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/issuers/script/download/' + filename, {responseType: 'blob'});
  }
}
