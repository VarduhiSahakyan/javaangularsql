import { TestBed } from '@angular/core/testing';
import {DeleteIssuerService} from "./delete-issuer.service";

describe('DeleteRuleService', () => {
  let service: DeleteIssuerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeleteIssuerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
