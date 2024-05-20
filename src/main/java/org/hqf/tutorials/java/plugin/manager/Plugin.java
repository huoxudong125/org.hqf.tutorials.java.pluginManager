package org.hqf.tutorials.java.plugin.manager;

public interface Plugin {
    void start();

    long getMemoryUsage();

    int getThreadCount();

    // Optional: Implement methods for resource cleanup or termination if needed
}
