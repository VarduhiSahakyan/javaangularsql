import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DeleteSubIssuerService {

  constructor(private http: HttpClient) {
  }

  deleteSubIssuer(code: string) {
    return this.http.delete("/subissuer/" + code, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/subissuer/script/download/' + filename, {responseType: 'blob'});
  }

}
