import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProfilesetDeleteService {

  constructor(private http: HttpClient) { }

  deleteProfileSet(id: number){
      return this.http.delete("/profileset/" + id, { responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get("/profileset/script/download/" + filename, {responseType: 'blob'});
  }
}
