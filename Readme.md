# flinkall

通过加载module的方式，支持直接在sql-client中对base64数据进行转换，支持如下操作：

+ b64tourl 通过将base64存到seaweedfs中转换成url
+ urltob64 通过下载指定url的内容变换为base64字符串
+ 

## 使用案例
+ 支持的参数
```java
  * 提供三个参数
 * 在sql-client中传参方式为：SET pipeline.global-job-parameters=swftype:jpg,urlsuffix:7d,swhost:'http://192.168.240.14:7777/';
 * String swhost = "http://localhost:8888/"; //seaweedfs的filer地址
 * String swtimeout = "5000"; //保存图片超时时间ms
 * String swftype = "jpg"; //未指定文件名时候自动生成的文件名后缀
 * String urlsuffix=""; //用于添加ttl等参数，例如ttl=10d保留10天
```
+ 将flink-lc-1.13.6.jar复制放到flink的lib文件夹中并重启集群
+ 读取kafka数据将base64数据存到seaweedfs中变成url
```sql
load module lc;
set execution.checkpointing.interval=10s;
SET 'pipeline.name' = 'yhface2db';
SET pipeline.global-job-parameters=swftype=jpg,swhost:'http://192.168.240.14:7777/';
CREATE TABLE kfkyhface (deviceid string,imgdata string,imageid string,ptime as proctime()) WITH ( 'connector' = 'kafka', 'topic' = 'yh_face', 'properties.bootstrap.servers' = '192.168.240.14:9092','properties.group.id' = 'flink2022', 'format'='json','scan.startup.mode' = 'group-offsets');
create table pgyh_face(deviceid string,imgurl string,string imageid,primary key(imageid) not enforced ) with('connector'='jdbc','driver'='org.postgresql.Driver','url'='jdbc:postgresql://192.168.240.14:5432/lhcz','table-name'='yh_face','username'='postgres','password'='password');
insert into pgyh_face(deviceid,imgurl,imageid) select deviceid,b64tourl(imgdata),imageid from kfkyhface;
```