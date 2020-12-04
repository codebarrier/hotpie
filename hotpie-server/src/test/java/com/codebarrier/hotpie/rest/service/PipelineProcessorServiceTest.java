package com.codebarrier.hotpie.rest.service;

import com.codebarrier.hotpie.model.Checkpoint;
import com.codebarrier.hotpie.model.Data;
import com.codebarrier.hotpie.model.Group;
import com.codebarrier.hotpie.processor.ProcessorPipeline;
import com.codebarrier.hotpie.processor.SeverityProcessor;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.util.List;

public class PipelineProcessorServiceTest {

    String completeConfig = "{\n" +
            "\t\"lineDemarcation\": {\n" +
            "\t\t\"lineRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*\"\n" +
            "\t},\n" +
            "\t\"filters\": [{\n" +
            "\t\t\"filterRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*\\\\[\\\\S+, (\\\\S+)\\\\].*\",\n" +
            "\t\t\"isExclude\": true,\n" +
            "\t\t\"filterValue\": \"customer_95\"\n" +
            "\t}],\n" +
            "\t\"highlights\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat initialized with (.*)\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Starting Servlet engine: \\\\[(.*)\\\\].*\"\n" +
            "\t}],\n" +
            "\t\"severities\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.\\\\s+INFO.*\",\n" +
            "\t\t\"severity\": \"HIGH\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+DEBUG\\\\s.*\",\n" +
            "\t\t\"severity\": \"MEDIUM\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+TRACE\\\\s.*\",\n" +
            "\t\t\"severity\": \"LOW\"\n" +
            "\t}],\n" +
            "\t\"checkpoints\": [{\n" +
            "\t\t\"checkpointHeader\": \"Server Initialization\",\n" +
            "\t\t\"checkpointDescription\": \"The server is initialized with the supplied configuraton.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat initialized.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Spring Initialization\",\n" +
            "\t\t\"checkpointDescription\": \"Verifying the initialization of the server internal components\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Initializing Spring embedded WebApplicationContext.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Successful Server Startup\",\n" +
            "\t\t\"checkpointDescription\": \"The server has started successfully.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat started on port.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Started Customer Processing\",\n" +
            "\t\t\"checkpointDescription\": \"The server has started processing customer requests.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*customer_1.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Processing Complete\",\n" +
            "\t\t\"checkpointDescription\": \"The server has completed processing all of the customers's requests.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*customer_99.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Server Shutdown Successful\",\n" +
            "\t\t\"checkpointDescription\": \"The server has gracefully shutdown without issues.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Shutting down ExecutorService 'applicationTaskExecutor'.*\"\n" +
            "\t}],\n" +
            "\t\"groups\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+\\\\S+\\\\s+\\\\d+\\\\s+---\\\\s\\\\[(\\\\S+)].+[\\\\r|\\\\n|\\\\s|\\\\S|\\\\d]+\",\n" +
            "\t\t\"name\": \"Thread Group\"\n" +
            "\t}]\n" +
            "}";

    String firstfilter = "{\n" +
            "\t\"filters\": [{\n" +
            "\t\t\"filterRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*\\\\[\\\\S+, (\\\\S+)\\\\].*\",\n" +
            "\t\t\"isExclude\": true,\n" +
            "\t\t\"filterValue\": \"customer_95\"\n" +
            "\t}],\n" +
            "\t\"highlights\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat initialized with (.*)\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Starting Servlet engine: \\\\[(.*)\\\\].*\"\n" +
            "\t}]\n" +
            "}";
    String firstHigh = "{\n" +
            "\t\"highlights\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat initialized with (.*)\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Starting Servlet engine: \\\\[(.*)\\\\].*\"\n" +
            "\t}],\n" +
            "\t\"severities\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.\\\\s+INFO.*\",\n" +
            "\t\t\"severity\": \"HIGH\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+DEBUG\\\\s.*\",\n" +
            "\t\t\"severity\": \"MEDIUM\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+TRACE\\\\s.*\",\n" +
            "\t\t\"severity\": \"LOW\"\n" +
            "\t}]\n" +
            "}";
    String firstSev = "{\n" +
            "\t\"severities\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.\\\\s+INFO.*\",\n" +
            "\t\t\"severity\": \"HIGH\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+DEBUG\\\\s.*\",\n" +
            "\t\t\"severity\": \"MEDIUM\"\n" +
            "\t}, {\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+TRACE\\\\s.*\",\n" +
            "\t\t\"severity\": \"LOW\"\n" +
            "\t}],\n" +
            "\t\"checkpoints\": [{\n" +
            "\t\t\"checkpointHeader\": \"Server Initialization\",\n" +
            "\t\t\"checkpointDescription\": \"The server is initialized with the supplied configuraton.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat initialized.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Spring Initialization\",\n" +
            "\t\t\"checkpointDescription\": \"Verifying the initialization of the server internal components\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Initializing Spring embedded WebApplicationContext.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Successful Server Startup\",\n" +
            "\t\t\"checkpointDescription\": \"The server has started successfully.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat started on port.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Started Customer Processing\",\n" +
            "\t\t\"checkpointDescription\": \"The server has started processing customer requests.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*customer_1.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Processing Complete\",\n" +
            "\t\t\"checkpointDescription\": \"The server has completed processing all of the customers's requests.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*customer_99.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Server Shutdown Successful\",\n" +
            "\t\t\"checkpointDescription\": \"The server has gracefully shutdown without issues.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Shutting down ExecutorService 'applicationTaskExecutor'.*\"\n" +
            "\t}]\n" +
            "}";

    String firstCheck = "{\n" +
            "\t\"checkpoints\": [{\n" +
            "\t\t\"checkpointHeader\": \"Server Initialization\",\n" +
            "\t\t\"checkpointDescription\": \"The server is initialized with the supplied configuraton.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat initialized.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Spring Initialization\",\n" +
            "\t\t\"checkpointDescription\": \"Verifying the initialization of the server internal components\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Initializing Spring embedded WebApplicationContext.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Successful Server Startup\",\n" +
            "\t\t\"checkpointDescription\": \"The server has started successfully.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Tomcat started on port.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Started Customer Processing\",\n" +
            "\t\t\"checkpointDescription\": \"The server has started processing customer requests.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*customer_1.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Processing Complete\",\n" +
            "\t\t\"checkpointDescription\": \"The server has completed processing all of the customers's requests.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*customer_99.*\"\n" +
            "\t}, {\n" +
            "\t\t\"checkpointHeader\": \"Server Shutdown Successful\",\n" +
            "\t\t\"checkpointDescription\": \"The server has gracefully shutdown without issues.\",\n" +
            "\t\t\"checkpointIdentificationRegex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}.*Shutting down ExecutorService 'applicationTaskExecutor'.*\"\n" +
            "\t}],\n" +
            "\t\"groups\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+\\\\S+\\\\s+\\\\d+\\\\s+---\\\\s\\\\[(\\\\S+)].*\",\n" +
            "\t\t\"name\": \"Thread Group\"\n" +
            "\t}]\n" +
            "}";

    String firstGroup = "{\n" +
            "\t\"groups\": [{\n" +
            "\t\t\"regex\": \"\\\\d{4}-\\\\d{2}-\\\\d{2}\\\\s\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}\\\\s+\\\\S+\\\\s+\\\\d+\\\\s+---\\\\s\\\\[(\\\\S+)].*\",\n" +
            "\t\t\"name\": \"Thread Group\"\n" +
            "\t}]\n" +
            "}";

    private static final String sampleData = "2020-06-04 18:35:11.227  INFO 3064 --- [main] com.codebarrier.hotpie.BakeHotPie        : No active profile set, falling back to default profiles: default\n" +
            "2020-06-04 18:35:12.527  INFO 3064 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)\n" +
            "2020-06-04 18:35:12.540  INFO 3064 --- [main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]\n" +
            "2020-06-04 18:35:12.542  INFO 3064 --- [main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.29]\n" +
            "2020-06-04 18:35:12.635  INFO 3064 --- [main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext\n" +
            "2020-06-04 18:35:12.635 DEBUG 3064 --- [main] o.s.web.context.ContextLoader            : Published root WebApplicationContext as ServletContext attribute with name [org.springframework.web.context.WebApplicationContext.ROOT]\n" +
            "2020-06-04 18:35:12.637  INFO 3064 --- [main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1357 ms\n" +
            "2020-06-04 18:35:12.822  INFO 3064 --- [main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'\n" +
            "2020-06-04 18:35:12.835 DEBUG 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerAdapter : ControllerAdvice beans: 0 @ModelAttribute, 0 @InitBinder, 1 RequestBodyAdvice, 1 ResponseBodyAdvice\n" +
            "2020-06-04 18:35:12.881 TRACE 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : \n" +
            "\tc.c.h.r.c.HotPieRestController:\n" +
            "\t{GET /getConfigurations}: processData()\n" +
            "\t{POST /processData}: processData(MultipartFile,String,RedirectAttributes)\n" +
            "\t{POST /processDataWithProfile}: processData(MultipartFile,MultipartFile,RedirectAttributes)\n" +
            "\t{POST /processTextData}: processTextData(String,String,RedirectAttributes)\n" +
            "2020-06-04 18:35:12.886 TRACE 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : \n" +
            "\tc.c.h.r.c.RegexTesterController:\n" +
            "\t{POST /testRegex}: testRegex(String,String)\n" +
            "2020-06-04 18:35:12.893 TRACE 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : \n" +
            "\to.s.b.a.w.s.e.BasicErrorController:\n" +
            "\t{ /error}: error(HttpServletRequest)\n" +
            "\t{ /error, produces [text/html]}: errorHtml(HttpServletRequest,HttpServletResponse)\n" +
            "2020-06-04 18:35:12.897 DEBUG 3064 --- [main] s.w.s.m.m.a.RequestMappingHandlerMapping : 7 mappings in 'requestMappingHandlerMapping'\n" +
            "2020-06-04 18:35:12.900  INFO 3064 --- [main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [resources/index.html]\n" +
            "2020-06-04 18:35:12.911 DEBUG 3064 --- [main] o.s.w.s.h.BeanNameUrlHandlerMapping      : Detected 0 mappings in 'beanNameHandlerMapping'\n" +
            "2020-06-04 18:35:12.926 TRACE 3064 --- [main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped [/webjars/**] onto ResourceHttpRequestHandler [\"classpath:/META-INF/resources/webjars/\"]\n" +
            "2020-06-04 18:35:12.927 TRACE 3064 --- [main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped [/**] onto ResourceHttpRequestHandler [\"classpath:/META-INF/resources/\", \"classpath:/resources/\", \"classpath:/static/\", \"classpath:/public/\", \"/\"]\n" +
            "2020-06-04 18:35:12.930 DEBUG 3064 --- [main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Patterns [/webjars/**, /**] in 'resourceHandlerMapping'\n" +
            "2020-06-04 18:35:12.944 DEBUG 3064 --- [main] .m.m.a.ExceptionHandlerExceptionResolver : ControllerAdvice beans: 0 @ExceptionHandler, 1 ResponseBodyAdvice\n" +
            "2020-06-04 18:35:13.037  INFO 3064 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''\n" +
            "2020-06-04 18:45:10.663 TRACE 3064 --- [http-nio-8080-exec-19] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [^customer_/s, customer_95] \n" +
            "2020-06-04 18:35:13.042  INFO 3064 --- [main] com.codebarrier.hotpie.BakeHotPie        : Started BakeHotPie in 2.274 seconds (JVM running for 2.925)\n" +
            "2020-06-04 18:35:58.122  INFO 3064 --- [http-nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:35:58.123  INFO 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'\n" +
            "2020-06-04 18:35:58.126 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Detected org.springframework.web.multipart.support.StandardServletMultipartResolver@34f4cf12\n" +
            "2020-06-04 18:35:58.132 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : No LocaleResolver 'localeResolver': using default [AcceptHeaderLocaleResolver]\n" +
            "2020-06-04 18:35:58.135 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : No ThemeResolver 'themeResolver': using default [FixedThemeResolver]\n" +
            "2020-06-04 18:35:58.141 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : No RequestToViewNameTranslator 'viewNameTranslator': using default [DefaultRequestToViewNameTranslator]\n" +
            "2020-06-04 18:35:58.157 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : No FlashMapManager 'flashMapManager': using default [SessionFlashMapManager]\n" +
            "2020-06-04 18:35:58.158 DEBUG 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : enableLoggingRequestDetails='false': request parameters and headers will be masked to prevent unsafe logging of potentially sensitive data\n" +
            "2020-06-04 18:35:58.162  INFO 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 36 ms\n" +
            "2020-06-04 18:35:58.185 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:35:58.194 TRACE 3064 --- [http-nio-8080-exec-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:35:58.218 DEBUG 3064 --- [http-nio-8080-exec-1] .w.s.m.m.a.ServletInvocableHandlerMethod : Could not resolve parameter [0] in public com.codebarrier.hotpie.model.RegexTestResult com.codebarrier.hotpie.rest.controller.RegexTesterController.testRegex(java.lang.String,java.lang.String): Required String parameter 'regex' is not present\n" +
            "2020-06-04 18:35:58.226  WARN 3064 --- [http-nio-8080-exec-1] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required String parameter 'regex' is not present]\n" +
            "2020-06-04 18:35:58.227 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : No view rendering, null ModelAndView returned.\n" +
            "2020-06-04 18:35:58.228 DEBUG 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 400 BAD_REQUEST, headers={}\n" +
            "2020-06-04 18:35:58.234 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : \"ERROR\" dispatch for POST \"/error\", parameters={}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:35:58.236 TRACE 3064 --- [http-nio-8080-exec-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#error(HttpServletRequest)\n" +
            "2020-06-04 18:35:58.242 TRACE 3064 --- [http-nio-8080-exec-1] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [org.apache.catalina.core.ApplicationHttpRequest@60489c0b]\n" +
            "2020-06-04 18:35:58.279 DEBUG 3064 --- [http-nio-8080-exec-1] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'application/json', given [application/json] and supported [application/json, application/*+json, application/json, application/*+json]\n" +
            "2020-06-04 18:35:58.280 TRACE 3064 --- [http-nio-8080-exec-1] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing [{timestamp=Thu Jun 04 18:35:58 IST 2020, status=400, error=Bad Request, message=Required String parameter 'regex' is not present, path=/testRegex}]\n" +
            "2020-06-04 18:35:58.361 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : No view rendering, null ModelAndView returned.\n" +
            "2020-06-04 18:35:58.392 DEBUG 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Exiting from \"ERROR\" dispatch, status 400, headers={masked}\n" +
            "2020-06-04 18:39:14.832 TRACE 3064 --- [http-nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:39:14.833 TRACE 3064 --- [http-nio-8080-exec-2] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:39:14.842 TRACE 3064 --- [http-nio-8080-exec-2] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [abcdefg, abcdefghijk]\n" +
            "2020-06-04 18:39:14.864 DEBUG 3064 --- [http-nio-8080-exec-2] m.m.a.RequestResponseBodyMethodProcessor : Using 'application/json', given [application/json] and supported [application/json, application/*+json, application/json, application/*+json]\n" +
            "2020-06-04 18:39:14.865 TRACE 3064 --- [http-nio-8080-exec-2] m.m.a.RequestResponseBodyMethodProcessor : Writing [com.codebarrier.hotpie.model.RegexTestResult@44dccb1f]\n" +
            "2020-06-04 18:39:14.874 TRACE 3064 --- [http-nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : No view rendering, null ModelAndView returned.\n" +
            "2020-06-04 18:39:14.877 DEBUG 3064 --- [http-nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Completed 200 OK, headers={masked}\n" +
            "2020-06-04 18:41:35.867 TRACE 3064 --- [http-nio-8080-exec-4] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.872 TRACE 3064 --- [http-nio-8080-exec-6] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.906 TRACE 3064 --- [http-nio-8080-exec-6] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.908 TRACE 3064 --- [http-nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.884 TRACE 3064 --- [http-nio-8080-exec-7] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.885 TRACE 3064 --- [http-nio-8080-exec-5] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.904 TRACE 3064 --- [http-nio-8080-exec-4] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.914 TRACE 3064 --- [http-nio-8080-exec-5] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.904 TRACE 3064 --- [http-nio-8080-exec-13] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.904 TRACE 3064 --- [http-nio-8080-exec-12] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.906 TRACE 3064 --- [http-nio-8080-exec-9] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.906 TRACE 3064 --- [http-nio-8080-exec-10] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.908 TRACE 3064 --- [http-nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.909 TRACE 3064 --- [http-nio-8080-exec-8] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.930 TRACE 3064 --- [http-nio-8080-exec-3] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.911 TRACE 3064 --- [http-nio-8080-exec-11] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.911 TRACE 3064 --- [http-nio-8080-exec-14] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.911 TRACE 3064 --- [http-nio-8080-exec-15] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.911 TRACE 3064 --- [http-nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.912 TRACE 3064 --- [http-nio-8080-exec-16] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.912 TRACE 3064 --- [http-nio-8080-exec-6] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [abcdefg, abcdefghijk]\n" +
            "2020-06-04 18:41:35.912 TRACE 3064 --- [http-nio-8080-exec-2] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.913 TRACE 3064 --- [http-nio-8080-exec-7] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.917 TRACE 3064 --- [http-nio-8080-exec-5] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [abcdefg, abcdefghijk]\n" +
            "2020-06-04 18:41:35.917 TRACE 3064 --- [http-nio-8080-exec-4] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [abcdefg, abcdefghijk]\n" +
            "2020-06-04 18:41:35.918 TRACE 3064 --- [http-nio-8080-exec-13] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.919 TRACE 3064 --- [http-nio-8080-exec-18] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.919 TRACE 3064 --- [http-nio-8080-exec-12] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.921 TRACE 3064 --- [http-nio-8080-exec-19] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.923 TRACE 3064 --- [http-nio-8080-exec-17] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.955 TRACE 3064 --- [http-nio-8080-exec-19] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.925 TRACE 3064 --- [http-nio-8080-exec-9] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.929 TRACE 3064 --- [http-nio-8080-exec-10] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.929 TRACE 3064 --- [http-nio-8080-exec-20] o.s.web.servlet.DispatcherServlet        : POST \"/testRegex\", parameters={masked}, headers={masked} in DispatcherServlet 'dispatcherServlet'\n" +
            "2020-06-04 18:41:35.931 TRACE 3064 --- [http-nio-8080-exec-8] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.931 TRACE 3064 --- [http-nio-8080-exec-3] .w.s.m.m.a.ServletInvocableHandlerMethod : Arguments: [abcdefg, abcdefghijk]\n" +
            "2020-06-04 18:41:35.937 TRACE 3064 --- [http-nio-8080-exec-11] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.938 TRACE 3064 --- [http-nio-8080-exec-14] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.939 TRACE 3064 --- [http-nio-8080-exec-15] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)\n" +
            "2020-06-04 18:41:35.939 TRACE 3064 --- [http-nio-8080-exec-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.codebarrier.hotpie.rest.controller.RegexTesterController#testRegex(String, String)";


    private static final PipelineProcessorService service = new PipelineProcessorService();

    @org.testng.annotations.Test
    public void testProcessData() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream(completeConfig.getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 9);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        int totalLines = 0;
        for (Group group : result.get(0).getSubGroups()) {
            totalLines += group.getGroupData().size();
        }
        Assert.assertEquals(result.get(0).getSubGroups().size(), 21);
        Assert.assertEquals(result.get(0).getGroupData().size(), 0);
        Assert.assertEquals(result.get(0).getHighestSeverity(), SeverityProcessor.SEVERITY.HIGH);
        Assert.assertEquals(result.get(0).getHighlights().size(), 2);
        Assert.assertEquals(result.get(0).getHighlights().get(0).highlightedData, "port(s): 8080 (http)");
        Assert.assertEquals(result.get(0).getHighlights().get(1).highlightedData, "Apache Tomcat/9.0.29");
        Assert.assertEquals(totalLines, 90);

        Assert.assertEquals(result.get(0).getSubGroups().get(0).getGroupName(), "main");
        Assert.assertEquals(result.get(0).getSubGroups().get(0).getGroupData().size(), 21);
        Assert.assertEquals(result.get(0).getSubGroups().get(0).getHighestSeverity(), SeverityProcessor.SEVERITY.HIGH);
        Assert.assertEquals(result.get(0).getSubGroups().get(0).getHighlights().size(), 2);
        Assert.assertEquals(result.get(0).getSubGroups().get(0).getHighlights().get(0).highlightedData, "port(s): 8080 (http)");
        Assert.assertEquals(result.get(0).getSubGroups().get(0).getHighlights().get(1).highlightedData, "Apache Tomcat/9.0.29");
    }

    @org.testng.annotations.Test
    public void testProcessDataFirstFilter() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream(firstfilter.getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 3);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getGroupData().size(), 100);
        Assert.assertEquals(result.get(0).getHighlights().size(), 2);
        Assert.assertEquals(result.get(0).getHighlights().get(0).highlightedData, "port(s): 8080 (http)");
        Assert.assertEquals(result.get(0).getHighlights().get(1).highlightedData, "Apache Tomcat/9.0.29");
    }

    @org.testng.annotations.Test
    public void testProcessDataFirstHigh() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream(firstHigh.getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 5);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getGroupData().size(), 101);
        Assert.assertEquals(result.get(0).getHighlights().size(), 2);
        Assert.assertEquals(result.get(0).getHighlights().get(0).highlightedData, "port(s): 8080 (http)");
        Assert.assertEquals(result.get(0).getHighlights().get(1).highlightedData, "Apache Tomcat/9.0.29");
    }

    @org.testng.annotations.Test
    public void testProcessDataFirstSev() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream(firstSev.getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 4);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getGroupData().size(), 101);
        Assert.assertEquals(result.get(0).getHighestSeverity(), SeverityProcessor.SEVERITY.HIGH);
        boolean lowSevFound = false;
        boolean medSevFound = false;
        boolean highSevFound = false;
        for (Data data : result.get(0).getGroupData()) {
            if ("LOW".equals(data.getDataSeverity())) {
                lowSevFound = true;
            } if ("MEDIUM".equals(data.getDataSeverity())) {
                medSevFound = true;
            } if ("HIGH".equals(data.getDataSeverity())) {
                highSevFound = true;
            }
        }

        Assert.assertEquals(lowSevFound, true);
        Assert.assertEquals(medSevFound, true);
        Assert.assertEquals(highSevFound, true);
    }

    @org.testng.annotations.Test
    public void testProcessDataFirstCheck() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream(firstCheck.getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 2);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        int passed = 0;
        int failed = 0;
        for (Checkpoint check : result.get(0).getCheckpoints()) {
            if (check.isCheckpointReached()) {
                passed++;
            } else {
                failed++;
            }
        }
        Assert.assertEquals(passed, 3);
        Assert.assertEquals(failed, 3);
    }

    @org.testng.annotations.Test
    public void testProcessDataFirstGroup() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream(firstGroup.getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 1);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        int totalLines = 0;
        for (Group group : result.get(0).getSubGroups()) {
            totalLines += group.getGroupData().size();
        }
        Assert.assertEquals(totalLines, 101);
        Assert.assertEquals(result.get(0).getSubGroups().size(), 22);
    }

    @org.testng.annotations.Test
    public void testProcessDataEmptyConfig() {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(new ByteArrayInputStream("".getBytes()));
        Assert.assertEquals(configPipeline.getProcessors().size(), 0);
        List<Group> result = service.processData(new ByteArrayInputStream(sampleData.getBytes()), configPipeline);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getGroupData().size(), 101);
        Assert.assertEquals(result.get(0).getSubGroups().size(), 0);
    }
}