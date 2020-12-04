package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Group;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class DefaultProcessor extends DataProcessor {

    @Override
    public List<Group> process(InputStream source) {
        List<Group> data = getAllLinesFromStreamAsGroup(source);
        return data;
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        return sourceGroups;
    }
}
