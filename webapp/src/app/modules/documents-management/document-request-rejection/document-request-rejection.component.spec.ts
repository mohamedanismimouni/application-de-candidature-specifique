import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentRequestRejectionComponent } from './document-request-rejection.component';

describe('DocumentRequestRejectionComponent', () => {
  let component: DocumentRequestRejectionComponent;
  let fixture: ComponentFixture<DocumentRequestRejectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentRequestRejectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentRequestRejectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
