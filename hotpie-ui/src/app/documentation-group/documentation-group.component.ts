import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-documentation-group',
  templateUrl: './documentation-group.component.html',
  styleUrls: ['./documentation-group.component.scss']
})
export class DocumentationGroupComponent implements OnInit {

  rawData: string = `
  2020-06-04 18:35:12.542  INFO 3064 --- [main] org.apache.catalina.core.StandardEngine : Starting Servlet engine: [Apache Tomcat/9.0.29]
  2020-06-04 18:35:58.162  INFO 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet : Completed initialization in 36 ms
  2020-06-04 18:39:14.877 DEBUG 3064 --- [http-nio-8080-exec-2] o.s.web.servlet.DispatcherServlet : Completed 200 OK, headers={masked}
  2020-06-04 18:41:35.867 TRACE 3064 --- [http-nio-8080-exec-4] o.s.web.servlet.DispatcherServlet : POST "/testRegex", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'
  `;

  sampleRegex: string = `^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+\\S+\\s+\\d+\\s+---\\s\\[(\\S+)\\].*`;

  constructor() { }

  ngOnInit(): void {
  }

}
