package com.codebarrier.hotpie.rest.controller;

import com.codebarrier.hotpie.model.Configuration;
import com.codebarrier.hotpie.model.Group;
import com.codebarrier.hotpie.model.ProcessorPipelineProfile;
import com.codebarrier.hotpie.processor.ProcessorPipeline;
import com.codebarrier.hotpie.rest.service.PipelineProcessorService;
import com.codebarrier.hotpie.rest.service.ProcessorService;
import com.codebarrier.hotpie.rest.service.UnknownProfileException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HotPieRestController {

    @Autowired
    ProcessorService service;

    public Gson gson = new Gson();

    @PostMapping("/processDataWithProfile")
    public List<Group> processData(@RequestParam("dataFile") MultipartFile dataFile, @RequestParam("configFile") MultipartFile configFile,
                               RedirectAttributes redirectAttributes) throws IOException {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(configFile.getInputStream());
        return service.processData(dataFile.getInputStream(), configPipeline);
    }

    @PostMapping("/processRawDataWithProfile")
    public List<Group> processRawData(@RequestParam("textData") String textData, @RequestParam("configFile") MultipartFile configFile,
                                   RedirectAttributes redirectAttributes) throws IOException {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(configFile.getInputStream());
        return service.processData(new ByteArrayInputStream(textData.getBytes(StandardCharsets.UTF_8)), configPipeline);
    }

    @PostMapping("/processDataWithProfileConfig")
    public List<Group> processDataWithConfig(@RequestParam("dataFile") MultipartFile dataFile, @RequestParam("profile") String profile,
                                   RedirectAttributes redirectAttributes) throws IOException {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(gson.fromJson(profile, ProcessorPipelineProfile.class));
        return service.processData(dataFile.getInputStream(), configPipeline);
    }

    @PostMapping("/processRawDataWithProfileConfig")
    public List<Group> processRawDataWithConfig(@RequestParam("textData") String textData, @RequestParam("profile") String profile,
                                      RedirectAttributes redirectAttributes) throws IOException {
        ProcessorPipeline configPipeline = service.getPipelineFromConfig(gson.fromJson(profile, ProcessorPipelineProfile.class));
        return service.processData(new ByteArrayInputStream(textData.getBytes(StandardCharsets.UTF_8)), configPipeline);
    }

    @GetMapping("/getConfigurations")
    public List<Configuration> processData() throws IOException {
        Configuration config = new Configuration();
        config.setProcessorName("com.codebarrier.hotpie.processor.FilterProcessor");
        Map<String,String> params = new HashMap<>();
        params.put("isExcludeFilter", "true");
        params.put("lineMatchPattern", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}\\s\\[([^\\]]+)\\].+");
        params.put("matchValue", "pool-11-thread-1");
        config.setProcessorParams(params);
        ArrayList<Configuration> configurations = new ArrayList<>();
        configurations.add(config);
        return configurations;
    }
}
