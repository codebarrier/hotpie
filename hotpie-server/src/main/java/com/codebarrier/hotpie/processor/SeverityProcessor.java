package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Data;
import com.codebarrier.hotpie.model.Group;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeverityProcessor extends DataProcessor {

    private Pattern sevPattern;

    public SeverityProcessor(String severityMatchRegex, SEVERITY severity) {
        this.severityMatchRegex = severityMatchRegex;
        this.severity = severity;
        sevPattern = Pattern.compile(severityMatchRegex);
    }

    public enum SEVERITY {
        LOW,
        MEDIUM,
        HIGH,
        NONE;

        public static SEVERITY getEnum(String value) {
            if ("HIGH".equals(value)) {
                return HIGH;
            } else if ("MEDIUM".equals(value)) {
                return MEDIUM;
            } else if ("LOW".equals(value)) {
                return LOW;
            } else {
                return NONE;
            }
        }
    }

    private String severityMatchRegex;
    private SEVERITY severity;

    public String getSeverityMatchRegex() {
        return severityMatchRegex;
    }

    public void setSeverityMatchRegex(String severityMatchRegex) {
        this.severityMatchRegex = severityMatchRegex;
    }

    public SEVERITY getSeverity() {
        return severity;
    }

    public void setSeverity(SEVERITY severity) {
        this.severity = severity;
    }

    @Override
    public List<Group> process(InputStream source) {
        List<Group> data = getAllLinesFromStreamAsGroup(source);
        applySeverityOnGroup(data.get(0));
        return data;
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        applySeverityOnGroup(sourceGroups.get(0));
        return sourceGroups;
    }

    private void applySeverityOnGroup(Group group) {
        if (group.getGroupData() != null && group.getGroupData().size() > 0) {
            for (Data data: group.getGroupData()) {
                processDataSeverity(data);
                if (!(data.getDataSeverity() == null) && !data.getDataSeverity().isEmpty()) {
                    SEVERITY sev = SEVERITY.valueOf(data.getDataSeverity());
                    SEVERITY highestGrpSev = group.getHighestSeverity();
                    if(group.getHighestSeverity() == null || sev.ordinal() > highestGrpSev.ordinal()) {
                        group.setHighestSeverity(sev);
                    }
                }
            }
        } else {
            for (Group subGroup : group.getSubGroups()) {
                applySeverityOnGroup(subGroup);
                if(subGroup.getHighestSeverity() == null) {
                    continue;
                } else if (group.getHighestSeverity() == null || subGroup.getHighestSeverity().ordinal() > group.getHighestSeverity().ordinal()) {
                    group.setHighestSeverity(subGroup.getHighestSeverity());
                }
            }
        }
    }

    private void processDataSeverity(Data data) {
        if (data.getData().isEmpty()) {
            return;
        }

        Matcher filterMatcher = sevPattern.matcher(data.getData());
        if (filterMatcher.matches()) {
            data.setDataSeverity(this.severity.name());
        }
    }
}
