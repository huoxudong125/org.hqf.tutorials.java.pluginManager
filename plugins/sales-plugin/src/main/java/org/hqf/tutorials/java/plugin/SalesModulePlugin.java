package org.hqf.tutorials.java.plugin;

import org.hqf.tutorials.api.business.BusinessFlowContext;
import org.hqf.tutorials.api.business.BusinessModulePlugin;
import org.hqf.tutorials.api.business.BusinessRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SalesModulePlugin implements BusinessModulePlugin {
    @Override
    public String getModuleName() {
        return "sales";
    }

    @Override
    public List<BusinessRule> getRules() {
        return Arrays.<BusinessRule>asList(new CreateOrderRule(), new PublishOrderRule());
    }

    private static class CreateOrderRule implements BusinessRule {
        @Override
        public String getRuleId() {
            return "sales.create-order";
        }

        @Override
        public String getModuleName() {
            return "sales";
        }

        @Override
        public int getOrder() {
            return 10;
        }

        @Override
        public void execute(BusinessFlowContext context) {
            context.put("orderId", "SO-2026-0001");
            context.put("sku", "SKU-APPLE-01");
            context.put("quantity", 12);
            context.put("warehouse", "WH-SH-001");
        }
    }

    private static class PublishOrderRule implements BusinessRule {
        @Override
        public String getRuleId() {
            return "sales.publish-order-result";
        }

        @Override
        public String getModuleName() {
            return "sales";
        }

        @Override
        public int getOrder() {
            return 100;
        }

        @Override
        public List<String> runAfterRuleIds() {
            return Collections.singletonList("warehouse.create-pick-task");
        }

        @Override
        public void execute(BusinessFlowContext context) {
            String orderId = context.get("orderId", String.class);
            Boolean shortage = context.get("inventory.shortage", Boolean.class);
            String message = Boolean.TRUE.equals(shortage)
                    ? "订单" + orderId + "已创建，库存不足，等待采购补货"
                    : "订单" + orderId + "已创建，仓库拣货任务已下发";
            context.put("sales.notice", message);
        }
    }
}
