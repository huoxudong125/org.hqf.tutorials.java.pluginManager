package org.hqf.tutorials.java.plugin.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class PluginManager {

    private final Map<String, Plugin> pluginMap = new HashMap<>();


    public void loadPlugins() {
        ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);
        for (Plugin plugin : loader) {
            String pluginId = plugin.getClass().getSimpleName(); // Assuming unique class names
            pluginMap.put(pluginId, plugin);
            System.out.println("PluginManager: Loaded plugin " + pluginId);
        }
    }

    public void startPlugins() {

        pluginMap.entrySet().forEach(entry -> {
            PluginResourceUsage pluginResourceUsage = new PluginResourceUsage(entry.getKey(), entry.getValue());
        });
    }

    public void getPluginInfo(String pluginId) {
        Plugin plugin = pluginMap.get(pluginId);
        if (plugin != null) {
            System.out.println("PluginInfo: Plugin ID: " + pluginId);
            System.out.println("  System.out.println(  Memory Usage: " + plugin.getMemoryUsage() + " bytes");
            System.out.println("  Thread Count: " + plugin.getThreadCount());
        } else {
            System.out.println("PluginInfo: Plugin " + pluginId + " not found.");
        }
    }

    // (Optional) Implement methods for:
    // - Stopping plugins
    // - Unloading plugins
    // - Monitoring plugin resource usage (CPU, memory, etc.)
    // - Implementing resource limits and taking actions based on resource usage (e.g., logging warnings, restarting plugins)
}