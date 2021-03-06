import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-documentation-checkpoints',
  templateUrl: './documentation-checkpoints.component.html',
  styleUrls: ['./documentation-checkpoints.component.scss']
})
export class DocumentationCheckpointsComponent implements OnInit {

  constructor() { }

  rawData: string = `2020-06-04 18:35:13.037  INFO 3064 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''`;
  sampleRegex: string = `\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}.*Tomcat started on port.*`;

  ngOnInit(): void {
  }

}
