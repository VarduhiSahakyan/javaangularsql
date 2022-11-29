import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthentMeans} from "../authent-mean";

@Injectable({
  providedIn: 'root'
})
export class AuthentMeanEditService {

  constructor(private  http: HttpClient) {
  }

  updateAuthentMeans(mean: AuthentMeans) {
    return this.http.put('/authent/', mean, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/authent/script/download/' + filename, {responseType: 'blob'});
  }
}
