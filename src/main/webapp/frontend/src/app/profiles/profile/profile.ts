import {AuthentMeans} from "../../authentmean/authent-mean";
import {Subissuer} from "../../subissuer/subissuer";
import {Issuer} from "../../issuer/issuer";

export class Profile {

  id: number = 0;
  createdBy: string = '';
  creationDate: Date = new Date();
  description: string = '';
  lastUpdateBy: string = '';
  name: string = '';
  updateState: string = '';
  maxAttempts: number = 0;
  dataEntryFormat: string = '';
  dataEntryAllowedPattern: string = '';
  authentMeans: AuthentMeans = new AuthentMeans();
  subIssuer: Subissuer = new Subissuer();
  issuer: Issuer = new Issuer();
  authentMeansId: number = 0;
  subIssuerId: number = 0;
}
