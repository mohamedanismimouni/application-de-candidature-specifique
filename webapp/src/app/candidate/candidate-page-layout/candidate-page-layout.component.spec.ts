import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidatePageLayoutComponent } from './candidate-page-layout.component';

describe('CandidatePageLayoutComponent', () => {
  let component: CandidatePageLayoutComponent;
  let fixture: ComponentFixture<CandidatePageLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidatePageLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidatePageLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
