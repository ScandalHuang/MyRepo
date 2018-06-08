import { Component, OnInit } from '@angular/core';
import { Record } from '../record';
import { RecordService } from '../record.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-record-table',
  templateUrl: './record-table.component.html',
  styleUrls: ['./record-table.component.css']
})
export class RecordTableComponent implements OnInit {

  records: Record[];

  constructor(
    private recordService: RecordService
  ) { }
  getRecords(): void {
    this.recordService.getRecords()
        .subscribe(records => this.records = records);
  }
  ngOnInit() {
    this.getRecords();
  }

}
