import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizHomePageComponent } from './quiz-home-page.component';

describe('QuizHomePageComponent', () => {
  let component: QuizHomePageComponent;
  let fixture: ComponentFixture<QuizHomePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuizHomePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
