import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ProfileSet} from "./profileset";

@Injectable({
  providedIn: 'root'
})
export class ProfileSetService {

  constructor(private http: HttpClient) { }

  getAllProfileSets(){
    return this.http.get<ProfileSet[]>('/profileset' + '/list');
  }

  getPagedProfileSets(page: number){
    return this.http.get<ProfileSet[]>('/profileset' + '?page=' + page);
  }

  addNewProfileSet(profileSet: ProfileSet){
    return this.http.post("/profileset", profileSet, { responseType: 'text'} );
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/profileset/script/download/' + filename, {responseType: 'blob'});
  }


}
