package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Group;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DataProcessor implements Processor {

    public GsonBuilder groupBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();

    public List<String> getAllLinesFromStream(InputStream source) {
        List<String> doc =
                new BufferedReader(new InputStreamReader(source,
                        StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        return doc;
    }

    public List<Group> getAllLinesFromStreamAsGroup(InputStream source) {
        List<String> doc =
                new BufferedReader(new InputStreamReader(source,
                        StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (String line : doc) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }
        List<Group> allGroups = new ArrayList<>();
        try {
            allGroups = groupBuilder.create().fromJson(builder.toString(), new TypeToken<List<Group>>() {
            }.getType());
        } catch (JsonSyntaxException syntaxException) {
            Group group = new Group("All Data");
            group.addData(getCombinedLines(doc));
            allGroups.add(group);
        }
        return allGroups;
    }

    public List<Group> getAllLinesFromStreamAsGroup(List<String> source) {
        List<Group> allGroups = new ArrayList<>();
        Group group = new Group("All Data");
        group.addData(getCombinedLines(source));
        allGroups.add(group);
        return allGroups;
    }

    public List<String> getCombinedLines(List<String> input) {
        StringBuilder lineBuilder = new StringBuilder();
        List<String> builtString = new ArrayList<>();

        if (!input.get(0).contains(LineDemarkationProcessor.LINE_BARRIER)) {
            return input;
        }
        for (String line : input) {
            if (line.isEmpty()) {
                continue;
            } else if (line.contains(LineDemarkationProcessor.LINE_BARRIER)) {
                if (lineBuilder.length() > 0) {
                    builtString.add(lineBuilder.toString().trim());
                    lineBuilder.setLength(0);
                }
            } else {
                lineBuilder.append(line);
                lineBuilder.append(System.lineSeparator());
            }
        }
        return builtString;
    }

    public void publishAllLinesToStream(OutputStream destination, List<String> outputData) {
        try (PrintWriter p = new PrintWriter(destination)) {
            for (String line : outputData) {
                p.println(line);
            }
        }
    }

    public void publishAllLinesToStream(OutputStream destination, String outputData) {
        try (PrintWriter p = new PrintWriter(destination)) {
            p.println(outputData);
        }
    }
}
