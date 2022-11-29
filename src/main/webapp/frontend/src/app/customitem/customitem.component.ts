import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {CustomitemService} from "./customitem.service";

@Component({
  selector: 'app-customitem',
  templateUrl: './customitem.component.html',
  styleUrls: ['./customitem.component.css']
})
export class CustomitemComponent implements OnInit {


  customItems: any = [];
  filename: string = "";
  customItem: string = '';
  id!: string | null;
  customItemForm: any;
  validMessage: string = "";
  page: number = 1;
  total: number = 1;

  constructor(
    private customitemService: CustomitemService,
    private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit(): void {
    this.getAllCustomItems();
    this.initializeForm();
  }

  onSubmit() {
    let customItemData = this.customItemForm.value;
    if (this.customItemForm.valid) {
      this.customitemService.sendCustomItemData(customItemData).subscribe(
        response => {
          this.customItemForm.reset();
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

  initializeForm() {
    this.customItemForm = this.formBuilder.group({
      code: ['', Validators.required],
      createdBy: ['', Validators.required],
      name: ['', Validators.required],
      updateState: ['', Validators.required],
      ordinal: ['', Validators.required],
      description: ['', Validators.required],
      creationDate: ['', Validators.required]
    });
  }

  getAllCustomItems() {
    this.customitemService.getAllCustomItems(this.page - 1)
      .subscribe((response: any) => {
        this.customItems = response.content;
        this.total = response.totalElements;
      });
  }

  pageChangeEvent(event: number) {
    this.page = event;
    this.getAllCustomItems();
  }

  downloadFile() {
    this.customitemService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
