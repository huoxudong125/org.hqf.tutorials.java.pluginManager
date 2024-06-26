package org.hqf.tutorials.java.plugin;


import org.hqf.tutorials.api.Plugin;

public class ResourceIntensiveProcessingPlugin implements Plugin {

    private static final int PROCESSING_ITERATIONS = 100;

    private volatile boolean isStopping = false;

    @Override
    public void start() {
        for (int i = 0; i < PROCESSING_ITERATIONS; i++) {
            // Perform resource-intensive processing tasks
            // (This could involve complex calculations, file I/O, or other CPU/memory-intensive operations)
            double log = Math.log(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(String.format("Processing iteration %d with log value %f", (long)i, log));
        }
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
    public void stop() {
        isStopping = true;
        // No specific actions needed for this plugin since it doesn't hold resources
    }

    @Override
    public boolean isStopping() {
        return false;
    }
}
