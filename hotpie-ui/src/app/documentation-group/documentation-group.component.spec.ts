import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationGroupComponent } from './documentation-group.component';

describe('DocumentationGroupComponent', () => {
  let component: DocumentationGroupComponent;
  let fixture: ComponentFixture<DocumentationGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
