import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DeleteImageServiceService {
  constructor(private http: HttpClient) {
  }

  deleteImageService(id: any) {
    return this.http.delete("/image/" + id, {responseType: 'text'});
  }

  downloadSqlFile(filename: String) {
    return this.http.get('/image/script/download/' + filename, {responseType: 'blob'});
  }

  getImageById(id: any) {
     return this.http.get('/image/' + id);
  }
}
