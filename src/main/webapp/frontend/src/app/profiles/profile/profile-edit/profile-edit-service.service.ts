import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Profile} from "../profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileEditServiceService {

  constructor(private http: HttpClient) { }

  updateProfile(profile: Profile){
    return this.http.put("/profile", profile,{ responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/profile/script/download/' + filename, {responseType: 'blob'});
  }

}
