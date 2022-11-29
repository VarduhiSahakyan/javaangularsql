import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { saveAs } from 'file-saver';
import { CryptoConfigEditService } from './crypto-config-edit.service';
import {CryptoConfig} from "../crypto-config";
import {Location} from "@angular/common";
import Swal from 'sweetalert2';


@Component({
  selector: 'app-cryptoconfig-edit',
  templateUrl: './crypto-config-edit.component.html',
  styleUrls: ['./crypto-config-edit.component.css']
})
export class CryptoConfigEditComponent implements OnInit {

  filename:string = "";
  cryptoConfigEditForm: FormGroup;
  cryptoConfig: CryptoConfig = {} as CryptoConfig;
  cryptoConfigEdited: CryptoConfig = {} as CryptoConfig;
  isEdited: boolean = false;

  constructor(private cryptoConfigEditService: CryptoConfigEditService, private location: Location, private formBuilder: FormBuilder) {

    this.cryptoConfig = <CryptoConfig>location.getState();

    this.cryptoConfigEditForm = this.formBuilder.group({
      id: [this.cryptoConfig.id],
      description: [this.cryptoConfig.description],
      protocolOne: [this.cryptoConfig.protocolOne],
      protocolTwo: [this.cryptoConfig.protocolTwo]
    });
  }

  ngOnInit(): void { }

  updateCryptoConfig() {

    this.cryptoConfigEdited.id = this.cryptoConfigEditForm.value['id'];

    if(this.cryptoConfig.description != this.cryptoConfigEditForm.value['description']){
      this.cryptoConfigEdited.description = this.cryptoConfigEditForm.value['description'];
      this.isEdited = true;
    }
    if(this.cryptoConfig.protocolOne != this.cryptoConfigEditForm.value['protocolOne']){
      this.cryptoConfigEdited.protocolOne = this.cryptoConfigEditForm.value['protocolOne'];
      this.isEdited = true;
    }
    if(this.cryptoConfig.protocolTwo != this.cryptoConfigEditForm.value['protocolTwo']){
      this.cryptoConfigEdited.protocolTwo = this.cryptoConfigEditForm.value['protocolTwo'];
      this.isEdited = true;
    }

    if (this.isEdited) {
      this.cryptoConfigEditService.updateCryptoConfig(this.cryptoConfigEdited).subscribe(response => {
        this.filename = response;
      });
    }
    else {
      Swal.fire('There is no any change to create update script');
    }

    // reset/clear edited values in order to send last edited values next time
    this.isEdited = false;
    this.cryptoConfigEdited = {} as any;
    this.cryptoConfig = this.cryptoConfigEditForm.getRawValue();
  }

  downloadSqlFile(){
    this.cryptoConfigEditService.downloadFile(this.filename).subscribe(file => saveAs(file,this.filename));
  }
}
