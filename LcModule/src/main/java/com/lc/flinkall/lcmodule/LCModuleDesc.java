package com.lc.flinkall.lcmodule;

import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.descriptors.ModuleDescriptor;

import java.util.Map;

import static com.lc.flinkall.lcmodule.LCModuleDescriptorValidator.MODULE_LC_VERSION;
import static com.lc.flinkall.lcmodule.LCModuleDescriptorValidator.MODULE_TYPE_LC;

public class LCModuleDesc extends ModuleDescriptor {
    private String lcVersion;

    public LCModuleDesc(String lcVersion) {
        super(MODULE_TYPE_LC);
        this.lcVersion = lcVersion;
    }


    @Override
    protected Map<String, String> toModuleProperties() {
        final DescriptorProperties properties = new DescriptorProperties();

        if (lcVersion != null) {
            properties.putString(MODULE_LC_VERSION, lcVersion);
        }

        return properties.asMap();
    }
}
