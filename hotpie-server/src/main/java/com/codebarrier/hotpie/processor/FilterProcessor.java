package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Data;
import com.codebarrier.hotpie.model.Group;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterProcessor extends DataProcessor {

    private final boolean exclude;
    private final String lineMatchPattern;
    private final String matchValue;
    private boolean useLineBarrier;
    private Pattern linePattern;

    public FilterProcessor(boolean isExcludeFilter, String lineMatchPattern, String matchValue) {
        this.exclude = isExcludeFilter;
        this.lineMatchPattern = lineMatchPattern;
        this.matchValue = matchValue;
        linePattern = Pattern.compile(lineMatchPattern);
    }

    public void filterGroup(Group selectedGroup) {
        List<Data> data = selectedGroup.getGroupData();

        if (data == null || data.isEmpty()) {
            for (Group subGroup : selectedGroup.getSubGroups()) {
                filterGroup(subGroup);
            }
            return;
        }

        if (data.size() > 0 && data.get(0).getData().equals(LineDemarkationProcessor.LINE_BARRIER)) {
            useLineBarrier = true;
        }

        List<Data> filteredData = new ArrayList<>();

        if (useLineBarrier) {
            List<Data> lineBuilder = new ArrayList<>();
            for (Data actualLine: data) {
                if (actualLine.getData().equals(LineDemarkationProcessor.LINE_BARRIER)) {
                    if(isFilterMatching(lineBuilder)) {
                        if (!exclude) {
                            addLineToFilter(filteredData, lineBuilder);
                        }
                    } else {
                        addLineToFilter(filteredData, lineBuilder);
                    }
                    lineBuilder.clear();
                } else {
                    lineBuilder.add(actualLine);
                }
            }
        } else {
            for (Data actualLine: data) {
                if (exclude) {
                    if(!isFilterMatching(actualLine)) {
                        addLineToFilter(filteredData, actualLine);
                    }
                } else {
                    if(isFilterMatching(actualLine)) {
                        addLineToFilter(filteredData, actualLine);
                    }
                }
            }
        }

        selectedGroup.clearGroupData();
        selectedGroup.addDataObjects(filteredData);
    }

    private boolean isFilterMatching(List<Data> actualLine) {

        if (actualLine.isEmpty()) {
            return false;
        }

        for (Data line: actualLine) {
            if (isFilterMatching(line)) {
                return true;
            }
        }

        return false;
    }

    private boolean isFilterMatching(Data actualLine) {

        if (actualLine.getData().isEmpty()) {
            return false;
        }

        Matcher filterMatcher = linePattern.matcher(actualLine.getData());
        if (filterMatcher.matches() && matchValue.equals(filterMatcher.group(1))) {
            return true;
        }

        return false;
    }

    public List<Group> process(InputStream source) {
        List<Group> data = getAllLinesFromStreamAsGroup(source);
        for (Group selectedGroup : data) {
            filterGroup(selectedGroup);
        }
        return data;
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        for (Group selectedGroup : sourceGroups) {
            filterGroup(selectedGroup);
        }
        return sourceGroups;
    }

    private List<Data> addLineToFilter (List<Data> lines, List<Data> linesToAdd) {

        if (lines == null) {
            lines = new ArrayList<>();
        }

        if (useLineBarrier) {
            lines.add(new Data(LineDemarkationProcessor.LINE_BARRIER));
        }

        lines.addAll(linesToAdd);
        return lines;
    }

    private List<Data> addLineToFilter (List<Data> lines, Data lineToAdd) {

        if (lines == null) {
            lines = new ArrayList<>();
        }

        if (useLineBarrier) {
            lines.add(new Data(LineDemarkationProcessor.LINE_BARRIER));
        }

        lines.add(lineToAdd);
        return lines;
    }
}
