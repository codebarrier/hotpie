package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Data;
import com.codebarrier.hotpie.model.Group;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupByProcessor extends DataProcessor {

    GsonBuilder groupBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();
    private Pattern linePattern;
    private final String groupTitle;

    public GroupByProcessor(String groupNameRegex, String groupTitle) {
        linePattern = Pattern.compile(groupNameRegex);
        this.groupTitle = groupTitle;
    }

    public void processGroup(Group selectedGroup) {
        LinkedHashMap<String, List<Data>> groups = new LinkedHashMap<>();
        List<Data> data = selectedGroup.getGroupData();
        selectedGroup.setHighestSeverity(null);

        if (data == null || data.isEmpty()) {
            for (Group subGroup : selectedGroup.getSubGroups()) {
                processGroup(subGroup);
                if (subGroup.getHighestSeverity() == null) {
                    continue;
                } else if (selectedGroup.getHighestSeverity() == null) {
                    selectedGroup.setHighestSeverity(subGroup.getHighestSeverity());
                } else if (selectedGroup.getHighestSeverity().ordinal() < subGroup.getHighestSeverity().ordinal()) {
                    selectedGroup.setHighestSeverity(subGroup.getHighestSeverity());
                }
            }
            return;
        }

        for (Data actualLine: data) {
            if (actualLine.getData().contains(LineDemarkationProcessor.LINE_BARRIER)) {
                continue;
            }
            processGroupByForLine(groups, actualLine);
        }

        List<Group> allGroups = new ArrayList<>();
        for (String group : groups.keySet()) {
            Group groupObj = new Group(group);
            groupObj.addDataObjects(groups.get(group));
            groupObj.setHighestSeverity(getHighestSevInDataSet(groups.get(group)));
            allGroups.add(groupObj);
        }

        selectedGroup.addSubGroups(allGroups);
        selectedGroup.setHighestSeverity(getHighestSevInSubGroups(allGroups));
        selectedGroup.setGroupTitle(this.groupTitle);
        selectedGroup.clearGroupData();

    }

    private SeverityProcessor.SEVERITY getHighestSevInSubGroups(List<Group> subGroups) {
        SeverityProcessor.SEVERITY highest = null;
        for (Group subGroup : subGroups) {
            if (subGroup.getHighestSeverity() != null) {
                if (highest == null) {
                    highest = subGroup.getHighestSeverity();
                } else if (subGroup.getHighestSeverity().ordinal() > highest.ordinal()) {
                    highest = subGroup.getHighestSeverity();
                }
            }
        }
        return highest;
    }

    private SeverityProcessor.SEVERITY getHighestSevInDataSet(List<Data> data) {
        SeverityProcessor.SEVERITY highest = null;
        for (Data datum : data) {
            if (datum.getDataSeverity() != null && !datum.getDataSeverity().isEmpty()) {
                if (highest == null) {
                    highest = SeverityProcessor.SEVERITY.getEnum(datum.getDataSeverity());
                } else if (SeverityProcessor.SEVERITY.getEnum(datum.getDataSeverity()).ordinal() > highest.ordinal()) {
                    highest = SeverityProcessor.SEVERITY.getEnum(datum.getDataSeverity());
                }
            }
        }
        return highest;
    }

    public List<Group> process(InputStream source) {
        List<Group> data = getAllLinesFromStreamAsGroup(source);
        for (Group selectedGroup : data) {
            processGroup(selectedGroup);
        }
        return data;
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        for (Group selectedGroup : sourceGroups) {
            processGroup(selectedGroup);
        }
        return sourceGroups;
    }

    private LinkedHashMap<String, List<Data>> processGroupByForLine(LinkedHashMap<String, List<Data>> groups, final Data line) {
        if (line.getData().isEmpty()) {
            return groups;
        }
        Matcher groupNameMatcher = linePattern.matcher(line.getData());

        String groupName = "Others";
        if (groupNameMatcher.matches()) {
            groupName = groupNameMatcher.group(1);
        }
        groups.computeIfPresent(groupName, (k, v) -> addLineToGroup(v, line));
        groups.computeIfAbsent(groupName, (k) -> addLineToGroup(null, line));

        return groups;
    }

    private List<Data> addLineToGroup (List<Data> lines, Data lineToAdd) {

        if (lines == null) {
            lines = new ArrayList<>();
        }

        lines.add(lineToAdd);
        return lines;
    }
}
