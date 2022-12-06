package com.lc.flinkall.lcmodule.funs;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.flink.annotation.Internal;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

@Internal
public class UrltoB64UDF extends ScalarFunction {
    String swtimeout = "5000";

    @Override
    public void open(FunctionContext context) throws Exception {
        swtimeout = context.getJobParameter("swtimeout", "5000");
    }

    public String eval(String url) {
        HttpRequest req = HttpUtil.createGet(url, true);
        req.timeout(Integer.parseInt(swtimeout));
        try {
            HttpResponse res = req.execute();
            if (res.getStatus() == 200) {
                return Base64.encode(res.bodyBytes());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
