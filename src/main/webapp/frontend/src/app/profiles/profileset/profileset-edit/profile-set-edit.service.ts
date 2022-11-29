import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {ProfileSet} from "../profileset";

@Injectable({
  providedIn: 'root'
})
export class ProfileSetEditService {

  constructor(private http: HttpClient) { }


  getById(id: number){
    return this.http.get("/profileset/" + id);
  }

  editProfileSet(profileSet: ProfileSet){
    return this.http.put("/profileset", profileSet,{ responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get("/profileset/script/download/" + filename, {responseType: 'blob'});
  }

}
