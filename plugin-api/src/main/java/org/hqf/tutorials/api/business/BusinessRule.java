package org.hqf.tutorials.api.business;

import java.util.Collections;
import java.util.List;

public interface BusinessRule {

    String getRuleId();

    String getModuleName();

    int getOrder();

    default List<String> runAfterRuleIds() {
        return Collections.emptyList();
    }

    void execute(BusinessFlowContext context);
}
