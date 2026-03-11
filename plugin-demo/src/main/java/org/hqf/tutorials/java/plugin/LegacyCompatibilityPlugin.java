package org.hqf.tutorials.java.plugin;

import org.hqf.tutorials.api.Plugin;

public class LegacyCompatibilityPlugin implements Plugin {

    private volatile boolean stopping;

    @Override
    public void start() {
        System.out.println("LegacyCompatibilityPlugin started.");
    }

    @Override
    public long getMemoryUsage() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    @Override
    public int getThreadCount() {
        return Thread.activeCount();
    }

    @Override
    public void stop() {
        stopping = true;
    }

    @Override
    public boolean isStopping() {
        return stopping;
    }
}
