import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-documentation-filters',
  templateUrl: './documentation-filters.component.html',
  styleUrls: ['./documentation-filters.component.scss']
})
export class DocumentationFiltersComponent implements OnInit {

  constructor() { }

  rawData: string = `
2020-06-04 18:45:10.659 DEBUG 3064 --- [http-nio-8080-exec-16] o.s.web.servlet.DispatcherServlet        : Completed 200 OK, headers={masked}
2020-06-04 18:45:10.660 TRACE 3064 --- [http-nio-8080-exec-13] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_94]
2020-06-04 18:45:10.663 TRACE 3064 --- [http-nio-8080-exec-19] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_95]
2020-06-04 18:45:10.664 TRACE 3064 --- [http-nio-8080-exec-20] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_96]
2020-06-04 18:45:10.665 TRACE 3064 --- [http-nio-8080-exec-15] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_97]
`;

  sampleRegex: string = `\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}.*\\[\\S+, (\\S+)\\].*`;

  filterValue: string = `customer_95`;

  excludedSample: string = `
2020-06-04 18:45:10.659 DEBUG 3064 --- [http-nio-8080-exec-16] o.s.web.servlet.DispatcherServlet        : Completed 200 OK, headers={masked}
2020-06-04 18:45:10.660 TRACE 3064 --- [http-nio-8080-exec-13] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_94]
2020-06-04 18:45:10.664 TRACE 3064 --- [http-nio-8080-exec-20] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_96]
2020-06-04 18:45:10.665 TRACE 3064 --- [http-nio-8080-exec-15] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_97]`;

  includedSample: string = `
2020-06-04 18:45:10.663 TRACE 3064 --- [http-nio-8080-exec-19] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_95]`

  ngOnInit(): void {
  }

}
