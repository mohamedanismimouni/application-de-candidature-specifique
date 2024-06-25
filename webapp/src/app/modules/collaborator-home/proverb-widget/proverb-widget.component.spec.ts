import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProverbWidgetComponent } from './proverb-widget.component';

describe('ProverbWidgetComponent', () => {
  let component: ProverbWidgetComponent;
  let fixture: ComponentFixture<ProverbWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProverbWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProverbWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
