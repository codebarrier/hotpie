package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Checkpoint;
import com.codebarrier.hotpie.model.CheckpointConfiguration;
import com.codebarrier.hotpie.model.Data;
import com.codebarrier.hotpie.model.Group;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataComparator implements Comparator<Data> {

    @Override
    public int compare(Data o1, Data o2) {
        if (Long.valueOf(o1.getDataId()) < Long.valueOf(o2.getDataId())) {
            return -1;
        } else if (Long.valueOf(o1.getDataId()) > Long.valueOf(o2.getDataId())) {
            return 1;
        } else {
            return 0;
        }
    }
}

public class SequentialCheckpointProcessor extends DataProcessor {

    private List<CheckpointConfiguration> configs;
    private Integer currentCheckpointIndex = 0;
    private List<Checkpoint> checkpointStatus;


    private Set<Data> allData = new TreeSet<>(new DataComparator());

    public SequentialCheckpointProcessor(List<CheckpointConfiguration> configs) {
        for (CheckpointConfiguration configuration : configs) {
            configuration.setCheckPointIdPattern(Pattern.compile(configuration.getCheckpointIdentificationRegex()));
        }
        this.configs = configs;
    }

    @Override
    public List<Group> process(InputStream source) {
        allData.clear();
        List<Group> data = getAllLinesFromStreamAsGroup(source);
        checkpointStatus = new ArrayList<>();
        currentCheckpointIndex = 0;
        collectAllData(data.get(0));

        for (Data orderedData: allData) {
            verifyCheckpoint(orderedData, getCheckPointToValidate());
        }

        for (int configIndex = currentCheckpointIndex; configIndex < configs.size(); configIndex ++) {
            Checkpoint checkpoint = new Checkpoint();
            checkpoint.setCheckpointHeader(configs.get(configIndex).getCheckpointHeader());
            checkpoint.setCheckpointDescription(configs.get(configIndex).getCheckpointDescription());
            checkpointStatus.add(checkpoint);
        }
        data.get(0).setCheckpoints(checkpointStatus);
        return data;
    }

    @Override
    public List<Group> processGroups(List<Group> sourceGroups) {
        allData.clear();
        checkpointStatus = new ArrayList<>();
        currentCheckpointIndex = 0;
        collectAllData(sourceGroups.get(0));

        for (Data orderedData: allData) {
            verifyCheckpoint(orderedData, getCheckPointToValidate());
        }

        for (int configIndex = currentCheckpointIndex; configIndex < configs.size(); configIndex ++) {
            Checkpoint checkpoint = new Checkpoint();
            checkpoint.setCheckpointHeader(configs.get(configIndex).getCheckpointHeader());
            checkpoint.setCheckpointDescription(configs.get(configIndex).getCheckpointDescription());
            checkpointStatus.add(checkpoint);
        }
        sourceGroups.get(0).setCheckpoints(checkpointStatus);
        return sourceGroups;
    }

    private void collectAllData(Group selectedGroup) {

        if (currentCheckpointIndex >= configs.size()) {
            return;
        }

        if (CollectionUtils.isEmpty(selectedGroup.getGroupData())) {
            for (Group subGroup : selectedGroup.getSubGroups()) {
                collectAllData(subGroup);
            }
        }

        allData.addAll(selectedGroup.getGroupData());
    }

    private CheckpointConfiguration getCheckPointToValidate() {
        if (currentCheckpointIndex >= configs.size() ) {
            return null;
        } else {
            return configs.get(currentCheckpointIndex);
        }
    }

    private void verifyCheckpoint(Data actualLine, CheckpointConfiguration checkpointConfig) {

        if (actualLine.getData().isEmpty() || checkpointConfig == null) {
            return;
        }

        Matcher filterMatcher = checkpointConfig.getCheckPointIdPattern().matcher(actualLine.getData());
        if (filterMatcher.matches()) {
            Checkpoint checkpoint = new Checkpoint();
            checkpoint.setCheckpointHeader(checkpointConfig.getCheckpointHeader());
            checkpoint.setCheckpointDescription(checkpointConfig.getCheckpointDescription());
            checkpoint.setCheckpointReached(true);
            checkpoint.setCheckpointData(actualLine);
            List<String> matchedData = new ArrayList<>();
            for (int groupCnt = 0; groupCnt < filterMatcher.groupCount(); groupCnt++) {
                matchedData.add(filterMatcher.group(groupCnt));
            }
            if (matchedData.size() > 0) {
                checkpoint.setDataExtractedFromCheckpoint(matchedData);
            }
            checkpointStatus.add(checkpoint);
            currentCheckpointIndex++;
        }
    }
}
