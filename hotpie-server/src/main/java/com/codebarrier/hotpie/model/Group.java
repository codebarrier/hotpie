package com.codebarrier.hotpie.model;

import com.codebarrier.hotpie.processor.SeverityProcessor;

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Highlight> highlights) {
        this.highlights = highlights;
    }

    private String groupName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    private String groupId;
    private String groupTitle;
    private SeverityProcessor.SEVERITY highestSeverity;
    private List<Checkpoint> checkpoints;

    public SeverityProcessor.SEVERITY getHighestSeverity() {
        return highestSeverity;
    }

    public void setHighestSeverity(SeverityProcessor.SEVERITY highestSeverity) {
        this.highestSeverity = highestSeverity;
    }

    public List<Data> getGroupData() {
        return groupData;
    }

    private List<Data> groupData;

    public List<Group> getSubGroups() {
        return subGroups;
    }

    private List<Group> subGroups;
    private List<Highlight> highlights;

    public Group() {
        this.groupId = String.valueOf(System.nanoTime());
    }

    public Group(String groupName) {
        this.groupName = groupName;
        this.groupId = String.valueOf(System.nanoTime());
        this.groupData = new ArrayList<>();
        this.subGroups = new ArrayList<>();
        this.highlights = new ArrayList<>();
    }

    public void addData(Data data) {
        this.groupData.add(data);
    }

    public void addData(String data) {
        Data dataObj = new Data();
        dataObj.setData(data);
        dataObj.setDataId(String.valueOf(System.nanoTime()));
        this.groupData.add(dataObj);
    }

    public void addData(List<String> data) {
        for (String line: data) {
            Data dataObj = new Data();
            dataObj.setData(line);
            dataObj.setDataId(String.valueOf(System.nanoTime()));
            this.groupData.add(dataObj);
        }
    }

    public void addDataObjects(List<Data> data) {
        this.groupData.addAll(data);
    }


    public void addSubGroup(Group subGroup) {
        subGroups.add(subGroup);
    }



    public void addSubGroups(List<Group> subGroup) {
        subGroups.addAll(subGroup);
    }

    public void clearGroupData() {
        groupData.clear();
    }

    public void addHighlight(String highlightedText, Data relevantData) {
        highlights.add(new Highlight(highlightedText, relevantData));
    }

    public void addHighlights(List<Highlight> highlights) {
        this.highlights.addAll(highlights);
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void clearHighlights() {
        this.highlights.clear();
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupTitle='" + groupTitle + '\'' +
                ", highestSeverity=" + highestSeverity +
                ", checkpoints=" + checkpoints +
                ", groupData=" + groupData +
                ", subGroups=" + subGroups +
                ", highlights=" + highlights +
                '}';
    }
}
