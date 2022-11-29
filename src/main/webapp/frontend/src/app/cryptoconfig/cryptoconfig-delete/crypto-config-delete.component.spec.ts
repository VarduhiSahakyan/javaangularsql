import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CryptoConfigDeleteComponent } from './crypto-config-delete.component';

describe('CryptoConfigDeleteComponent', () => {
  let component: CryptoConfigDeleteComponent;
  let fixture: ComponentFixture<CryptoConfigDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CryptoConfigDeleteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CryptoConfigDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
