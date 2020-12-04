package com.codebarrier.hotpie.rest.service;

import com.codebarrier.hotpie.model.ProcessorPipelineProfile;

import java.io.IOException;
import java.util.List;

public interface ProfileDataStore {

    void saveProfile(ProcessorPipelineProfile profile) throws IOException;

    void deleteProfile(ProcessorPipelineProfile profile);

    List<ProcessorPipelineProfile> getAllProfiles();
}
