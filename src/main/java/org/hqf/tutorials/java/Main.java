package org.hqf.tutorials.java;

import org.hqf.tutorials.java.plugin.manager.PluginResourceManager;
import org.hqf.tutorials.java.plugins.LoggingPlugin;
import org.hqf.tutorials.java.plugins.ResourceIntensiveProcessingPlugin;

public class Main {
    public static void main(String[] args) {

        // Create an instance of the PluginResourceManager
        PluginResourceManager pluginResourceManager = new PluginResourceManager();

        // Create instances of your plugins
        LoggingPlugin loggingPlugin = new LoggingPlugin();
        ResourceIntensiveProcessingPlugin resourceIntensiveProcessingPlugin = new ResourceIntensiveProcessingPlugin();

        // Register the plugins with the PluginResourceManager
        pluginResourceManager.registerPlugin("logging-plugin", loggingPlugin);
        pluginResourceManager.registerPlugin("resource-intensive-plugin", resourceIntensiveProcessingPlugin);

        // Let the plugins run for 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Unregister the plugins (optional)
        // pluginResourceManager.unregisterPlugin("logging-plugin");
        // pluginResourceManager.unregisterPlugin("resource-intensive-plugin");

        System.out.println("PluginManagerDemo: Stopping plugins and exiting...");
    }
}