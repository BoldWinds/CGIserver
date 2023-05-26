# README

This is a simple cgi server, just for Beijing Institute of Technology's course "Computer Network".

## Dev tools

- OS: macos Ventura 13.3.1
- Lang: Java open jdk 19.0.2. & Python 3.11.3
- Database: MySql 8.0.33
- IDE: IntelliJ IDEA 2023.1
- Test tool: Apifox 2.2.38

## Deployment

使用Jar包直接部署服务

## Startup

启动命令

```shell
java -jar WebServer.jar -d webroot -p 8080 -s 10
```

其中：

- -d参数是webroot目录的位置
- -p参数是要监听的端口
- -s参数是线程池的最大线程数

## Use

对应IP+port访问即可，也可使用诸如Postman类的工具