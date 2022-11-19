package com.codebarrier.hotpie.rest.service;

import com.codebarrier.hotpie.model.ProcessorPipelineProfile;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileFileDataStore implements ProfileDataStore {

    String userHomeDir = "/store";
    String storeName = "hotpie_datastore";
    Path dataStore = Paths.get(userHomeDir, storeName);
    public Gson gson = new Gson();

    @Override
    public void saveProfile(ProcessorPipelineProfile profile) throws IOException {
        if (Files.notExists(dataStore, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(dataStore);
        }
        Files.write(Paths.get(userHomeDir, storeName, System.nanoTime() + ".profile"), gson.toJson(profile).getBytes(), StandardOpenOption.CREATE_NEW);
    }

    @Override
    public void deleteProfile(ProcessorPipelineProfile profile) {
        if (StringUtils.isEmpty(profile.getProfileName())) {
            return;
        }
        File dataStoreDir = dataStore.toFile();
        File[] allprofiles = dataStoreDir.listFiles();
        if (allprofiles == null) {
            return;
        }
        for (File profileFile : allprofiles) {
            try {
                ProcessorPipelineProfile profileData = gson.fromJson(Files.readString(profileFile.toPath(), StandardCharsets.UTF_8), ProcessorPipelineProfile.class);
                if (profileData.getProfileName().equalsIgnoreCase(profile.getProfileName())) {
                    profileFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ProcessorPipelineProfile> getAllProfiles() {
        List<ProcessorPipelineProfile> allProfiles = new ArrayList<>();
        File dataStoreDir = dataStore.toFile();
        File[] allprofiles = dataStoreDir.listFiles();
        if (allprofiles == null) {
            return allProfiles;
        }
        for (File profile : allprofiles) {
            try {
                ProcessorPipelineProfile profileData = gson.fromJson(Files.readString(profile.toPath(), StandardCharsets.UTF_8), ProcessorPipelineProfile.class);
                allProfiles.add(profileData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allProfiles;
    }
}
