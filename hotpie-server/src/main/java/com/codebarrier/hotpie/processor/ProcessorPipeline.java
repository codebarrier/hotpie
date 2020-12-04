package com.codebarrier.hotpie.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProcessorPipeline {
    private final Queue<Processor> processors;

    public ProcessorPipeline() {
        processors = new ConcurrentLinkedQueue();
    }

    public void addProcessor(Processor processor) {
        processors.add(processor);
    }

    public Queue<Processor> getProcessors() {
        return processors;
    }

    public void clear() {
        processors.clear();
    }
}
