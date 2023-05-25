package cn.edu.bit;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // default values
        String rootPath = "webroot";
        int port = 8888;
        int poolSize = 10;
        // 读取命令行参数
        for (int i=0;i < args.length;i++) {
            switch (args[i]){
                case "-d":
                    // 设置webroot的路径
                    rootPath = args[++i];
                    break;
                case "-p":
                    // 设置端口号
                    port = Integer.parseInt(args[++i]);
                    break;
                case "-s":
                    // 设置线程池大小
                    poolSize = Integer.parseInt(args[++i]);
                    break;
            }
        }
        // 启动服务器
        try{
            WebServer webServer = new WebServer(port,poolSize,rootPath);
            webServer.listen();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}