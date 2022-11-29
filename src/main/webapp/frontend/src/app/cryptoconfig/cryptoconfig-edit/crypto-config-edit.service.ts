import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {CryptoConfig} from "../crypto-config";

@Injectable({
  providedIn: 'root'
})
export class CryptoConfigEditService {

  constructor(private http: HttpClient) { }

  updateCryptoConfig(cryptoConfigData: CryptoConfig) {
    return this.http.put("/crypto", cryptoConfigData, {responseType: 'text'});
  }

  downloadFile(filename: String) {
    return this.http.get("/crypto/download/" + filename, {responseType: 'blob'});
  }
}
