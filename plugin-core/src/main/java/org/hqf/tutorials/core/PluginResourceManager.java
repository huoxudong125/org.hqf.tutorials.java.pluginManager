package org.hqf.tutorials.core;

import org.hqf.tutorials.api.Plugin;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.*;

public class PluginResourceManager {

    private static final int MAX_CPU_USAGE_PERCENTAGE = 5;
    private static final long MAX_MEMORY_USAGE_BYTES = 1024 * 1024 * 1024; // 1GB
    private static final long MAX_THREAD_COUNT = 10;

    private final Map<String, PluginResourceUsage> pluginResourceUsageMap = new ConcurrentHashMap<>();
    private final ExecutorService resourceMonitoringExecutorService = Executors.newSingleThreadExecutor();

    public void registerPlugin(String pluginId, Plugin plugin) {
        pluginResourceUsageMap.put(pluginId, new PluginResourceUsage(pluginId, plugin));
        startResourceMonitoring();
    }

    public void unregisterPlugin(String pluginId) {
        pluginResourceUsageMap.remove(pluginId);
        stopResourceMonitoring();
    }

    private void startResourceMonitoring() {
        resourceMonitoringExecutorService.submit(() -> {
            while (true) {
                monitorPluginResourceUsage();
                try {
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }



    private void stopResourceMonitoring() {
        resourceMonitoringExecutorService.shutdown();
        try {
            if (!resourceMonitoringExecutorService.awaitTermination(10, TimeUnit.SECONDS)) {
                resourceMonitoringExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void monitorPluginResourceUsage() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        for (Map.Entry<String, PluginResourceUsage> entry : pluginResourceUsageMap.entrySet()) {
            String pluginId = entry.getKey();
            PluginResourceUsage pluginResourceUsage = entry.getValue();

            long cpuUsage = threadMXBean.getThreadCpuTime(pluginResourceUsage.getPluginThreadId());
            long memoryUsage = pluginResourceUsage.getPlugin().getMemoryUsage();
            int threadCount = pluginResourceUsage.getPlugin().getThreadCount();

            boolean exceedingCPUUsageLimit = (cpuUsage / pluginResourceUsage.getPluginStartTime()) > (MAX_CPU_USAGE_PERCENTAGE / 100.0);
            boolean exceedingMemoryUsageLimit = memoryUsage > MAX_MEMORY_USAGE_BYTES;
            boolean exceedingThreadCountLimit = threadCount > MAX_THREAD_COUNT;

            if (exceedingCPUUsageLimit || exceedingMemoryUsageLimit || exceedingThreadCountLimit) {
                System.err.println("Plugin " + pluginId + " is exceeding resource limits:");
                if (exceedingCPUUsageLimit) {
                    System.err.println("CPU Usage: " + (cpuUsage / pluginResourceUsage.getPluginStartTime()) + "% (Max: " + MAX_CPU_USAGE_PERCENTAGE + "%)");
                }
                if (exceedingMemoryUsageLimit) {
                    System.err.println("Memory Usage: " + memoryUsage + " bytes (Max: " + MAX_MEMORY_USAGE_BYTES + " bytes)");
                }
                if (exceedingThreadCountLimit) {
                    System.err.println("Thread Count: " + threadCount + " (Max: " + MAX_THREAD_COUNT + ")");
                }

                // Take appropriate action, such as terminating the plugin or reducing its resource allocation
                // For demonstration purposes, we'll just print a warning
                System.err.println("Warning: Plugin " + pluginId + " may be impacting main service stability. Consider taking action.");
            }
        }
    }


}