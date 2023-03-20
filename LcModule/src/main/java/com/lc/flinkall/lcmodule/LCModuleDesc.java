package com.lc.flinkall.lcmodule;

import org.apache.flink.table.descriptors.ConnectorDescriptorValidator;
import org.apache.flink.table.descriptors.DescriptorProperties;

import java.util.Map;

import static com.lc.flinkall.lcmodule.LCModuleDescriptorValidator.MODULE_LC_VERSION;

public class LCModuleDesc extends ConnectorDescriptorValidator {
    private String lcVersion;

    public LCModuleDesc(String lcVersion) {
        super();
        this.lcVersion = lcVersion;
    }


//    @Override
    protected Map<String, String> toModuleProperties() {
        final DescriptorProperties properties = new DescriptorProperties();

        if (lcVersion != null) {
            properties.putString(MODULE_LC_VERSION, lcVersion);
        }

        return properties.asMap();
    }
}
