import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegextesterComponent } from './regextester.component';

describe('RegextesterComponent', () => {
  let component: RegextesterComponent;
  let fixture: ComponentFixture<RegextesterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegextesterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegextesterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
