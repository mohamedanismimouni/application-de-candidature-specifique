import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPayrollComponent } from './upload-payroll.component';

describe('UploadPayrollComponent', () => {
  let component: UploadPayrollComponent;
  let fixture: ComponentFixture<UploadPayrollComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadPayrollComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadPayrollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
