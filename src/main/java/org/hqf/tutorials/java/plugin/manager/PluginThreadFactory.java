package org.hqf.tutorials.java.plugin.manager;

import java.util.concurrent.ThreadFactory;

class PluginThreadFactory implements ThreadFactory {
    private final String pluginId;

    public PluginThreadFactory(String pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("PluginThread-" + pluginId);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Uncaught exception in plugin thread " + pluginId + ": " + e.getMessage());
        });
        return thread;
    }
}
