package org.hqf.tutorials.java.plugins;

import org.hqf.tutorials.java.plugin.manager.Plugin;

import java.util.Timer;
import java.util.TimerTask;

public class LoggingPlugin implements Plugin {

    private static final long LOG_INTERVAL_MS = 1000; // Log every second

    private Timer timerTask = new Timer();

    private volatile boolean isStopping = false;

    @Override
    public void start() {

        timerTask.scheduleAtFixedRate(new TimerTask() {
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

    @Override
    public boolean isStopping() {
        return isStopping;
    }

    @Override
    public void stop() {
        isStopping = true;
        // Stop the timer task used for logging
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        // Perform any additional cleanup specific to the plugin
        // (e.g., closing files, releasing resources)
    }

}
