import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecordTableComponent } from './record-table/record-table.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RecordDetailComponent } from './record-detail/record-detail.component';

const routes: Routes = [
  { path: 'list', component: RecordTableComponent },
  { path: 'dash', component: DashboardComponent },
  { path: 'detail/:id', component: RecordDetailComponent },
  { path: '', redirectTo: '/dash', pathMatch: 'full' }
];

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forRoot(routes)
  ],
})

export class AppRoutingModule { }
