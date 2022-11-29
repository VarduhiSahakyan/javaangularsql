import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CryptoConfig} from "./crypto-config";

@Injectable({
  providedIn: 'root'
})
export class CryptoConfigService {

  private baseUrl = '/crypto';

  constructor(private http: HttpClient) {
  }

  getCryptoConfigList() {
    return this.http.get<CryptoConfig[]>(this.baseUrl + '/list');
  }

  getPagedCryptoConfigs(page: number) {
    return this.http.get<CryptoConfig[]>(this.baseUrl + '?page=' + page);
  }

  deleteById(id: number) {
    return this.http.delete(this.baseUrl + '/' + `${id}`, {responseType: 'text'});
  }


  addCryptoConfig(cryptoConfigData: CryptoConfig) {
    return this.http.post(this.baseUrl, cryptoConfigData, {responseType: 'text'});
  }

  downloadFile(filename: String) {
    return this.http.get(this.baseUrl + '/download/' + filename, {responseType: 'blob'});
  }
}
