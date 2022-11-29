import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Image} from "../image";

@Injectable({
  providedIn: 'root'
})
export class EditImageService {

  constructor(private  http: HttpClient) {
  }

  updateImage(image: Image) {
    return this.http.put('/image/', image, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/image/script/download/' + filename, {responseType: 'blob'});
  }
}
