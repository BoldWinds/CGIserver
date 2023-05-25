package cn.edu.bit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 一个凑合用的日志类
 * 单例
 */
public class Logger {

    private static Logger instance;

    private String logPath;

    public static synchronized Logger getInstance(String rootPath){
        if(instance == null){
            instance = new Logger(rootPath);
        }
        return instance;
    }

    // 因为在Main函数中Logger类一定被初始化过了，所以这里不需要判断instance是否为null
    public static synchronized Logger getInstance(){
        return instance;
    }

    private Logger(String rootPath){
        this.logPath = rootPath+"/log/log.txt";
    }

    public void log(String log){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logPath, true));
            StringBuilder sb = new StringBuilder();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            sb.append(timestamp).append(" - ").append(log);

            // 将日志写入并关闭流
            writer.write(sb.toString());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(HttpRequest request){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logPath, true));
            StringBuilder sb = new StringBuilder();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            sb.append("[").append(timestamp).append("]  ")
                    .append("\"").append(request.getMethod()).append(" ").append(request.getUrl()).append(" ").append(request.getVersion()).append("\"")
                    .append("  \"").append(request.getUserAgent()).append("\"");

            // 将日志写入并关闭流
            writer.write(sb.toString());
            writer.newLine();
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String rootPath){
        this.logPath = rootPath+"/log/log.txt";
    }
}
