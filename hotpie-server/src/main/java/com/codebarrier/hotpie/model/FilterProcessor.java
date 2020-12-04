package com.codebarrier.hotpie.model;

public class FilterProcessor {

    public boolean isExclude;
    public String filterRegex;
    public String filterValue;

    public boolean isExclude() {
        return isExclude;
    }

    public void setExclude(boolean exclude) {
        isExclude = exclude;
    }

    public String getFilterRegex() {
        return filterRegex;
    }

    public void setFilterRegex(String filterRegex) {
        this.filterRegex = filterRegex;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}
