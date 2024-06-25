import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollaboratorHomeComponent } from './collaborator-home.component';

describe('CollaboratorHomeComponent', () => {
  let component: CollaboratorHomeComponent;
  let fixture: ComponentFixture<CollaboratorHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollaboratorHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollaboratorHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
