package org.hqf.tutorials.api.business;

import java.util.List;

public interface BusinessModulePlugin {

    String getModuleName();

    List<BusinessRule> getRules();
}
