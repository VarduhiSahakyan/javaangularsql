import { TestBed } from '@angular/core/testing';

import { CryptoConfigEditService } from './crypto-config-edit.service';

describe('CryptoConfigEditService', () => {
  let service: CryptoConfigEditService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CryptoConfigEditService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
