import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProverbWidgetComponent } from './add-proverb-widget.component';

describe('AddProverbWidgetComponent', () => {
  let component: AddProverbWidgetComponent;
  let fixture: ComponentFixture<AddProverbWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProverbWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProverbWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
