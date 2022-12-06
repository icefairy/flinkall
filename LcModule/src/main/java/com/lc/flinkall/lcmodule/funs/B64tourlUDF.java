package com.lc.flinkall.lcmodule.funs;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.flink.annotation.Internal;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * 提供三个参数
 * 在sql-client中传参方式为：SET pipeline.global-job-parameters=swhost:'http://192.168.240.14:7777/';
 * String swhost = "http://localhost:8888/";
 * String swtimeout = "5000";
 * String swftype = "jpg";
 */
@Internal
public class B64tourlUDF extends ScalarFunction {
    String swhost = "http://localhost:8888/";
    String swtimeout = "5000";
    String swftype = "jpg";

    @Override
    public void open(FunctionContext context) throws Exception {
        swhost = context.getJobParameter("swhost", "http://localhost:8888/");
        swtimeout = context.getJobParameter("swtimeout", "5000");
        swftype = context.getJobParameter("swftype", "jpg");
    }

    public String eval(String b64) {
        return eval(b64, IdUtil.fastSimpleUUID() + "." + swftype);
    }

    public String eval(String b64, String fn) {
        HttpRequest req = HttpUtil.createPost(swhost + fn);
        try {
            req.timeout(Integer.parseInt(swtimeout));
            req.form("img", Base64.decode(b64), fn);
            HttpResponse res = req.execute();
            if (res.getStatus() >= 200 && res.getStatus() < 300) {
                return swhost + fn;
            } else {
                return "error:" + res.getStatus();
            }
        } catch (Exception ioe) {
            System.out.println(ioe);
        }
        return "";
    }
}
