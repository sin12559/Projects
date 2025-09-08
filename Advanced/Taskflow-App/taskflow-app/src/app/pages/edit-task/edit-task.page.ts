import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TaskService } from 'src/app/services/task.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.page.html',
  styleUrls: ['./edit-task.page.scss'],
  standalone: false
})
export class EditTaskPage implements OnInit {
  taskForm: FormGroup;
  taskId: string = '';

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private taskService: TaskService,
    private navCtrl: NavController
  ) {
    this.taskForm = this.fb.group({
      title: [''],
      description: [''],
      dueDate: [''],
      priority: [''],
      status: [''],
    });
  }

  ngOnInit() {
    this.taskId = this.route.snapshot.paramMap.get('id')!;
    this.taskService.getTasks().subscribe(tasks => {
      const task = tasks.find(t => t._id === this.taskId);
      if (task) this.taskForm.patchValue(task);
    });
  }

  saveTask() {
    this.taskService.updateTask(this.taskId, this.taskForm.value).subscribe(() => {
      this.navCtrl.navigateBack('/task-list');
    });
  }
}
