import {Component, OnInit} from '@angular/core';
import {DeleteSubIssuerService} from "./delete-sub-issuer.service";
import {ActivatedRoute} from "@angular/router";
import {saveAs} from "file-saver";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-delete',
  templateUrl: './delete-subissuer.component.html',
  styleUrls: ['./delete-subissuer .component.css']
})
export class DeleteSubissuerComponent implements OnInit {

  code: string = "";
  filename: string = "";

  constructor(private service: DeleteSubIssuerService, private router: ActivatedRoute) { }

  ngOnInit(): void {
    this.code = <string>this.router.snapshot.paramMap.get("subIssuerCode");
  }

  deleteSubIssuer() {
    this.service.deleteSubIssuer(this.code).subscribe(response => {
      this.filename = response;
    });
  }

  downloadFile() {
    this.service.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}

