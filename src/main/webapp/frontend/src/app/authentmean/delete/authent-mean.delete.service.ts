import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthentMeanDeleteService {

  constructor(private http: HttpClient) {
  }

  deleteAuthentMeans(id: any) {
    return this.http.delete('/authent/' + id, {responseType: 'text'});
  }


  downloadFile(filename: String) {
    return this.http.get('/authent/script/download/' + filename, {responseType: 'blob'});
  }
}
