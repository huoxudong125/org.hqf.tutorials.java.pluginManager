package org.hqf.tutorials.java;

import org.apache.commons.cli.*;
import org.hqf.tutorials.java.plugin.manager.PluginManager;
import org.hqf.tutorials.java.plugin.manager.PluginResourceManager;
import org.hqf.tutorials.java.plugins.LoggingPlugin;
import org.hqf.tutorials.java.plugins.ResourceIntensiveProcessingPlugin;

import java.io.IOException;

public class Main {

    private static final PluginManager pluginManager = new PluginManager();

    public static void main(String[] args) {

        pluginManager.loadPlugins();


        // Parse command-line arguments
        Options options = new Options();
        options.addOption("s", "start", true, "Start the specified plugin (ID required)");
        options.addOption("t", "stop", true, "Stop the specified plugin (ID required)");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
            System.exit(1);
        }

        // Process command-line options
        if (cmd.hasOption("s")) {
            String pluginId = cmd.getOptionValue("s");
            startPlugin(pluginId);
        } else if (cmd.hasOption("t")) {
            String pluginId = cmd.getOptionValue("t");
            stopPlugin(pluginId);
        } else {
            System.out.println("Invalid command. Use -h or --help for usage instructions.");
        }

//        loadPluginsBySpi();

//        manualLoadPlugins();
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("PluginManagerDemo: Stopping plugins and exiting...");
    }


    private static void startPlugin(String pluginId) {
        // Implement logic to start the specified plugin
        pluginManager.startPlugin(pluginId);
        System.out.println("Starting plugin: " + pluginId);
    }

    private static void stopPlugin(String pluginId) {
        // Implement logic to stop the specified plugin
        pluginManager.stopPlugin(pluginId);
        System.out.println("Stopping plugin: " + pluginId);
    }

    private static void loadPluginsBySpi() {

        pluginManager.loadPlugins();
        pluginManager.startPlugins();

    }

    private static void manualLoadPlugins() {
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
    }
}