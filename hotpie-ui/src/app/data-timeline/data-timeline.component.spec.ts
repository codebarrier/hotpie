import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataTimelineComponent } from './data-timeline.component';

describe('DataTimelineComponent', () => {
  let component: DataTimelineComponent;
  let fixture: ComponentFixture<DataTimelineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataTimelineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
