import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-documentation-highlight',
  templateUrl: './documentation-highlight.component.html',
  styleUrls: ['./documentation-highlight.component.scss']
})
export class DocumentationHighlightComponent implements OnInit {

  constructor() { }

  rawData: string = `
  2020-06-04 18:35:12.527  INFO 3064 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
  2020-06-04 18:35:12.542  INFO 3064 --- [main] org.apache.catalina.core.StandardEngine : Starting Servlet engine: [Apache Tomcat/9.0.29]
  `;

  sampleRegex: string = `^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}.*Tomcat initialized with (.*)
  ^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}.*Starting Servlet engine: \\[(.*)\\].*`;

  ngOnInit(): void {
  }

}
