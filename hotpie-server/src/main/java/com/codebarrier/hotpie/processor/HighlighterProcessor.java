package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Data;
import com.codebarrier.hotpie.model.Group;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlighterProcessor extends DataProcessor {

    private String highlighterRegex;

    public String getHighlighterRegex() {
        return highlighterRegex;
    }

    public void setHighlighterRegex(String highlighterRegex) {
        this.highlighterRegex = highlighterRegex;
    }

    private Pattern highlighterRegexPattern;

    public HighlighterProcessor() {
    }

    public HighlighterProcessor (String highlighterRegex) {
        this.highlighterRegex = highlighterRegex;
        highlighterRegexPattern = Pattern.compile(highlighterRegex, Pattern.MULTILINE);
    }

    @Override
    public List<Group> process(InputStream source) {
        List<Group> data = getAllLinesFromStreamAsGroup(source);
        for (Group selectedGroup : data) {
            highlightGroup(selectedGroup);
        }
        return data;
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        for (Group selectedGroup : sourceGroups) {
            highlightGroup(selectedGroup);
        }
        return sourceGroups;
    }

    private void highlightGroup(Group selectedGroup) {
        List<Data> data = selectedGroup.getGroupData();

        if (data == null || data.isEmpty()) {
            selectedGroup.clearHighlights();
            for (Group subGroup : selectedGroup.getSubGroups()) {
                highlightGroup(subGroup);
                if (subGroup.getHighlights() != null && subGroup.getHighlights().size() > 0) {
                    selectedGroup.addHighlights(subGroup.getHighlights());
                }
            }
            return;
        }

        for (Data actualLine: data) {
            String highlightedText = getHighlighterMatchingText(actualLine.getData());
            if(!highlightedText.isEmpty()) {
                selectedGroup.addHighlight(highlightedText, actualLine);
            }
        }
    }

    private String getHighlighterMatchingText(String actualLine) {

        if (actualLine.isEmpty()) {
            return "";
        }

        Matcher filterMatcher = highlighterRegexPattern.matcher(actualLine);
        if (filterMatcher.matches()) {
            return filterMatcher.group(1);
        }

        return "";
    }
}
