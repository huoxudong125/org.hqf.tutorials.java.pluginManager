package org.hqf.tutorials.java;

import org.hqf.tutorials.api.business.BusinessFlowContext;
import org.hqf.tutorials.core.BusinessFlowEngine;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        BusinessFlowEngine engine = new BusinessFlowEngine();
        engine.loadModulesBySpi();

        BusinessFlowContext context = new BusinessFlowContext();
        List<String> executionTrace = engine.executeFlow(context);

        System.out.println("===== Rule Execution Trace =====");
        for (String step : executionTrace) {
            System.out.println(step);
        }

        System.out.println("===== Business Context =====");
        for (Map.Entry<String, Object> entry : context.asMap().entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
