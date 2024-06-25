import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsefulDocumentWidgetComponent } from './useful-document-widget.component';

describe('UsefulDocumentWidgetComponent', () => {
  let component: UsefulDocumentWidgetComponent;
  let fixture: ComponentFixture<UsefulDocumentWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsefulDocumentWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsefulDocumentWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
