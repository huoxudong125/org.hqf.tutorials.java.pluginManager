package org.hqf.tutorials.java.plugin;

import org.hqf.tutorials.api.business.BusinessFlowContext;
import org.hqf.tutorials.api.business.BusinessModulePlugin;
import org.hqf.tutorials.api.business.BusinessRule;

import java.util.Collections;
import java.util.List;

public class WarehouseModulePlugin implements BusinessModulePlugin {
    @Override
    public String getModuleName() {
        return "warehouse";
    }

    @Override
    public List<BusinessRule> getRules() {
        return Collections.<BusinessRule>singletonList(new CreatePickTaskRule());
    }

    private static class CreatePickTaskRule implements BusinessRule {
        @Override
        public String getRuleId() {
            return "warehouse.create-pick-task";
        }

        @Override
        public String getModuleName() {
            return "warehouse";
        }

        @Override
        public int getOrder() {
            return 40;
        }

        @Override
        public List<String> runAfterRuleIds() {
            return Collections.singletonList("inventory.reserve-stock");
        }

        @Override
        public void execute(BusinessFlowContext context) {
            Boolean shortage = context.get("inventory.shortage", Boolean.class);
            if (Boolean.TRUE.equals(shortage)) {
                context.put("warehouse.pick-task", "SKIPPED");
                return;
            }
            String orderId = context.get("orderId", String.class);
            context.put("warehouse.pick-task", "PICK-" + orderId);
        }
    }
}
