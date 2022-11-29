import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {EditSubIssuerService} from "./edit-sub-issuer.service";
import {saveAs} from "file-saver";
import {Subissuer} from "../subissuer";
import Swal from "sweetalert2";

@Component({
  selector: 'app-edit',
  templateUrl: './edit-subissuer.component.html',
  styleUrls: ['./edit-subissuer.component.css']
})
export class EditSubIssuerComponent implements OnInit {

  filename: string = "";
  subIssuer: Subissuer = {} as Subissuer;
  editedSubIssuer: Subissuer = {} as Subissuer;
  editSubIssuerForm: FormGroup;
  isEdited: boolean = false;

  constructor(private service: EditSubIssuerService, private route: ActivatedRoute, private location: Location, private formBuilder: FormBuilder) {

    this.subIssuer = <Subissuer>this.location.getState();

    this.editSubIssuerForm = this.formBuilder.group({
      code: [this.subIssuer.code],
      name: [this.subIssuer.name],
      label: [this.subIssuer.label],
      acsId: [this.subIssuer.acsId],
      authenticationTimeout: [this.subIssuer.authenticationTimeout],
      backupLanguages: [this.subIssuer.backupLanguages],
      createdBy: [this.subIssuer.createdBy],
      description: [this.subIssuer.description],
      lastUpdateBy: [this.subIssuer.lastUpdateBy],
      transactionTimeout: [this.subIssuer.transactionTimeout],
      updateState: [this.subIssuer.updateState],
      defaultLanguage: [this.subIssuer.defaultLanguage],
      codeSvi: [this.subIssuer.codeSvi],
      currencyCode: [this.subIssuer.currencyCode],
      authentMeans: [this.subIssuer.authentMeans],
      automaticDeviceSelection: [this.subIssuer.automaticDeviceSelection],
      userChoiceAllowed: [this.subIssuer.userChoiceAllowed],
      backupAllowed: [this.subIssuer.backupAllowed],
      defaultDeviceChoice: [this.subIssuer.defaultDeviceChoice],
      personnalDataStorage: [this.subIssuer.personnalDataStorage],
      resetBackupsIfSuccess: [this.subIssuer.resetBackupsIfSuccess],
      resetChoicesIfSuccess: [this.subIssuer.resetChoicesIfSuccess],
      manageBackupsCombinedAmounts: [this.subIssuer.manageBackupsCombinedAmounts],
      manageChoicesCombinedAmounts: [this.subIssuer.manageChoicesCombinedAmounts],
      hubMaintenanceModeEnabled: [this.subIssuer.hubMaintenanceModeEnabled]
    });
  }

  ngOnInit(): void { }

  editSubIssuerData(){

    this.editedSubIssuer.code = this.subIssuer.code;

    if(this.subIssuer.name != this.editSubIssuerForm.value['name']){
      this.editedSubIssuer.name = this.editSubIssuerForm.value['name'];
      this.isEdited = true;
    }
    if(this.subIssuer.label != this.editSubIssuerForm.value['label']){
      this.editedSubIssuer.label = this.editSubIssuerForm.value['label'];
      this.isEdited = true;
    }
    if(this.subIssuer.acsId != this.editSubIssuerForm.value['acsId']){
      this.editedSubIssuer.acsId = this.editSubIssuerForm.value['acsId'];
      this.isEdited = true;
    }
    if(this.subIssuer.authenticationTimeout != this.editSubIssuerForm.value['authenticationTimeout']){
      this.editedSubIssuer.authenticationTimeout = this.editSubIssuerForm.value['authenticationTimeout'];
      this.isEdited = true;
    }
    if(this.subIssuer.backupLanguages != this.editSubIssuerForm.value['backupLanguages']){
      this.editedSubIssuer.backupLanguages = this.editSubIssuerForm.value['backupLanguages'];
      this.isEdited = true;
    }
    if(this.subIssuer.createdBy != this.editSubIssuerForm.value['createdBy']){
      this.editedSubIssuer.createdBy = this.editSubIssuerForm.value['createdBy'];
      this.isEdited = true;
    }
    if(this.subIssuer.description != this.editSubIssuerForm.value['description']){
      this.editedSubIssuer.description = this.editSubIssuerForm.value['description'];
      this.isEdited = true;
    }
    if(this.subIssuer.lastUpdateBy != this.editSubIssuerForm.value['lastUpdateBy']){
      this.editedSubIssuer.lastUpdateBy = this.editSubIssuerForm.value['lastUpdateBy'];
      this.isEdited = true;
    }
    if(this.subIssuer.transactionTimeout != this.editSubIssuerForm.value['transactionTimeout']){
      this.editedSubIssuer.transactionTimeout = this.editSubIssuerForm.value['transactionTimeout'];
      this.isEdited = true;
    }
    if(this.subIssuer.updateState != this.editSubIssuerForm.value['updateState']){
      this.editedSubIssuer.updateState = this.editSubIssuerForm.value['updateState'];
      this.isEdited = true;
    }
    if(this.subIssuer.defaultLanguage != this.editSubIssuerForm.value['defaultLanguage']){
      this.editedSubIssuer.defaultLanguage = this.editSubIssuerForm.value['defaultLanguage'];
      this.isEdited = true;
    }
    if(this.subIssuer.codeSvi != this.editSubIssuerForm.value['codeSvi']){
      this.editedSubIssuer.codeSvi = this.editSubIssuerForm.value['codeSvi'];
      this.isEdited = true;
    }
    if(this.subIssuer.currencyCode != this.editSubIssuerForm.value['currencyCode']){
      this.editedSubIssuer.currencyCode = this.editSubIssuerForm.value['currencyCode'];
      this.isEdited = true;
    }
    if(this.subIssuer.authentMeans != this.editSubIssuerForm.value['authentMeans']){
      this.editedSubIssuer.authentMeans = this.editSubIssuerForm.value['authentMeans'];
      this.isEdited = true;
    }
    if(this.subIssuer.automaticDeviceSelection != this.editSubIssuerForm.value['automaticDeviceSelection']){
      this.editedSubIssuer.automaticDeviceSelection = this.editSubIssuerForm.value['automaticDeviceSelection'];
      this.isEdited = true;
    }
    if(this.subIssuer.userChoiceAllowed != this.editSubIssuerForm.value['userChoiceAllowed']){
      this.editedSubIssuer.userChoiceAllowed = this.editSubIssuerForm.value['userChoiceAllowed'];
      this.isEdited = true;
    }
    if(this.subIssuer.backupAllowed != this.editSubIssuerForm.value['backupAllowed']){
      this.editedSubIssuer.backupAllowed = this.editSubIssuerForm.value['backupAllowed'];
      this.isEdited = true;
    }
    if(this.subIssuer.defaultDeviceChoice != this.editSubIssuerForm.value['defaultDeviceChoice']){
      this.editedSubIssuer.defaultDeviceChoice = this.editSubIssuerForm.value['defaultDeviceChoice'];
      this.isEdited = true;
    }
    if(this.subIssuer.personnalDataStorage != this.editSubIssuerForm.value['personnalDataStorage']){
      this.editedSubIssuer.personnalDataStorage = this.editSubIssuerForm.value['personnalDataStorage'];
      this.isEdited = true;
    }
    if(this.subIssuer.resetBackupsIfSuccess != this.editSubIssuerForm.value['resetBackupsIfSuccess']){
      this.editedSubIssuer.resetBackupsIfSuccess = this.editSubIssuerForm.value['resetBackupsIfSuccess'];
      this.isEdited = true;
    }
    if(this.subIssuer.resetChoicesIfSuccess != this.editSubIssuerForm.value['resetChoicesIfSuccess']){
      this.editedSubIssuer.resetChoicesIfSuccess = this.editSubIssuerForm.value['resetChoicesIfSuccess'];
      this.isEdited = true;
    }
    if(this.subIssuer.manageBackupsCombinedAmounts != this.editSubIssuerForm.value['manageBackupsCombinedAmounts']){
      this.editedSubIssuer.manageBackupsCombinedAmounts = this.editSubIssuerForm.value['manageBackupsCombinedAmounts'];
      this.isEdited = true;
    }
    if(this.subIssuer.manageChoicesCombinedAmounts != this.editSubIssuerForm.value['manageChoicesCombinedAmounts']){
      this.editedSubIssuer.manageChoicesCombinedAmounts = this.editSubIssuerForm.value['manageChoicesCombinedAmounts'];
      this.isEdited = true;
    }
    if(this.subIssuer.hubMaintenanceModeEnabled != this.editSubIssuerForm.value['hubMaintenanceModeEnabled']){
      this.editedSubIssuer.hubMaintenanceModeEnabled = this.editSubIssuerForm.value['hubMaintenanceModeEnabled'];
      this.isEdited = true;
    }

    if (this.isEdited){
    this.service.editSubIssuer(this.editedSubIssuer).subscribe(response => {
      this.filename = response;
    })
    }
    else {
      Swal.fire('There is no any change to create update script');
    }
    // reset/clear edited values in order to send last edited values next time
    this.isEdited = false;
    this.editedSubIssuer = {} as any;
    this.subIssuer = this.editSubIssuerForm.getRawValue();
  }

  downloadFile() {
    this.service.downloadSqlFile(this.filename).subscribe(file => saveAs(file, this.filename));
  }
}
