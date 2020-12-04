package com.codebarrier.hotpie.configuration;

import com.codebarrier.hotpie.rest.service.PipelineProcessorService;
import com.codebarrier.hotpie.rest.service.ProcessorService;
import com.codebarrier.hotpie.rest.service.ProfileDataStore;
import com.codebarrier.hotpie.rest.service.ProfileFileDataStore;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class HotPieSpringBootConfig {

    @Bean
    public ProcessorService processorService() {
        return new PipelineProcessorService();
    }

    @Bean
    public ProfileDataStore profileDataStore() {
        return new ProfileFileDataStore();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(100));
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));
        return factory.createMultipartConfig();
    }

}
