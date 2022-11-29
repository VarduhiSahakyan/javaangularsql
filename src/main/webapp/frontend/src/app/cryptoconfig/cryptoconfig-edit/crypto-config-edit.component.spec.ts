import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CryptoConfigEditComponent } from './crypto-config-edit.component';

describe('CryptoConfigEditComponent', () => {
  let component: CryptoConfigEditComponent;
  let fixture: ComponentFixture<CryptoConfigEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CryptoConfigEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CryptoConfigEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
