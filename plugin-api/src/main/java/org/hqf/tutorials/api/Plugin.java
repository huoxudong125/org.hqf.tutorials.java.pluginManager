package org.hqf.tutorials.api;

public interface Plugin {
    void start();

    long getMemoryUsage();

    int getThreadCount();

    // Optional: Implement methods for resource cleanup or termination if needed

    void stop();

    boolean isStopping();
}
