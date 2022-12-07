import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.io.*;

public class test {
    @Test
    public void String1() {
        System.out.println(Base64.encode("hello world"));
    }

    @Test
    public void b64test() throws IOException {
        File f = new File("D:\\mydocs\\docs\\lhcz\\bz\\xqkfk\\toDuowei\\toDuowei\\zaff_entrance_record.txt");
        String js = FileUtil.readUtf8String(f);
        JSONObject jo = JSONUtil.parseObj(js);
        String tpb64 = jo.getStr("tp");
        File fimg = new File("d:\\down\\a.jpg");
        FileOutputStream fos = new FileOutputStream(fimg);
        fos.write(Base64.decode(tpb64));
        fos.flush();
        fos.close();
        System.out.println("a.jpg write ok");
    }
}
