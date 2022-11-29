import {Profile} from "../profiles/profile/profile";

export class Rule {

  id: number =0;
  createdBy: string = '';
  creationDate: Date = new Date();
  description: string = '';
  lastUpdateBy: string = '';
  lastUpdateDate: Date = new Date();
  name: string = '';
  updateState: string = '';
  orderRule: number = 0;
  profileId: number = 0;
  profile: Profile = new Profile();

}
