package org.hqf.tutorials.java.plugin;

import org.hqf.tutorials.api.business.BusinessFlowContext;
import org.hqf.tutorials.api.business.BusinessModulePlugin;
import org.hqf.tutorials.api.business.BusinessRule;

import java.util.Collections;
import java.util.List;

public class ProcurementModulePlugin implements BusinessModulePlugin {
    @Override
    public String getModuleName() {
        return "procurement";
    }

    @Override
    public List<BusinessRule> getRules() {
        return Collections.<BusinessRule>singletonList(new CreatePurchaseRequestRule());
    }

    private static class CreatePurchaseRequestRule implements BusinessRule {

        @Override
        public String getRuleId() {
            return "procurement.create-pr";
        }

        @Override
        public String getModuleName() {
            return "procurement";
        }

        @Override
        public int getOrder() {
            return 30;
        }

        @Override
        public List<String> runAfterRuleIds() {
            return Collections.singletonList("inventory.reserve-stock");
        }

        @Override
        public void execute(BusinessFlowContext context) {
            Boolean shortage = context.get("inventory.shortage", Boolean.class);
            if (!Boolean.TRUE.equals(shortage)) {
                context.put("procurement.request", "NONE");
                return;
            }
            String sku = context.get("sku", String.class);
            Integer quantity = context.get("quantity", Integer.class);
            Integer available = context.get("inventory.available", Integer.class);
            int missing = quantity - available;
            context.put("procurement.request", "PR-" + sku + "-" + missing);
        }
    }
}
