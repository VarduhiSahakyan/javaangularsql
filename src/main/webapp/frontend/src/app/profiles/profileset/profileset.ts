import {Subissuer} from "../../subissuer/subissuer";

export class ProfileSet {

    id: number = 0;
    createdBy: string = '';
    creationDate: Date = new Date();
    description: string = '';
    lastUpdateBy: string = '';
    lastUpdateDate: Date = new Date();
    name: string = '';
    updateState: string = '';
    subIssuerId: number = 0;
    subIssuer: Subissuer = new Subissuer();
}
