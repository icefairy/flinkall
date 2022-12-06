package com.lc.flinkall.lcmodule;

import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.descriptors.ModuleDescriptorValidator;

public class LCModuleDescriptorValidator extends ModuleDescriptorValidator {
    public static final String MODULE_TYPE_LC = "lc";
    public static final String MODULE_LC_VERSION = "lc-version";

    @Override
    public void validate(DescriptorProperties properties) {
        super.validate(properties);
        properties.validateValue(MODULE_TYPE, MODULE_TYPE_LC, false);
        properties.validateString(MODULE_LC_VERSION, true, 1);
    }
}
