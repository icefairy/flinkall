package com.lc.flinkall.lcmodule.funs;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.flink.annotation.Internal;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * 提供三个参数
 * 在sql-client中传参方式为：SET pipeline.global-job-parameters=swftype:jpg,urlsuffix:7d,swhost:'http://192.168.240.14:7777/';
 * String swhost = "http://localhost:8888/"; //seaweedfs的filer地址
 * String swtimeout = "5000"; //保存图片超时时间ms
 * String swftype = "jpg"; //未指定文件名时候自动生成的文件名后缀
 * String urlsuffix=""; //用于添加ttl等参数，例如ttl=10d保留10天
 */
@Internal
public class B64tourlUDF extends ScalarFunction {
    String swhost = "http://localhost:8888/";
    String swtimeout = "5000";
    String swftype = "jpg";
    String urlsuffix = "";

    @Override
    public void open(FunctionContext context) throws Exception {
        swhost = context.getJobParameter("swhost", "http://localhost:8888/");
        swtimeout = context.getJobParameter("swtimeout", "5000");
        swftype = context.getJobParameter("swftype", "jpg");
        urlsuffix = context.getJobParameter("urlsuffix", "");
//        System.out.println(StrUtil.format("swhost:{} swtimeout:{} swftype:{}", swhost, swtimeout, swftype));
    }

    public String eval(String b64) {
        return eval(b64, IdUtil.fastSimpleUUID() + "." + swftype);
    }

    public String eval(String b64, String fn) {
        //判断有没有图片头有的话去掉
        if (b64 ==null || StrUtil.isBlank(b64)) {
            //内容为空忽略
            return "";
        }
        if (b64.startsWith("data")) {
            int nBegin = b64.indexOf("base64,");
            if (nBegin > -1) {
                b64 = StrUtil.subSuf(b64, nBegin + 7);
            }
        }

        HttpRequest req = HttpUtil.createPost(swhost + fn + "?" + urlsuffix);
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
