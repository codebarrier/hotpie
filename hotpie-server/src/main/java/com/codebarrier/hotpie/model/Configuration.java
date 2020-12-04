package com.codebarrier.hotpie.model;

import java.util.Map;

public class Configuration {

    private String processorName;
    private Map<String, String> processorParams;

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public Map<String, String> getProcessorParams() {
        return processorParams;
    }

    public void setProcessorParams(Map<String, String> processorParams) {
        this.processorParams = processorParams;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "processorName='" + processorName + '\'' +
                ", processorParams=" + processorParams +
                '}';
    }
}
