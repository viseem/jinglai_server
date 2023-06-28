# 晶莱数字化
本地先打包
```bash
mvn clean install package -Dmaven.test.skip=true
```
上传至指定目录
```bash
scp yudao-server/target/yudao-server.jar zs:/home/ubuntu/jinglai
```

运行以下命令
```bash
bash deploy.sh
```