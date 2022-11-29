import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Subissuer} from "../subissuer";

@Injectable({
  providedIn: 'root'
})
export class EditSubIssuerService {

  constructor(private http: HttpClient) {
  }

  editSubIssuer(subissuer: Subissuer) {
    return this.http.put('/subissuer/', subissuer, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/subissuer/script/download/' + filename, {responseType: 'blob'});
  }

}
