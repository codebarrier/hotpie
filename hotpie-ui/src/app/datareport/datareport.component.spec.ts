import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatareportComponent } from './datareport.component';

describe('DatareportComponent', () => {
  let component: DatareportComponent;
  let fixture: ComponentFixture<DatareportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatareportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatareportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
