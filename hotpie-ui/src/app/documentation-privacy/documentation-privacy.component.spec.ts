import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationPrivacyComponent } from './documentation-privacy.component';

describe('DocumentationPrivacyComponent', () => {
  let component: DocumentationPrivacyComponent;
  let fixture: ComponentFixture<DocumentationPrivacyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationPrivacyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationPrivacyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
