package org.hqf.tutorials.core;

import org.hqf.tutorials.api.Plugin;

class PluginResourceUsage {
    private final String pluginId;
    private final Plugin plugin;
    private final long pluginStartTime;
    private final Thread pluginThread;

    public PluginResourceUsage(String pluginId, Plugin plugin) {
        this.pluginId = pluginId;
        this.plugin = plugin;
        this.pluginStartTime = System.currentTimeMillis();

        // Assuming your Plugin interface has methods for:
        // - getMemoryUsage(): returns memory usage of the plugin in bytes
        // - getThreadCount(): returns the number of threads used by the plugin
        // - runInSandbox(Runnable task): executes the provided task in a sandboxed environment

        //        this.pluginThreadId = plugin.runInSandbox(() -> plugin.start());

        // Create a PluginThreadFactory to isolate plugin execution
        PluginThreadFactory pluginThreadFactory = new PluginThreadFactory(pluginId);

        // Use the PluginThreadFactory to create a sandboxed thread
        Thread pluginThread = pluginThreadFactory.newThread(() -> {
            try {
                plugin.start();
            } catch (Exception e) {
                System.err.println("Error starting plugin " + pluginId + ": " + e.getMessage());
            }
        });

        this.pluginThread = pluginThread;
        // Start the sandboxed thread
        pluginThread.start();
    }

    public String getPluginId() {
        return pluginId;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public long getPluginStartTime() {
        return pluginStartTime;
    }

    public Thread getPluginThread() {
        return pluginThread;
    }

    public long getPluginThreadId() {
        return pluginThread.getId();
    }
}
