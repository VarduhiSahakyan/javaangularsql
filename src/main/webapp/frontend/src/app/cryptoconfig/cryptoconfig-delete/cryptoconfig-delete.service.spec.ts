import { TestBed } from '@angular/core/testing';

import { CryptoConfigDeleteService } from './crypto-config-delete.service';

describe('CryptoConfigDeleteService', () => {
  let service: CryptoConfigDeleteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CryptoConfigDeleteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
