import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProfileDeleteServiceService {

  constructor(private http: HttpClient) { }


  delete(id: any){
    return this.http.delete("/profile/" + id,{ responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/profile/script/download/' + filename, {responseType: 'blob'});
  }


}
