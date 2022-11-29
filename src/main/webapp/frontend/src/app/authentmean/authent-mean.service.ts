import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthentMeans} from "./authent-mean";



@Injectable({
  providedIn: 'root'
})
export class AuthentMeanService {
  constructor(private http: HttpClient) {
  }

  getAllAuthentMeans() {
    return this.http.get<AuthentMeans[]>('/authent/list');
  }

  getPagedAuthentMeans(page: number) {
    return this.http.get<AuthentMeans[]>('/authent?page=' + page);
  }

  addNewAuthentMeans(meansData: AuthentMeans) {
    return this.http.post('/authent', meansData, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/authent/script/download/' + filename, {responseType: 'blob'});
  }
}
