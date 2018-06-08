import { Injectable } from '@angular/core';
import { Record } from './record';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap , map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class RecordService {
  private url = 'api/records';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getRecords(): Observable<Record[]> {
    return this.http.get<Record[]>(this.url)
    .pipe(
      tap(records => this.log(`fetch records`)),
      catchError(this.handleError('getRecords,[]'))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return( error: any): Observable<T> => {
      console.log(error);
      this.log(`@{operation} failed: @{error.message}`);
      return of(result as T);
    };
  }

  getRecord(id: number): Observable<Record> {
    if (id === 0) {
      this.messageService.add(`switch between dash and list!`);
    } else {
      this.messageService.add(`fetch record in ${id}`);
    }
    return this.http.get<Record>(`${this.url}/${id}`)
    .pipe(
      tap(_ => this.log(`fetched record id= ${id}`)),
      catchError(this.handleError<Record>(`getRecord id= ${id}`))
    );
  }

  updateRecord(record: Record): Observable<any> {
    return this.http.put(this.url, record, this.httpOptions)
    .pipe(
      tap(_ => this.log(`updated record id = ${record.id}`)),
      catchError(this.handleError<any>('updateRecord'))
    );
  }

  searchRecords(term: string): Observable<Record[]> {
    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Record[]>(`${this.url}/?employee=${term}`).pipe(
      tap(_ => this.log(`found record march ${term}`)),
      catchError(this.handleError<Record[]>('searchRecord', []))
    );
  }
  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  private log (message: string ) {
    this.messageService.add(`RecordService${message}`);
  }

}
