import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CryptoConfigDeleteService {

  constructor(private http: HttpClient) { }

  deleteById(id: number) {
    return this.http.delete("/crypto/" + id, {responseType: 'text'});
  }

  downloadFile(filename: String) {
    return this.http.get("/crypto/download/" + filename, {responseType: 'blob'});
  }

}
