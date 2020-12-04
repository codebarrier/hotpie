package com.codebarrier.hotpie.rest.service;

import com.codebarrier.hotpie.model.Group;
import com.codebarrier.hotpie.model.ProcessorPipelineProfile;
import com.codebarrier.hotpie.processor.ProcessorPipeline;

import java.io.InputStream;
import java.util.List;

public interface ProcessorService {

    List<Group> processData(InputStream source, ProcessorPipeline pipelineConfig);

    ProcessorPipeline getPipelineFromConfig(InputStream inputStream);

    ProcessorPipeline getPipelineFromConfig(ProcessorPipelineProfile profile);
}
