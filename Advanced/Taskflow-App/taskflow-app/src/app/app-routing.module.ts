import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then(m => m.HomePageModule)
  },
  {
    path: 'task-list',
    loadChildren: () => import('./pages/task-list/task-list.module').then(m => m.TaskListPageModule)
  },
  {
    path: 'create-task',
    loadChildren: () => import('./pages/create-task/create-task.module').then(m => m.CreateTaskPageModule)
  },
  {
    path: 'edit-task/:id',
    loadChildren: () => import('./pages/edit-task/edit-task.module').then(m => m.EditTaskPageModule)
  }
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
