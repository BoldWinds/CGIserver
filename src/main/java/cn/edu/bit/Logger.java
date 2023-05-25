package cn.edu.bit;

public class Logger {

    private String logPath;

    public Logger(String rootPath) {
        this.logPath = rootPath+"/log/log.txt";
    }
}
