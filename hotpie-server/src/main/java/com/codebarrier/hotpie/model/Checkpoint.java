package com.codebarrier.hotpie.model;

import java.util.List;

public class Checkpoint {

    private String checkpointHeader;
    private List<String> dataExtractedFromCheckpoint;
    private boolean checkpointReached = false;
    private String checkpointDescription;
    private Data checkpointData;

    public String getCheckpointHeader() {
        return checkpointHeader;
    }

    public void setCheckpointHeader(String checkpointHeader) {
        this.checkpointHeader = checkpointHeader;
    }

    public List<String> getDataExtractedFromCheckpoint() {
        return dataExtractedFromCheckpoint;
    }

    public void setDataExtractedFromCheckpoint(List<String> dataExtractedFromCheckpoint) {
        this.dataExtractedFromCheckpoint = dataExtractedFromCheckpoint;
    }

    public boolean isCheckpointReached() {
        return checkpointReached;
    }

    public void setCheckpointReached(boolean checkpointReached) {
        this.checkpointReached = checkpointReached;
    }

    public String getCheckpointDescription() {
        return checkpointDescription;
    }

    public void setCheckpointDescription(String checkpointDescription) {
        this.checkpointDescription = checkpointDescription;
    }

    public Data getCheckpointData() {
        return checkpointData;
    }

    public void setCheckpointData(Data checkpointData) {
        this.checkpointData = checkpointData;
    }
}
