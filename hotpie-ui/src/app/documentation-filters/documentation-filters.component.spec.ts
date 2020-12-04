import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationFiltersComponent } from './documentation-filters.component';

describe('DocumentationFiltersComponent', () => {
  let component: DocumentationFiltersComponent;
  let fixture: ComponentFixture<DocumentationFiltersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationFiltersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
