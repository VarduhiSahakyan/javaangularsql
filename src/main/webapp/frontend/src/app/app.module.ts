import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule, routesList} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainComponent} from './layouts/main/main.component';
import {NavbarComponent} from './layouts/navbar/navbar.component';
import {ImageComponent} from './image/image.component';
import {EditImageComponent} from './image/edit/edit-image.component';
import {DeleteImageComponent} from './image/delete/delete-image.component';
import {RuleComponent} from './rule/rule.component';
import {DeleteRuleComponent} from './rule/rule-delete/delete-rule.component';
import {EditRuleComponent} from './rule/rule-edit/edit-rule.component';
import {RuleConditionComponent} from './rulecondition/rule-condition.component';
import {EditConditionComponent} from './rulecondition/conditionedit/edit-condition.component';
import {DeleteConditionComponent} from './rulecondition/conditiondelete/delete-condition.component';
import {CryptoConfigComponent } from "./cryptoconfig/crypto-config.component";
import {AuthentMeanComponent } from './authentmean/authent-mean.component';
import {AuthentMeanDeleteComponent} from './authentmean/delete/authent-mean.delete.component';
import {AuthentMeanEditComponent} from './authentmean/edit/authent-mean.edit.component';
import {CryptoConfigEditComponent} from './cryptoconfig/cryptoconfig-edit/crypto-config-edit.component';
import {CryptoConfigDeleteComponent} from './cryptoconfig/cryptoconfig-delete/crypto-config-delete.component';
import { CustomitemComponent } from './customitem/customitem.component';
import { CustomitemEditComponent } from './customitem/edit/customitem.edit.component';
import { DeleteCustomitemComponent } from './customitem/delete/delete.customitem.component';
import {NgxPaginationModule} from "ngx-pagination";


@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    NavbarComponent,
    routesList,
    ImageComponent,
    EditImageComponent,
    DeleteImageComponent,
    RuleComponent,
    DeleteRuleComponent,
    EditRuleComponent,
    RuleConditionComponent,
    EditConditionComponent,
    DeleteConditionComponent,
    CryptoConfigComponent,
    AuthentMeanComponent,
    AuthentMeanEditComponent,
    AuthentMeanDeleteComponent,
    CryptoConfigEditComponent,
    CryptoConfigDeleteComponent,
    CustomitemComponent,
    CustomitemEditComponent,
    DeleteCustomitemComponent
  ],

  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule,
    Ng2SearchPipeModule,
    FormsModule,
    NgxPaginationModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {
}
