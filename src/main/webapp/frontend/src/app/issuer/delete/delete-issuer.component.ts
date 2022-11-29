import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DeleteIssuerService} from "./delete-issuer.service";
import {saveAs} from "file-saver";

@Component({
  selector: 'app-delete',
  templateUrl: './delete-issuer.component.html',
  styleUrls: ['./delete-issuer.component.css']
})
export class DeleteIssuerComponent implements OnInit {

  issuerCode: string = "";
  filename: string = "";

  constructor(private deleteService: DeleteIssuerService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.issuerCode = <string>this.route.snapshot.paramMap.get('issuerCode');
  }

  deleteIssuer(){
    this.deleteService.deleteIssuer(this.issuerCode).subscribe(response => {
         this.filename = response;
    });
  }

  downloadFile() {
    this.deleteService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
