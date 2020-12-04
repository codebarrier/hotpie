import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationHighlightComponent } from './documentation-highlight.component';

describe('DocumentationHighlightComponent', () => {
  let component: DocumentationHighlightComponent;
  let fixture: ComponentFixture<DocumentationHighlightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationHighlightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationHighlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
