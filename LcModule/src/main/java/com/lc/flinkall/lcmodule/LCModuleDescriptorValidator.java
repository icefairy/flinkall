package com.lc.flinkall.lcmodule;

import org.apache.flink.table.descriptors.ConnectorDescriptorValidator;
import org.apache.flink.table.descriptors.DescriptorProperties;

import static org.apache.flink.table.module.CommonModuleOptions.MODULE_TYPE;

public class LCModuleDescriptorValidator extends ConnectorDescriptorValidator {
    public static final String MODULE_TYPE_LC = "lc";
    public static final String MODULE_LC_VERSION = "lc-version";

    @Override
    public void validate(DescriptorProperties properties) {
        super.validate(properties);
        properties.validateValue(String.valueOf(MODULE_TYPE), MODULE_TYPE_LC, false);
        properties.validateString(MODULE_LC_VERSION, true, 1);
    }
}
