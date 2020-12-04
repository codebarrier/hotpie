import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeverityreportComponent } from './severityreport.component';

describe('SeverityreportComponent', () => {
  let component: SeverityreportComponent;
  let fixture: ComponentFixture<SeverityreportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeverityreportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeverityreportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
