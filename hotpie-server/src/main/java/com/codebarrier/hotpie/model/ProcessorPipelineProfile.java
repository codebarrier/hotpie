package com.codebarrier.hotpie.model;

import java.util.List;

public class ProcessorPipelineProfile {

    String profileName;
    String profileDescription;
    LineDemarcationProcessor lineDemarcation;
    List<FilterProcessor> filters;
    List<HighlightConfig> highlights;
    List<SeverityConfig> severities;
    List<CheckpointConfiguration> checkpoints;
    List<GroupConfig> groups;

    public LineDemarcationProcessor getLineDemarcation() {
        return lineDemarcation;
    }

    public void setLineDemarcation(LineDemarcationProcessor lineDemarcation) {
        this.lineDemarcation = lineDemarcation;
    }

    public List<FilterProcessor> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterProcessor> filters) {
        this.filters = filters;
    }

    public List<HighlightConfig> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<HighlightConfig> highlights) {
        this.highlights = highlights;
    }

    public List<SeverityConfig> getSeverities() {
        return severities;
    }

    public void setSeverities(List<SeverityConfig> severities) {
        this.severities = severities;
    }

    public List<CheckpointConfiguration> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<CheckpointConfiguration> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public List<GroupConfig> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupConfig> groups) {
        this.groups = groups;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
}
