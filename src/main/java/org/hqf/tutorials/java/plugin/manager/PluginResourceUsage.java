package org.hqf.tutorials.java.plugin.manager;

class PluginResourceUsage {
    private final String pluginId;
    private final Plugin plugin;
    private final long pluginStartTime;
    private final long pluginThreadId;

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

        this.pluginThreadId = pluginThread.getId();
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

    public long getPluginThreadId() {
        return pluginThreadId;
    }
}
