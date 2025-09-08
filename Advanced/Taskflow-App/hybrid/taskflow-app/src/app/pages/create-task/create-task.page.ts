import { Component } from '@angular/core';
import { TaskService } from 'src/app/services/task.service';
import { NavController } from '@ionic/angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.page.html',
  styleUrls: ['./create-task.page.scss'],
  standalone: false
})
export class CreateTaskPage {
  taskForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private navCtrl: NavController
  ) {
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      dueDate: [''],
      priority: ['Medium'],
      status: ['Pending'],
    });
  }

  createTask() {
    if (this.taskForm.valid) {
      this.taskService.createTask(this.taskForm.value).subscribe(() => {
        this.navCtrl.navigateBack('/task-list');
      });
    }
  }
}
