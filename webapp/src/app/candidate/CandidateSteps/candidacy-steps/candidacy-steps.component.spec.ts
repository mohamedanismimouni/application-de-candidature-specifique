import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidacyStepsComponent } from './candidacy-steps.component';

describe('CandidacyStepsComponent', () => {
  let component: CandidacyStepsComponent;
  let fixture: ComponentFixture<CandidacyStepsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidacyStepsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidacyStepsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
