import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteIssuerComponent } from './delete-issuer.component';

describe('DeleteSubissuerComponent', () => {
  let component: DeleteIssuerComponent;
  let fixture: ComponentFixture<DeleteIssuerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteIssuerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteIssuerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
