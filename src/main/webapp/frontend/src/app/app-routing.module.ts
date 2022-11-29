import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IssuerComponent} from "./issuer/issuer.component";
import {EditIssuerComponent} from "./issuer/edit/edit-issuer.component";
import {DeleteIssuerComponent} from "./issuer/delete/delete-issuer.component";
import {CryptoConfigComponent} from "./cryptoconfig/crypto-config.component";
import {ProfileComponent} from './profiles/profile/profile.component';
import {ProfileSetComponent} from './profiles/profileset/profile-set.component';
import {SubIssuerComponent} from "./subissuer/sub-issuer.component";
import {EditSubIssuerComponent} from "./subissuer/edit/edit-sub-issuer.component";
import {DeleteSubissuerComponent} from "./subissuer/delete/delete-subissuer.component";
import {ImageComponent} from "./image/image.component";
import {EditImageComponent} from "./image/edit/edit-image.component";
import {DeleteImageComponent} from "./image/delete/delete-image.component";
import {RuleComponent} from './rule/rule.component';
import {DeleteRuleComponent} from "./rule/rule-delete/delete-rule.component";
import {EditRuleComponent} from "./rule/rule-edit/edit-rule.component";
import {RuleConditionComponent} from "./rulecondition/rule-condition.component";
import {DeleteConditionComponent} from "./rulecondition/conditiondelete/delete-condition.component";
import {EditConditionComponent} from "./rulecondition/conditionedit/edit-condition.component";
import {ProfileEditComponent} from './profiles/profile/profile-edit/profile-edit.component';
import {ProfileDeleteComponent} from './profiles/profile/profile-delete/profile-delete.component';
import {ProfileSetEditComponent} from './profiles/profileset/profileset-edit/profile-set-edit.component';
import {ProfileSetDeleteComponent} from './profiles/profileset/profileset-delete/profile-set-delete.component';
import {AuthentMeanComponent} from "./authentmean/authent-mean.component";
import {AuthentMeanEditComponent} from "./authentmean/edit/authent-mean.edit.component";
import {AuthentMeanDeleteComponent} from "./authentmean/delete/authent-mean.delete.component";
import {CryptoConfigDeleteComponent} from './cryptoconfig/cryptoconfig-delete/crypto-config-delete.component';
import {CryptoConfigEditComponent} from './cryptoconfig/cryptoconfig-edit/crypto-config-edit.component';
import {InitialScriptComponent} from "./initial/initial-script.component";
import {CustomitemComponent} from "./customitem/customitem.component";
import {CustomitemEditComponent} from "./customitem/edit/customitem.edit.component";
import {DeleteCustomitemComponent} from "./customitem/delete/delete.customitem.component";

const routes: Routes = [
  {path: '', component: IssuerComponent},
  {path: 'issuer/edit', component: EditIssuerComponent},
  {path: 'issuer/:issuerCode/delete', component: DeleteIssuerComponent},
  {path: 'subissuer', component: SubIssuerComponent},
  {path: 'subissuer/edit', component: EditSubIssuerComponent},
  {path: 'subissuer/:subIssuerCode/delete', component: DeleteSubissuerComponent},
  {path: 'image', component: ImageComponent},
  {path: 'image/edit', component: EditImageComponent},
  {path: 'image/delete/:id', component: DeleteImageComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'profile/edit', component: ProfileEditComponent},
  {path: 'profile/delete/:profileId', component: ProfileDeleteComponent},
  {path: 'profileset', component: ProfileSetComponent},
  {path: 'profileset/edit', component: ProfileSetEditComponent},
  {path: 'profileset/:id/delete', component: ProfileSetDeleteComponent},
  {path: 'crypto/:id/delete', component: CryptoConfigDeleteComponent},
  {path: 'crypto/edit', component: CryptoConfigEditComponent},
  {path: 'crypto', component: CryptoConfigComponent},
  {path: 'authent', component: AuthentMeanComponent},
  {path: 'authent/edit', component: AuthentMeanEditComponent},
  {path: 'authent/delete/:id', component: AuthentMeanDeleteComponent},
  {path: 'rule', component: RuleComponent},
  {path: 'rule/delete/:id', component: DeleteRuleComponent},
  {path: 'rule/edit', component: EditRuleComponent},
  {path: 'condition', component: RuleConditionComponent},
  {path: 'condition/delete/:id/:ruleId', component: DeleteConditionComponent},
  {path: 'condition/edit', component: EditConditionComponent},
  {path: 'script/sql/initial', component: InitialScriptComponent},
  {path: 'script/sql/initial', component: InitialScriptComponent},
  {path: 'cusomitem', component: CustomitemComponent},
  {path: 'customitem/edit', component: CustomitemEditComponent},
  {path: 'customitem/:id/delete', component: DeleteCustomitemComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}

export const routesList = [
  IssuerComponent, EditIssuerComponent, DeleteIssuerComponent,
  CryptoConfigComponent,  InitialScriptComponent,
  AuthentMeanComponent, AuthentMeanEditComponent, AuthentMeanDeleteComponent,
  SubIssuerComponent, EditSubIssuerComponent, DeleteSubissuerComponent,
  ImageComponent, EditImageComponent, DeleteImageComponent,
  RuleComponent, DeleteRuleComponent, EditRuleComponent,
  RuleConditionComponent, DeleteConditionComponent, EditConditionComponent,
  ProfileComponent, ProfileEditComponent, ProfileDeleteComponent,
  ProfileSetComponent, ProfileSetEditComponent, ProfileSetDeleteComponent,
  CustomitemComponent,CustomitemEditComponent, DeleteCustomitemComponent
];


