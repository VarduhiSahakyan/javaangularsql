import {Rule} from "../rule/rule";

export class RuleCondition {

  id: number = 0;
  name: string = '';
  createdBy: string = '';
  creationDate: Date = new Date();
  description: string = '';
  lastUpdateBy: string= '';
  lastUpdateDate: Date = new Date();
  updateState: string = '';
  rule: Rule = new Rule();
  ruleId: number = 0;
}
