import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import { RecordTableComponent } from './record-table/record-table.component';
import { RecordDatepickerComponent } from './record-datepicker/record-datepicker.component';
import { RecordDetailComponent } from './record-detail/record-detail.component';
import { RecordService } from './record.service';
import { MessageComponent } from './message/message.component';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';
import { RecordSearchComponent } from './record-search/record-search.component';

@NgModule({
  declarations: [
    AppComponent,
    RecordTableComponent,
    RecordDatepickerComponent,
    RecordDetailComponent,
    MessageComponent,
    DashboardComponent,
    RecordSearchComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false }
    )
  ],
  providers: [
    RecordService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
