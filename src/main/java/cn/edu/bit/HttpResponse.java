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
     * 调用.pl脚本生成html
     */
    public void dynamicWeb(String[] args){
        // TODO calculate  query
        try{
            ProcessBuilder processBuilder;
            System.out.println(file.getPath());
            if(file.getName().equals("calculator.pl")){
                // 构建Perl命令
                processBuilder = new ProcessBuilder("perl", file.getPath(), args[0], args[1]);
            }else {
                // 构建Perl命令
                processBuilder = new ProcessBuilder("perl", file.getPath(), args[0]);
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
                System.out.println("脚本执行成功，结果为: " + output);
            } else {
                System.out.println("脚本执行失败");
            }
            this.content = output.toString();
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n").append("Content-Type: text/html\r\n").append("\r\n").append(content);
        return sb.toString();
    }
}
