package cn.edu.bit;

import java.io.*;

public class HttpResponse {

    private int statusCode;

    private String statusMessage;

    private File file;

    private String content;

    public HttpResponse(int statusCode, String statusMessage, File file) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.file = file;
    }

    /**
     * 根据file字段对应.html文件生成静态页面
     */
    public void staticWeb(){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.content = sb.toString();
    }

    /**
     * 调用python脚本生成html
     * @param args 脚本参数
     */
    public void dynamicWeb(String[] args){
        try{
            ProcessBuilder processBuilder;
            System.out.println(file.getPath());
            if(file.getName().equals("calculator.py")){
                processBuilder = new ProcessBuilder("python3", file.getPath(), args[0], args[1]);
            } else {
                System.out.println(args[0]);
                processBuilder = new ProcessBuilder("python3", file.getPath(), args[0]);
            }
            // 启动进程
            Process process = processBuilder.start();
            // 获取进程的输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 读取输出
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            // 等待进程执行完毕
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 脚本执行成功
                this.content = output.toString();
            } else {
                // 脚本执行失败
                this.content = "Error";
            }

        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (this.content != null){
            sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n").append("Content-Type: text/html\r\n").append("\r\n").append(content);
        }else {
            sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n").append("Content-Type: text/html\r\n").append("\r\n");
        }
        return sb.toString();
    }
}
