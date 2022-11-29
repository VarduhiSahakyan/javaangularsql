import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Subissuer} from "./subissuer";

@Injectable({
  providedIn: 'root'
})
export class SubIssuerService {

  constructor(private http: HttpClient) {
  }

  getPagedSubIssuers(page: number) {
    return this.http.get<Subissuer[]>('/subissuer' + '?page=' + page);
  }

  getAllSubIssuer() {
    return this.http.get<Subissuer[]>('/subissuer' + '/list');
  }

  addNewSubIssuer(subIssuer: Subissuer) {
    return this.http.post('/subissuer', subIssuer, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/subissuer/script/download/' + filename, {responseType: 'blob'});
  }
}

