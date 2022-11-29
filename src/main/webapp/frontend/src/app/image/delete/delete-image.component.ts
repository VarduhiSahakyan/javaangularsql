import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {DeleteImageServiceService} from "./delete-image.service.service";

@Component({
  selector: 'app-imagedelete',
  templateUrl: './delete-image.component.html',
  styleUrls: ['./delete-image.component.css']
})
export class DeleteImageComponent implements OnInit {

  id: any;
  image: any;
  filename: string = "";
  imageForm: any;

  constructor(
    private service: DeleteImageServiceService,
    private router: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");
    this.initializeForm();
    this.getById(this.id);
  }

  deleteImage() {
    console.log(this.imageForm);
    this.service.deleteImageService(this.id).subscribe(response => {
      this.filename = response;
    });
  }

  downloadFile() {
    this.service.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }


  getById(id: any) {
    this.service.getImageById(id)
      .subscribe(resposne => {
      this.image = resposne;
    });
  }

  initializeForm() {
    this.imageForm = this.formBuilder.group({
      name: ['', Validators.required],
      createdBy: ['', Validators.required],
      id: [0, Validators.required]
    });
  }
}
