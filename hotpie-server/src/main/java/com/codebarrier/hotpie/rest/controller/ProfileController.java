package com.codebarrier.hotpie.rest.controller;

import com.codebarrier.hotpie.model.ProcessorPipelineProfile;
import com.codebarrier.hotpie.rest.service.ProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RestController
public class ProfileController {

    @Autowired
    ProfileDataStore profileStore;

    @PostMapping("/saveProfile")
    public void saveProfile(@RequestBody ProcessorPipelineProfile profile,
                            RedirectAttributes redirectAttributes) throws IOException {
        profileStore.deleteProfile(profile);
        profileStore.saveProfile(profile);
    }

    @PostMapping("/deleteProfile")
    public void deleteProfile(@RequestBody ProcessorPipelineProfile profile,
                              RedirectAttributes redirectAttributes) throws IOException {
        profileStore.deleteProfile(profile);
    }

    @GetMapping("/getAllProfiles")
    public List<ProcessorPipelineProfile> getAllProfiles() throws IOException {
        return profileStore.getAllProfiles();
    }
}
