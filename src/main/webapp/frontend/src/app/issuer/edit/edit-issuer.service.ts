import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Issuer} from "../issuer";

@Injectable({
  providedIn: 'root'
})
export class EditIssuerService {

  constructor(private http: HttpClient) { }

  updateIssuer(issuer: Issuer){
    return this.http.put("/issuers", issuer,{ responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/issuers/script/download/' + filename, {responseType: 'blob'});
  }

}
