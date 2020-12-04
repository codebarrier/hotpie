package com.codebarrier.hotpie.processor;

import com.codebarrier.hotpie.model.Group;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface Processor {

    List<Group> process(InputStream source);

    List<Group> processGroups(List<Group> sourceGroups);
}
