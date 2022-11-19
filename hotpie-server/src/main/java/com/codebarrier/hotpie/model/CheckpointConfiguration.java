package com.codebarrier.hotpie.model;

import java.util.regex.Pattern;

public class CheckpointConfiguration {

    private String checkpointHeader;
    private String checkpointIdentificationRegex;
    private String checkpointDescription;
    private transient Pattern checkPointIdPattern;

    public String getCheckpointHeader() {
        return checkpointHeader;
    }

    public void setCheckpointHeader(String checkpointHeader) {
        this.checkpointHeader = checkpointHeader;
    }

    public String getCheckpointIdentificationRegex() {
        return checkpointIdentificationRegex;
    }

    public void setCheckpointIdentificationRegex(String checkpointIdentificationRegex) {
        this.checkpointIdentificationRegex = checkpointIdentificationRegex;
    }

    public String getCheckpointDescription() {
        return checkpointDescription;
    }

    public void setCheckpointDescription(String checkpointDescription) {
        this.checkpointDescription = checkpointDescription;
    }

    public Pattern getCheckPointIdPattern() {
        return checkPointIdPattern;
    }

    public void setCheckPointIdPattern(Pattern checkPointIdPattern) {
        this.checkPointIdPattern = checkPointIdPattern;
    }
}
