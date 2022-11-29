import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Profile} from "./profile";


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  getAllProfiles(){
    return this.http.get<Profile[]>('/profile' + '/list');
  }

  getAllProfile(page: number){
    return this.http.get<Profile[]>('/profile' + '?page=' + page);
  }

  addNewProfile(profile:Profile){
    return this.http.post('/profile', profile, { responseType: 'text'} );
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/profile/script/download/' + filename, {responseType: 'blob'});
  }

}
