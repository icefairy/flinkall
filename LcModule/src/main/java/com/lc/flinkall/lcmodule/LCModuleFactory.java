package com.lc.flinkall.lcmodule;

import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.factories.ModuleFactory;
import org.apache.flink.table.module.Module;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lc.flinkall.lcmodule.LCModuleDescriptorValidator.MODULE_LC_VERSION;
import static com.lc.flinkall.lcmodule.LCModuleDescriptorValidator.MODULE_TYPE_LC;
import static org.apache.flink.table.module.CommonModuleOptions.MODULE_TYPE;

public class LCModuleFactory implements ModuleFactory {
    @Override
    public Module createModule(Map<String, String> map) {
        final DescriptorProperties descProperties = getValidatedProperties(map);

        final String lcVersion =
                descProperties
                        .getOptionalString(MODULE_LC_VERSION).orElse("1");

        return new LCModule(lcVersion);
    }

    @Override
    public Map<String, String> requiredContext() {
        Map<String, String> context = new HashMap<>();
        context.put(String.valueOf(MODULE_TYPE), MODULE_TYPE_LC);
        return context;
    }

    private static DescriptorProperties getValidatedProperties(Map<String, String> properties) {
        final DescriptorProperties descriptorProperties = new DescriptorProperties(true);
        descriptorProperties.putProperties(properties);

        new LCModuleDescriptorValidator().validate(descriptorProperties);

        return descriptorProperties;
    }

    @Override
    public List<String> supportedProperties() {
        return Arrays.asList(MODULE_LC_VERSION);
    }
}
