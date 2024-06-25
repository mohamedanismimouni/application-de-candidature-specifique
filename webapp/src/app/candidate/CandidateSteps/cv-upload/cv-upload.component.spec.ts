import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CvUploadComponent } from './cv-upload.component';

describe('CvUploadComponent', () => {
  let component: CvUploadComponent;
  let fixture: ComponentFixture<CvUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CvUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CvUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
