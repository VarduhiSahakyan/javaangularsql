import {Component, OnInit} from '@angular/core';
import {ImageService} from "./image.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {Image} from "./image";

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.css']
})
export class ImageComponent implements OnInit {

  filename: string = "";
  imageForm: FormGroup;
  image: Image = {} as Image;
  images: Image[] = [] as Image[];
  validMessage: string = "";
  page: number = 1;
  total: number = 1;

  constructor(private imageService: ImageService, private formBuilder: FormBuilder) {
    this.imageForm = this.formBuilder.group({
      id: [0, Validators.required],
      createdBy: ['', Validators.required],
      description: ['', Validators.required],
      lastUpdateBy: ['', Validators.required],
      name: ['', Validators.required],
      updateState: ['', Validators.required],
      binaryData: ['', Validators.required],
      relativePath: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllImages();
  }

  getAllImages() {
    this.imageService.getAllImage(this.page - 1)
      .subscribe((response: any) => {
        this.images = response.content;
        this.total = response.totalElements;
    });
  }

  pageChangeEvent(event: number){
    this.page = event;
    this.getAllImages();
  }

  sendImageData() {
    this.image = this.imageForm.getRawValue();
    if (this.imageForm.valid) {
      this.imageService.addNewImage(this.image).subscribe(
        response => {
          this.imageForm.reset();
          this.filename = response;
          return true;
        },
        error => {
          return (error);
        }
      )
    } else {
      this.validMessage = "Please fill out the form before submitting!";
    }
  }

  downloadFile() {
    this.imageService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
