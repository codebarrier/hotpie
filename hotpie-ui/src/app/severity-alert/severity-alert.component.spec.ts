import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeverityAlertComponent } from './severity-alert.component';

describe('SeverityAlertComponent', () => {
  let component: SeverityAlertComponent;
  let fixture: ComponentFixture<SeverityAlertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeverityAlertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeverityAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
