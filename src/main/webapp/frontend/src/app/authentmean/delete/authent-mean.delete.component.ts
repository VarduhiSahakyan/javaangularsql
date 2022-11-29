import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {saveAs} from "file-saver";
import {AuthentMeanDeleteService} from "./authent-mean.delete.service";

@Component({
  selector: 'app-delete',
  templateUrl: './authent-mean.delete.component.html',
  styleUrls: ['./authent-mean.delete.component.css']
})
export class AuthentMeanDeleteComponent implements OnInit {

  id: number = 0;
  filename: string = "";

  constructor(
    private deleteService: AuthentMeanDeleteService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = <number><unknown>this.route.snapshot.paramMap.get('id');
  }

  deleteAuthentMeans() {
    this.deleteService.deleteAuthentMeans(this.id).subscribe(response => {
      this.filename = response;
    });
  }

  downloadFile() {
    this.deleteService.downloadFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
