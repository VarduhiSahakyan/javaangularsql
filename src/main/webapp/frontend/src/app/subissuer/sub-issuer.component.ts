import {Component, OnInit} from '@angular/core';
import {SubIssuerService} from "./subIssuer.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {saveAs} from 'file-saver';
import {IssuerService} from "../issuer/issuer.service";
import {CryptoConfigService} from "../cryptoconfig/crypto-config.service";
import {Subissuer} from "./subissuer";
import {Issuer} from "../issuer/issuer";
import {CryptoConfig} from "../cryptoconfig/crypto-config";

@Component({
  selector: 'app-subissuer',
  templateUrl: './sub-issuer.component.html',
  styleUrls: ['./sub-issuer.component.css']
})
export class SubIssuerComponent implements OnInit {

  filename: string = "";
  subIssuerForm: FormGroup;
  subIssuers: Subissuer[] = [] as Subissuer[];
  subIssuer: Subissuer ={} as Subissuer;
  issuers: Issuer[] = [] as Issuer[];
  cryptoConfigs: CryptoConfig[] = [] as CryptoConfig[];
  validMessage: string = "";
  creationDate = new Date();
  page: number = 1;
  total: number = 1;

  constructor(private subIssuerService: SubIssuerService, private issuerService: IssuerService, private criptoService: CryptoConfigService, private formBuilder: FormBuilder){
    this.subIssuerForm = this.formBuilder.group({
      acsId: ['', Validators.required],
      authenticationTimeout: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      backupLanguages: ['', Validators.required],
      createdBy  : ['', Validators.required],
      description : ['', Validators.required],
      transactionTimeout : ['',  [Validators.required, Validators.pattern("^[0-9]*$")]],
      updateState: ['', Validators.required],
      automaticDeviceSelection: [0, Validators.required],
      userChoiceAllowed: [0, Validators.required],
      backupAllowed: [0, Validators.required],
      defaultDeviceChoice: [0, Validators.required],
      defaultLanguage: ['', Validators.required],
      code: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      codeSvi: ['', Validators.required],
      currencyCode: ['', Validators.required],
      name: ['', Validators.required],
      label: ['', Validators.required],
      authentMeans: ['', Validators.required],
      personnalDataStorage: [0, Validators.required],
      resetBackupsIfSuccess: [0, Validators.required],
      resetChoicesIfSuccess: [0, Validators.required],
      manageBackupsCombinedAmounts: [0, Validators.required],
      manageChoicesCombinedAmounts: [0, Validators.required],
      hubMaintenanceModeEnabled: [0, Validators.required],
      issuerId: ['', Validators.required],
      cryptoConfigId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAllSubIssuer();
    this.getIssuersAndCryptoConfigs();
  }

  getAllSubIssuer() {
    this.subIssuerService.getPagedSubIssuers(this.page - 1)
      .subscribe((response: any) => {
      this.subIssuers = response.content;
      this.total = response.totalElements;
    });
  }

  pageChangeEvent(event: number){
    this.page = event;
    this.getAllSubIssuer();
  }

  getIssuersAndCryptoConfigs(){
    this.issuerService.getAllIssuer().subscribe(response => {
      this.issuers = response;
    });
    this.criptoService.getCryptoConfigList().subscribe(response =>{
      this.cryptoConfigs = response
    });
  }

  addNewSubIssuer() {
    this.subIssuer = this.subIssuerForm.getRawValue();
    if (this.subIssuerForm.valid) {
      this.subIssuerService.addNewSubIssuer(this.subIssuer).subscribe(
        response => {
          this.subIssuerForm.reset();
          this.filename = response;
          return true;
        },
        error => {
          return (error);
        }
      )
    }
  }

  downloadFile() {
    this.subIssuerService.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}

