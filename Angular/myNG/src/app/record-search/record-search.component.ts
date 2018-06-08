import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Record } from '../record';
import { RecordService } from '../record.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-record-search',
  templateUrl: './record-search.component.html',
  styleUrls: ['./record-search.component.css']
})
export class RecordSearchComponent implements OnInit {
  records$: Observable<Record[]>;

  private searchTerms = new Subject<string>();

  search(term: string): void {
    this.searchTerms.next(term);
  }

  constructor(private recordService: RecordService) { }

  ngOnInit(): void {
    this.records$ = this.searchTerms.pipe(
    debounceTime(300),
    distinctUntilChanged(),
    switchMap((term: string) => this.recordService.searchRecords(term)),
    );
    }

}
