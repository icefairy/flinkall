# flinkall

通过加载module的方式，支持直接在sql-client中对base64数据进行转换，支持如下操作：

+ b64tourl 通过将base64存到seaweedfs中转换成url
+ urltob65 通过下载指定url的内容变换为base64字符串
+ 