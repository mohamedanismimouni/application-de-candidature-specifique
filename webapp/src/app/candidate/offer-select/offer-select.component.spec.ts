import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferSelectComponent } from './offer-select.component';

describe('OfferSelectComponent', () => {
  let component: OfferSelectComponent;
  let fixture: ComponentFixture<OfferSelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferSelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
