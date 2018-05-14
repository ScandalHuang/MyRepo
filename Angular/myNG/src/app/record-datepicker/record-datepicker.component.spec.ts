import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordDatepickerComponent } from './record-datepicker.component';

describe('RecordDatepickerComponent', () => {
  let component: RecordDatepickerComponent;
  let fixture: ComponentFixture<RecordDatepickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordDatepickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordDatepickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
