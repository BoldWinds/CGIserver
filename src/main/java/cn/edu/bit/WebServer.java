package cn.edu.bit;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer{
    // 服务器套接字
    private ServerSocket serverSocket;
    // 监听端口
    final private int port;
    // 线程池大小
    final private int poolSize;
    // 根目录路径
    final private String rootPath;
    // 线程池
    private ExecutorService threadPool;

    public WebServer(int port, int poolSize,String rootPath) throws IOException {
        this.port = port;
        this.poolSize = poolSize;
        this.rootPath = rootPath;
        this.serverSocket = new ServerSocket(this.port);
        this.threadPool = Executors.newFixedThreadPool(this.poolSize);
    }

    public void listen() {
        try{
            while(true){
                RequestHandler requestHandler = new RequestHandler(serverSocket.accept());
                threadPool.execute(requestHandler);
            }
        }catch (IOException e){
            e.printStackTrace();
            //serverSocket.close();
        }

    }
}
