import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadUsefulDocumentComponent } from './upload-useful-document.component';

describe('UploadUsefulDocumentComponent', () => {
  let component: UploadUsefulDocumentComponent;
  let fixture: ComponentFixture<UploadUsefulDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadUsefulDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadUsefulDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
