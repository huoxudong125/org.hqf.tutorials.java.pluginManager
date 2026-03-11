package org.hqf.tutorials.java.plugin;

import org.hqf.tutorials.api.business.BusinessFlowContext;
import org.hqf.tutorials.api.business.BusinessModulePlugin;
import org.hqf.tutorials.api.business.BusinessRule;

import java.util.Collections;
import java.util.List;

public class InventoryModulePlugin implements BusinessModulePlugin {
    @Override
    public String getModuleName() {
        return "inventory";
    }

    @Override
    public List<BusinessRule> getRules() {
        return Collections.<BusinessRule>singletonList(new ReserveStockRule());
    }

    private static class ReserveStockRule implements BusinessRule {
        @Override
        public String getRuleId() {
            return "inventory.reserve-stock";
        }

        @Override
        public String getModuleName() {
            return "inventory";
        }

        @Override
        public int getOrder() {
            return 20;
        }

        @Override
        public List<String> runAfterRuleIds() {
            return Collections.singletonList("sales.create-order");
        }

        @Override
        public void execute(BusinessFlowContext context) {
            Integer quantity = context.get("quantity", Integer.class);
            int available = 8;
            boolean shortage = quantity != null && quantity > available;
            context.put("inventory.available", available);
            context.put("inventory.shortage", shortage);
            if (!shortage) {
                context.put("inventory.reserved", quantity);
            }
        }
    }
}
