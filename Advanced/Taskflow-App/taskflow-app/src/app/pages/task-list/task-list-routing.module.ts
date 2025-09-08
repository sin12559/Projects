import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TaskListPage } from './task-list.page';

const routes: Routes = [
  {
    path: '',
    component: TaskListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TaskListPageRoutingModule {}
