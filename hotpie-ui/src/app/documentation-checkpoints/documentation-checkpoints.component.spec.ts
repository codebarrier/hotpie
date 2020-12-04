import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentationCheckpointsComponent } from './documentation-checkpoints.component';

describe('DocumentationCheckpointsComponent', () => {
  let component: DocumentationCheckpointsComponent;
  let fixture: ComponentFixture<DocumentationCheckpointsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentationCheckpointsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentationCheckpointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
