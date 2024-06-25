import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelDocumentRequestComponent } from './cancel-document-request.component';

describe('CancelDocumentRequestComponent', () => {
  let component: CancelDocumentRequestComponent;
  let fixture: ComponentFixture<CancelDocumentRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CancelDocumentRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelDocumentRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
