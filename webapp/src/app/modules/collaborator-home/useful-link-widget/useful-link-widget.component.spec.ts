import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsefulLinkWidgetComponent } from './useful-link-widget.component';

describe('UsefulLinkWidgetComponent', () => {
  let component: UsefulLinkWidgetComponent;
  let fixture: ComponentFixture<UsefulLinkWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsefulLinkWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsefulLinkWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
