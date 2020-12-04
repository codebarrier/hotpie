import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataNavigatorComponent } from './data-navigator.component';

describe('DataNavigatorComponent', () => {
  let component: DataNavigatorComponent;
  let fixture: ComponentFixture<DataNavigatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataNavigatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataNavigatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
