import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationLineComponent } from './documentation-line.component';

describe('DocumentationLineComponent', () => {
  let component: DocumentationLineComponent;
  let fixture: ComponentFixture<DocumentationLineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationLineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
