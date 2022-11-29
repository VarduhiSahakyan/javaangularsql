import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {AuthentMeanService} from "./authent-mean.service";
import {AuthentMeans} from "./authent-mean";


@Component({
  selector: 'app-authentmean',
  templateUrl: './authent-mean.component.html',
  styleUrls: ['./authent-mean.component.css'],

})
export class AuthentMeanComponent implements OnInit {

  filename: string = "";
  meanForm: FormGroup;
  mean: AuthentMeans = {} as AuthentMeans;
  means: AuthentMeans[] = [] as AuthentMeans[];
  page: number = 1;
  total: number = 1

  constructor(private meanService: AuthentMeanService, private formBuilder: FormBuilder) {
    this.meanForm = this.formBuilder.group({
      createdBy: ['', Validators.required],
      description: ['', Validators.required],
      lastUpdateBy: ['', Validators.required],
      name: ['', Validators.required],
      updateState: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllAuthentMeans();
  }

  getAllAuthentMeans() {
    this.meanService.getPagedAuthentMeans(this.page - 1)
      .subscribe((response: any) => {
        this.means = response.content;
        this.total = response.totalElements;
      });
  }

  pageChangeEvent(event: number) {
    this.page = event;
    this.getAllAuthentMeans();
  }

  addNewAuthentMeans() {
    this.mean = this.meanForm.getRawValue();
    this.meanService.addNewAuthentMeans(this.mean).subscribe(response => {
        this.meanForm.reset();
        this.filename = response;
      },
      error => {
        return (error);
      }
    )
  }

  downloadFile() {
    this.meanService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
