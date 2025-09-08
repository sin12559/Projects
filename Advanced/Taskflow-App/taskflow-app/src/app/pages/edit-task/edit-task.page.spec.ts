import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditTaskPage } from './edit-task.page';

describe('EditTaskPage', () => {
  let component: EditTaskPage;
  let fixture: ComponentFixture<EditTaskPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTaskPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
