import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentRequestValidationDialogComponent } from './document-request-validation-dialog.component';

describe('DocumentRequestValidationDialogComponent', () => {
  let component: DocumentRequestValidationDialogComponent;
  let fixture: ComponentFixture<DocumentRequestValidationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentRequestValidationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentRequestValidationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
