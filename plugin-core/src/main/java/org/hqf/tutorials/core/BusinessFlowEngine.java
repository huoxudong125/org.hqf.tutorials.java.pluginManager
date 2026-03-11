package org.hqf.tutorials.core;

import org.hqf.tutorials.api.business.BusinessFlowContext;
import org.hqf.tutorials.api.business.BusinessModulePlugin;
import org.hqf.tutorials.api.business.BusinessRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

public class BusinessFlowEngine {

    private final Map<String, BusinessModulePlugin> moduleMap = new LinkedHashMap<String, BusinessModulePlugin>();
    private final Map<String, BusinessRule> ruleMap = new LinkedHashMap<String, BusinessRule>();

    public void loadModulesBySpi() {
        ServiceLoader<BusinessModulePlugin> loader = ServiceLoader.load(BusinessModulePlugin.class);
        for (BusinessModulePlugin plugin : loader) {
            moduleMap.put(plugin.getModuleName(), plugin);
            for (BusinessRule rule : plugin.getRules()) {
                if (ruleMap.containsKey(rule.getRuleId())) {
                    throw new IllegalStateException("Duplicate rule id found: " + rule.getRuleId());
                }
                ruleMap.put(rule.getRuleId(), rule);
            }
        }
    }

    public List<String> executeFlow(BusinessFlowContext context) {
        List<BusinessRule> sortedRules = sortRules();
        List<String> executed = new ArrayList<String>();
        for (BusinessRule rule : sortedRules) {
            rule.execute(context);
            executed.add(rule.getRuleId());
            System.out.println("BusinessFlowEngine: executed rule=" + rule.getRuleId() + " module=" + rule.getModuleName());
        }
        return executed;
    }

    public List<BusinessRule> getOrderedRules() {
        return Collections.unmodifiableList(sortRules());
    }

    private List<BusinessRule> sortRules() {
        List<BusinessRule> allRules = new ArrayList<BusinessRule>(ruleMap.values());
        Collections.sort(allRules, Comparator.comparingInt(BusinessRule::getOrder));

        Map<String, Set<String>> dependencyGraph = new HashMap<String, Set<String>>();
        Map<String, Integer> inDegree = new HashMap<String, Integer>();

        for (BusinessRule rule : allRules) {
            dependencyGraph.put(rule.getRuleId(), new HashSet<String>());
            inDegree.put(rule.getRuleId(), 0);
        }

        for (BusinessRule rule : allRules) {
            for (String parentRuleId : rule.runAfterRuleIds()) {
                if (!ruleMap.containsKey(parentRuleId)) {
                    throw new IllegalStateException("Rule dependency not found: " + parentRuleId + " for rule " + rule.getRuleId());
                }
                if (dependencyGraph.get(parentRuleId).add(rule.getRuleId())) {
                    inDegree.put(rule.getRuleId(), inDegree.get(rule.getRuleId()) + 1);
                }
            }
        }

        LinkedList<BusinessRule> queue = new LinkedList<BusinessRule>();
        for (BusinessRule rule : allRules) {
            if (inDegree.get(rule.getRuleId()) == 0) {
                queue.add(rule);
            }
        }

        List<BusinessRule> ordered = new ArrayList<BusinessRule>();
        while (!queue.isEmpty()) {
            Collections.sort(queue, Comparator.comparingInt(BusinessRule::getOrder));
            BusinessRule current = queue.removeFirst();
            ordered.add(current);

            for (String childId : dependencyGraph.get(current.getRuleId())) {
                int degree = inDegree.get(childId) - 1;
                inDegree.put(childId, degree);
                if (degree == 0) {
                    queue.add(ruleMap.get(childId));
                }
            }
        }

        if (ordered.size() != allRules.size()) {
            throw new IllegalStateException("Rule dependency has cycle.");
        }

        return ordered;
    }
}
