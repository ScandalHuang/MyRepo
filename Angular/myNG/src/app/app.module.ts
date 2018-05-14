import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RecordTableComponent } from './record-table/record-table.component';
import { RecordDatepickerComponent } from './record-datepicker/record-datepicker.component';

@NgModule({
  declarations: [
    AppComponent,
    RecordTableComponent,
    RecordDatepickerComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
