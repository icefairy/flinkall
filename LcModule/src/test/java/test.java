import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lc.flinkall.lcmodule.funs.B64tourlUDF;
import com.sun.imageio.plugins.common.ImageUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class test {
    @Test
    public void b64test() throws IOException {
        File f = new File("D:\\mydocs\\docs\\lhcz\\bz\\xqkfk\\toDuowei\\toDuowei\\zaff_entrance_record.txt");
        String js = FileUtil.readUtf8String(f);
        JSONObject jo = JSONUtil.parseObj(js);
        String tpb64 = jo.getStr("tp");
        File fimg = new File("d:\\down\\aa.jpg");
        FileOutputStream fos = new FileOutputStream(fimg);
        fos.write(Base64.decode(tpb64));
        fos.flush();
        fos.close();
        System.out.println("a.jpg write ok");
        String swhost="http://192.168.240.14:7777/";
        String fn= IdUtil.fastSimpleUUID()+".jpg";
        String urlsuffix="ttl=2d";
        String swtimeout="5000";
        HttpRequest req = HttpUtil.createPost(swhost + fn + "?" + urlsuffix);
        try {
            req.timeout(Integer.parseInt(swtimeout));
            req.form("img", Base64.decode(tpb64), fn);
            HttpResponse res = req.execute();
            if (res.getStatus() >= 200 && res.getStatus() < 300) {
                System.out.println("fileurl:"+swhost + fn);
            } else {
                System.out.println(res.getStatus()+ " "+res.body());
            }
        } catch (Exception ioe) {
            System.out.println(ioe);
        }
        System.out.println("over");
    }
}
