import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DeleteService {

  constructor(private http: HttpClient) { }

  deleteCustomItem(customitemId: any){
    return this.http.delete("/customitem/"+customitemId,{ responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/customitem/script/download/' + filename, {responseType: 'blob'});
  }
}
