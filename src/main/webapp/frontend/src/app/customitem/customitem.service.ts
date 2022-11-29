import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CustomitemService {

  constructor(private http: HttpClient) { }

  getAllCustomItems(page: number){
    return  this.http.get('/customitem' + '?page=' + page);
  }

  sendCustomItemData(issuerData:any){
    return this.http.post('/customitem', issuerData, { responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/customitem/script/download/' + filename, {responseType: 'blob'});
  }
}
