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
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BakeHotPie.class, args);
        String port = context.getEnvironment().getProperty("local.server.port");
        System.out.println("Hotpie Server started successfully!!");
        System.out.println("** Hotpie Server is running on localhost:" + port +", open your browser on http://localhost:" + port + "/ **");
    }
}
