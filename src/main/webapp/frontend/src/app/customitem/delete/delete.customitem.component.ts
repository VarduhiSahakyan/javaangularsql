import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DeleteService} from "./delete.service";
import {FormGroup} from "@angular/forms";
import {saveAs} from "file-saver";
import {Customitem} from "../customitem";

@Component({
  selector: 'app-delete',
  templateUrl: './delete.customitem.component.html',
  styleUrls: ['./delete.customitem.component.css']
})
export class DeleteCustomitemComponent implements OnInit {

  customitemId: any;
  filename: string = "";

  constructor(private deleteService: DeleteService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.customitemId = <string>this.route.snapshot.paramMap.get('customitemId');
  }

  deleteCustomItem(){
    this.deleteService.deleteCustomItem(this.customitemId).subscribe(resposne => {
      this.filename = resposne;
    });
  }

  downloadFile() {
    this.deleteService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }

}
