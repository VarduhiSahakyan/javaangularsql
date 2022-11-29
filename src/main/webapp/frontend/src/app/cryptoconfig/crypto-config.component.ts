import {Component, OnInit} from '@angular/core';
import {CryptoConfigService} from "./crypto-config.service";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from "file-saver";
import {CryptoConfig} from "./crypto-config";

@Component({
  selector: 'app-crypto-config',
  templateUrl: './crypto-config.component.html',
  styleUrls: ['./crypto-config.component.css'],
  providers: [NgbModalConfig, NgbModal]
})
export class CryptoConfigComponent implements OnInit {

  filename:string = "";
  cryptoConfigForm: FormGroup;
  cryptoConfigs: CryptoConfig[] = [] as CryptoConfig[];
  cryptoConfig: CryptoConfig = {} as CryptoConfig;
  validMessage: string = "";
  page: number = 1;
  total: number = 1;


  constructor(private cryptoConfigService: CryptoConfigService, private formBuilder: FormBuilder) {
    this.cryptoConfigForm = this.formBuilder.group({
      protocolOne: ['', Validators.required],
      protocolTwo: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllCryptoConfigs();
  }

  getAllCryptoConfigs() {
    this.cryptoConfigService.getPagedCryptoConfigs(this.page - 1)
      .subscribe((response: any) => {
      this.cryptoConfigs = response.content;
      this.total = response.totalElements;
    });
  }

  pageChangeEvent(event: number){
    this.page = event;
    this.getAllCryptoConfigs();
  }

  addNewCryptoConfig() {
    this.cryptoConfig = this.cryptoConfigForm.getRawValue();
    if (this.cryptoConfigForm.valid) {
      this.cryptoConfigService.addCryptoConfig(this.cryptoConfig).subscribe(
        response => {
          this.cryptoConfigForm.reset();
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
    this.cryptoConfigService.downloadFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
