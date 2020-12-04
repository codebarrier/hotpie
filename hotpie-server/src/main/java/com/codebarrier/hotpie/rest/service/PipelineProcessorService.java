package com.codebarrier.hotpie.rest.service;

import com.codebarrier.hotpie.model.*;
import com.codebarrier.hotpie.processor.*;
import com.codebarrier.hotpie.processor.FilterProcessor;
import com.codebarrier.hotpie.startup.ProcessorExecutor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;



public class PipelineProcessorService implements ProcessorService {

    public Gson gson = new Gson();

    public PipelineProcessorService() {

    }

    @Override
    public List<Group> processData(InputStream source, ProcessorPipeline pipelineConfig) {
        ProcessorExecutor executor = new ProcessorExecutor(pipelineConfig);
        return executor.execute(source);
    }

    @Override
    public ProcessorPipeline getPipelineFromConfig(InputStream configSource) {
        ProcessorPipeline pipeline = new ProcessorPipeline();

        try {
            ProcessorPipelineProfile profile = gson.fromJson(new InputStreamReader(configSource,
                    StandardCharsets.UTF_8), ProcessorPipelineProfile.class);
            createProcessorsFromProfile(profile, pipeline);
        } catch (Exception e) {
            return pipeline;
        }

        return pipeline;
    }

    @Override
    public ProcessorPipeline getPipelineFromConfig(ProcessorPipelineProfile profile) {
        ProcessorPipeline pipeline = new ProcessorPipeline();
        try {
            createProcessorsFromProfile(profile, pipeline);
        } catch (Exception e) {
            return pipeline;
        }

        return pipeline;
    }


    private void createProcessorsFromProfile(ProcessorPipelineProfile profile, ProcessorPipeline pipeline) throws Exception {

        LineDemarcationProcessor line = profile.getLineDemarcation();
        if (line != null) {
            pipeline.addProcessor(new LineDemarkationProcessor(line.getLineRegex()));
        }


        List<com.codebarrier.hotpie.model.FilterProcessor> filters = profile.getFilters();
        if (filters != null) {
            for (com.codebarrier.hotpie.model.FilterProcessor filter : filters) {
                pipeline.addProcessor(new FilterProcessor(filter.isExclude(), filter.getFilterRegex(), filter.getFilterValue()));
            }
        }

        List<SeverityConfig> sevs = profile.getSeverities();
        if (sevs != null) {
            for (SeverityConfig sev : sevs) {
                pipeline.addProcessor(new SeverityProcessor(sev.getRegex(), SeverityProcessor.SEVERITY.getEnum(sev.getSeverity())));
            }
        }

        if (profile.getCheckpoints() != null) {
            pipeline.addProcessor(new SequentialCheckpointProcessor(profile.getCheckpoints()));
        }

        List<GroupConfig> groups = profile.getGroups();
        if (groups != null) {
            for (GroupConfig group : groups) {
                pipeline.addProcessor(new GroupByProcessor(group.getRegex(), group.name));
            }
        }

        List<HighlightConfig> highlights = profile.getHighlights();
        if (highlights != null) {
            for (HighlightConfig highlight : highlights) {
                pipeline.addProcessor(new HighlighterProcessor(highlight.getRegex()));
            }
        }

    }
}
