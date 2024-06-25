import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferSelectionComponent } from './offer-selection.component';

describe('OfferSelectionComponent', () => {
  let component: OfferSelectionComponent;
  let fixture: ComponentFixture<OfferSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
