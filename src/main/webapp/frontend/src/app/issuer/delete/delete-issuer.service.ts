import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DeleteIssuerService {

  constructor(private http: HttpClient) {
  }

  deleteIssuer(issuerCode: string) {
    return this.http.delete("/issuers/" + issuerCode, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/issuers/script/download/' + filename, {responseType: 'blob'});
  }
}
