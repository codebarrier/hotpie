import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-documentation-line',
  templateUrl: './documentation-line.component.html',
  styleUrls: ['./documentation-line.component.scss']
})
export class DocumentationLineComponent implements OnInit {

  multilineCode: string =`
2020-06-04 18:35:12.881 TRACE 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : 
	c.c.h.r.c.HotPieRestController:
	{GET /getConfigurations}: processData()
	{POST /processData}: processData(MultipartFile,String,RedirectAttributes)
	{POST /processDataWithProfile}: processData(MultipartFile,MultipartFile,RedirectAttributes)
	{POST /processTextData}: processTextData(String,String,RedirectAttributes)
2020-06-04 18:35:12.886 TRACE 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : 
	c.c.h.r.c.RegexTesterController:
	{POST /testRegex}: testRegex(String,String)
2020-06-04 18:35:12.893 TRACE 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : 
	o.s.b.a.w.s.e.BasicErrorController:
	{ /error}: error(HttpServletRequest)
	{ /error, produces [text/html]}: errorHtml(HttpServletRequest,HttpServletResponse)`;

  lineexampleregex: string = `
  ^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+\\S+\\s+.*
  `;

  constructor() { }

  ngOnInit(): void {
  }

}
