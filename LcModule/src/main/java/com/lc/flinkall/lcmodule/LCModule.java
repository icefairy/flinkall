package com.lc.flinkall.lcmodule;

import com.lc.flinkall.lcmodule.funs.B64tourlUDF;
import org.apache.flink.annotation.Internal;
import org.apache.flink.table.functions.FunctionDefinition;
import org.apache.flink.table.module.Module;

import java.util.*;

@Internal
public class LCModule implements Module {
    private final String lcVersion;
    private String[] functions = new String[]{
            "b64tourl",
            "urltob64"
    };

    public LCModule(String lcVersion) {
        this.lcVersion = lcVersion;
    }

    @Override
    public Set<String> listFunctions() {
        Set<String> funs = new HashSet<>();
        for (int i = 0; i < functions.length; i++) {
            funs.add(functions[i]);
        }
        return funs;
    }

    @Override
    public Optional<FunctionDefinition> getFunctionDefinition(String name) {
        if (name.contentEquals(functions[0])) {
            //swudf_b64tourl
            return Optional.of(new B64tourlUDF());
        }
        return Optional.empty();
    }
}
