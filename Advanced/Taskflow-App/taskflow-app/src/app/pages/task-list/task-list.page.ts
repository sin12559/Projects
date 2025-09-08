import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { TaskService } from 'src/app/services/task.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.page.html',
  styleUrls: ['./task-list.page.scss'],
  standalone: false
})
export class TaskListPage implements OnInit {
  tasks: any[] = [];

  constructor(private taskService: TaskService, private navCtrl: NavController) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getTasks().subscribe(res => {
      this.tasks = res;
    });
  }

  deleteTask(id: string) {
    this.taskService.deleteTask(id).subscribe(() => {
      this.loadTasks();
    });
  }

  goToEdit(id: string) {
    this.navCtrl.navigateForward(`/edit-task/${id}`);
  }
  getPriorityColor(priority: string): string {
    switch (priority.toLowerCase()) {
      case 'high':
        return 'danger';
      case 'medium':
        return 'warning';
      case 'low':
        return 'success';
      default:
        return 'medium';
    }
  }
  
}

