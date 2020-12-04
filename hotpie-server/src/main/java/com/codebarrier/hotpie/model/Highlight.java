package com.codebarrier.hotpie.model;

public class Highlight {
    public String highlightedData;
    public Data data;

    public Highlight(String highlightedText, Data relevantData) {
        this.highlightedData = highlightedText;
        this.data = relevantData;
    }

    public String getHighlightedData() {
        return highlightedData;
    }

    public void setHighlightedData(String highlightedData) {
        this.highlightedData = highlightedData;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Highlight{" +
                "highlightedData='" + highlightedData + '\'' +
                ", data=" + data +
                '}';
    }
}
