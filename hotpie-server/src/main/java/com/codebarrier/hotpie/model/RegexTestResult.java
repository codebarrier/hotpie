package com.codebarrier.hotpie.model;

import java.util.List;

public class RegexTestResult {

    private String regex;
    private String sampleData;
    private boolean matchSuccessful;
    private List<String> matchedGroups;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getSampleData() {
        return sampleData;
    }

    public void setSampleData(String sampleData) {
        this.sampleData = sampleData;
    }

    public boolean isMatchSuccessful() {
        return matchSuccessful;
    }

    public void setMatchSuccessful(boolean matchSuccessful) {
        this.matchSuccessful = matchSuccessful;
    }

    public List<String> getMatchedGroups() {
        return matchedGroups;
    }

    public void setMatchedGroups(List<String> matchedGroups) {
        this.matchedGroups = matchedGroups;
    }
}
