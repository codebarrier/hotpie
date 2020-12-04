package com.codebarrier.hotpie.startup;

import com.codebarrier.hotpie.model.Group;
import com.codebarrier.hotpie.processor.DefaultProcessor;
import com.codebarrier.hotpie.processor.Processor;
import com.codebarrier.hotpie.processor.ProcessorPipeline;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessorExecutor {

    private final ProcessorPipeline pipeline;

    public ProcessorExecutor(ProcessorPipeline pipeline, File source, File destination) {
        this.pipeline = pipeline;
    }

    public ProcessorExecutor(ProcessorPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public List<Group> execute(InputStream inStream) {
        List<Group> processedGroups = new ArrayList<>();
        boolean first = true;

        for (Processor processor : pipeline.getProcessors()) {
            if (first) {
                processedGroups = processor.process(inStream);
                first = false;
            } else {
                processedGroups = processor.processGroups(processedGroups);
            }
        }

        if (pipeline.getProcessors() == null || pipeline.getProcessors().size() == 0) {
            DefaultProcessor defaultProcessor = new DefaultProcessor();
            processedGroups = defaultProcessor.process(inStream);
        }

        return processedGroups;
    }
}
