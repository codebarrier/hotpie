package com.codebarrier.hotpie.model;

public class Data {
    public String dataId;
    public String data;
    public String dataSeverity;

    public Data() {
    }

    public Data(String content) {
        this.data = content;
        this.dataId = String.valueOf(System.nanoTime());
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataSeverity() {
        return dataSeverity;
    }

    public void setDataSeverity(String dataSeverity) {
        this.dataSeverity = dataSeverity;
    }
}
