package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Group;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class LineDemarkationProcessor extends DataProcessor {

    public LineDemarkationProcessor() {
    }

    public String getLineDemarkationRegex() {
        return lineDemarkationRegex;
    }

    public void setLineDemarkationRegex(String lineDemarkationRegex) {
        this.lineDemarkationRegex = lineDemarkationRegex;
    }

    private String lineDemarkationRegex;
    public static final String LINE_BARRIER = "<-------------Line Break-------------->";
    private Pattern linePattern;

    public LineDemarkationProcessor (String lineBeginningRegexMetcher) {
        this.lineDemarkationRegex = lineBeginningRegexMetcher;
        linePattern = Pattern.compile(lineDemarkationRegex);
    }

    @Override
    public List<Group> process(InputStream source) {
        List<String> data = getAllLinesFromStream(source);
        List<String> output = new ArrayList<>(data.size() * 3);
        for (String line : data) {
            if (linePattern.matcher(line).matches()) {
                output.add(LINE_BARRIER);
            }
            output.add(line);
        }
        output.add(LINE_BARRIER);
        return getAllLinesFromStreamAsGroup(output);
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        return sourceGroups;
    }
}
