import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class EditService {

  constructor(private http: HttpClient) { }

  findById(id:any){
    return this.http.get("/customitem"+ id);
  }

  edit(customitem: any){
    return this.http.put("/customitem", customitem,{ responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/customitem/script/download/' + filename, {responseType: 'blob'});
  }

}
