package org.hqf.tutorials.java.plugins;

import org.hqf.tutorials.java.plugin.manager.Plugin;

import java.util.Timer;
import java.util.TimerTask;

public class LoggingPlugin implements Plugin {

    private static final long LOG_INTERVAL_MS = 1000; // Log every second

    @Override
    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("LoggingPlugin: Logging message at " + System.currentTimeMillis());
            }
        }, 0, LOG_INTERVAL_MS);
    }

    @Override
    public long getMemoryUsage() {
        // Implement logic to calculate memory usage of the plugin
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    @Override
    public int getThreadCount() {
        // Implement logic to count the number of threads used by the plugin
        return Thread.activeCount();
    }
}
