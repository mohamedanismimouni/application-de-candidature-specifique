import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollaboratorFileComponent } from './collaborator-file.component';

describe('CollaboratorFileComponent', () => {
  let component: CollaboratorFileComponent;
  let fixture: ComponentFixture<CollaboratorFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollaboratorFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollaboratorFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
