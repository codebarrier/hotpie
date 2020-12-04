import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationSeverityComponent } from './documentation-severity.component';

describe('DocumentationSeverityComponent', () => {
  let component: DocumentationSeverityComponent;
  let fixture: ComponentFixture<DocumentationSeverityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationSeverityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationSeverityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
