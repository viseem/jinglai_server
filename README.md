# 晶莱数字化
本地先打包
```bash
mvn clean install package -Dmaven.test.skip=true
```
上传至指定目录
```bash
scp yudao-server/target/yudao-server.jar zs:/home/ubuntu/jinglai/new.jar
```

运行以下命令
```bash
bash deploy.sh
```
# 注意事项
## 文件上传
这个是在管理后台页面上可以设置阿里云的key，内部已经封装好对应函数，只需配置即可
## 定时器
- 记得打开饲养单的定时器
## 用户
- 必须有邮箱，也可以把审批的邮箱发送的错误屏蔽掉