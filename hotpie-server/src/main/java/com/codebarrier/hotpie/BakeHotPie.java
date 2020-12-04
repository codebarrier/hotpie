package com.codebarrier.hotpie;

import com.codebarrier.hotpie.model.CheckpointConfiguration;
import com.codebarrier.hotpie.processor.*;
import com.codebarrier.hotpie.startup.ProcessorExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BakeHotPie {


    private static final String TIMESTAMP_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}";
    private static final String THREADNAME_REGEX = TIMESTAMP_REGEX + "\\s\\[([^\\]]+)\\].*";
    private static final String LOGLEVEL_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}\\s\\[[^\\]]+\\]\\s+(\\S+).*";
    private static final String CLASSNAME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}\\s\\[[^\\]]+\\]\\s\\S+\\s+(\\S+).*";
    private static final String COUNTER_REGEX = ".*\\s*Incremented counter\\s(\\S+).*";
    private static final String ERRORLEVEL_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}\\s\\[[^\\]]+\\]\\s+ERROR.*";
    private static final String INFOLEVEL_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}\\s\\[[^\\]]+\\]\\s+INFO.*";

    public static void main(String[] args) {
        /*try {
            testHotPie2();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ConfigurableApplicationContext context = SpringApplication.run(BakeHotPie.class, args);
        String port = context.getEnvironment().getProperty("local.server.port");
        System.out.println("Hotpie Server started successfully!!");
        System.out.println("** Hotpie Server is running on localhost:" + port +", open your browser on http://localhost:" + port + "/ **");
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }*/

    public static void testHotPie()  throws IOException {
        ProcessorPipeline pipeline = new ProcessorPipeline();
        pipeline.addProcessor(new LineDemarkationProcessor(TIMESTAMP_REGEX));
        pipeline.addProcessor(new SeverityProcessor(INFOLEVEL_REGEX, SeverityProcessor.SEVERITY.MEDIUM));
        pipeline.addProcessor(new SeverityProcessor(ERRORLEVEL_REGEX, SeverityProcessor.SEVERITY.HIGH));
        pipeline.addProcessor(new HighlighterProcessor(COUNTER_REGEX));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "pool-11-thread-1"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "DefaultMessageListenerContainer-1"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "ntf-smtp-connection-monitor-ntfSMTPConfig0"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Timer-6"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Timer-7"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Thread-119"));
        pipeline.addProcessor(new GroupByProcessor("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}\\s\\[([^\\]]+)\\].*", "Thread Group"));
        pipeline.addProcessor(new GroupByProcessor(LOGLEVEL_REGEX, "Log Level"));
        pipeline.addProcessor(new GroupByProcessor(CLASSNAME_REGEX, "Class Name"));
        pipeline.addProcessor(new FilterProcessor(true, LOGLEVEL_REGEX, "INFO"));
        // pipeline.addProcessor(new LineDemarkationCleanupProcessor());
        File sourceFile = Paths.get("C:\\Users\\maruthi.nityanandam\\Desktop\\garbage\\ntf_jbossas.log_ntf-JbossLogTailer.tail.log").toFile();
        File destinationFile = Paths.get("C:\\Users\\maruthi.nityanandam\\Desktop\\garbage\\ntf_jbossas.proc.log").toFile();
        destinationFile.delete();
        destinationFile.createNewFile();
        ProcessorExecutor executor = new ProcessorExecutor(pipeline, sourceFile, destinationFile);
        // executor.execute();
    }

    public static void testHotPie2() throws IOException {
        ProcessorPipeline pipeline = new ProcessorPipeline();
        pipeline.addProcessor(new LineDemarkationProcessor(TIMESTAMP_REGEX));
        pipeline.addProcessor(new SeverityProcessor(INFOLEVEL_REGEX, SeverityProcessor.SEVERITY.MEDIUM));
        pipeline.addProcessor(new SeverityProcessor(ERRORLEVEL_REGEX, SeverityProcessor.SEVERITY.HIGH));
        pipeline.addProcessor(new GroupByProcessor(THREADNAME_REGEX, "Thread Group"));
        pipeline.addProcessor(new HighlighterProcessor(COUNTER_REGEX));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Timer-6"));
        CheckpointConfiguration configuration = new CheckpointConfiguration();
        configuration.setCheckpointHeader("JMS Received");
        configuration.setCheckpointDescription("Checking the arrival of the JMS message from MSG.");
        configuration.setCheckpointIdentificationRegex("Incremented counter NTF_Incoming");
        CheckpointConfiguration configuration1 = new CheckpointConfiguration();
        configuration1.setCheckpointHeader("Notif Engine Processing");
        configuration1.setCheckpointDescription("Checking if the notif message was picked up by Notif Engine for processing.");
        configuration1.setCheckpointIdentificationRegex("Message read from Notif Queue");
        CheckpointConfiguration configuration2 = new CheckpointConfiguration();
        configuration2.setCheckpointHeader("Customer Processing");
        configuration2.setCheckpointDescription("Checking if the customer data has been retrieved successfully.");
        configuration2.setCheckpointIdentificationRegex("Customer ID List = ");
        CheckpointConfiguration configuration3 = new CheckpointConfiguration();
        configuration3.setCheckpointHeader("Template Retrieval");
        configuration3.setCheckpointDescription("Checking if the appropriate template has been retrieved.");
        configuration3.setCheckpointIdentificationRegex("retrieving templateTextRuleName=");
        CheckpointConfiguration configuration4 = new CheckpointConfiguration();
        configuration4.setCheckpointHeader("Variable Evaluation");
        configuration4.setCheckpointDescription("Evaluating the variables in the template.");
        configuration4.setCheckpointIdentificationRegex("computed text from segmentText=");
        CheckpointConfiguration configuration5 = new CheckpointConfiguration();
        configuration5.setCheckpointHeader("Processing Complete");
        configuration5.setCheckpointDescription("Message processing completion in the notif engine. Messages will now be sent to output channels.");
        configuration5.setCheckpointIdentificationRegex("In sendMsgToChannel\\(\\), calculated channel");
        List<CheckpointConfiguration> allCheckPoints = new ArrayList<>();
        allCheckPoints.add(configuration);
        allCheckPoints.add(configuration1);
        allCheckPoints.add(configuration2);
        allCheckPoints.add(configuration3);
        allCheckPoints.add(configuration4);
        allCheckPoints.add(configuration5);
        pipeline.addProcessor(new SequentialCheckpointProcessor(allCheckPoints));
        /*pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "pool-11-thread-1"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "DefaultMessageListenerContainer-1"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "ntf-smtp-connection-monitor-ntfSMTPConfig0"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Timer-6"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Timer-7"));
        pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Thread-119"));*/
        // pipeline.addProcessor(new FilterProcessor(true, THREADNAME_REGEX, "Timer-6"));
        // pipeline.addProcessor(new GroupByProcessor(THREADNAME_REGEX, "Thread Group"));
        // pipeline.addProcessor(new GroupByProcessor(LOGLEVEL_REGEX, "Log Level"));
        // pipeline.addProcessor(new GroupByProcessor(CLASSNAME_REGEX, "Class Name"));
        // pipeline.addProcessor(new FilterProcessor(true, LOGLEVEL_REGEX, "INFO"));
        // pipeline.addProcessor(new SeverityProcessor(ERRORLEVEL_REGEX, SeverityProcessor.SEVERITY.MEDIUM));
        File sourceFile = Paths.get("C:\\Users\\maruthi.nityanandam\\Desktop\\garbage\\spring.log").toFile();
        File destinationFile = Paths.get("C:\\Users\\maruthi.nityanandam\\Desktop\\garbage\\ntf_jbossas.proc.log").toFile();
        destinationFile.delete();
        destinationFile.createNewFile();


        ProcessorPipeline pipelineSpring = new ProcessorPipeline();

        pipelineSpring.addProcessor(new GroupByProcessor("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+\\S+\\s+\\d+\\s+---\\s\\[(\\S+)].*", "Thread Group"));
        pipelineSpring.addProcessor(new HighlighterProcessor("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}.*Tomcat initialized with (.*)"));
        pipelineSpring.addProcessor(new HighlighterProcessor("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}.*Starting Servlet engine: \\[(.*)\\].*"));
        ProcessorExecutor executor = new ProcessorExecutor(pipelineSpring, sourceFile, destinationFile);
        // executor.execute();
    }
}
