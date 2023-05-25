package cn.edu.bit;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{

    final private Socket socket;

    final private String rootPath;

    public RequestHandler(Socket socket,String path) {
        this.socket = socket;
        this.rootPath = path;
    }

    @Override
    public void run() {
        //System.out.println("I get a request!");
        try {
            // 创建输入输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 读取请求
            HttpRequest request = getRequest(in);
            // 写日志
            Logger.getInstance().log(request);
            // 构建响应
            String response = new ResponseBuilder(request,rootPath).build();
            // 发回响应
            sendResponse(response,out);

            // 关闭流和socket
            in.close();
            out.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Get the request from the client
     * @return request
     * @throws IOException
     */
    private HttpRequest getRequest(BufferedReader in) throws IOException {
        // TODO 测试完成之后删除request
        HttpRequest httpRequest = new HttpRequest();
        StringBuilder request = new StringBuilder("");
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            httpRequest.parse(line);
            // TODO delete
            request.append(line).append("\n");
        }

        // 读取请求主体部分
        if (httpRequest.getContentType() != null && httpRequest.getContentLength() != 0) {
            int contentLength = httpRequest.getContentLength();
            StringBuilder sb = new StringBuilder();
            char[] bodyBuffer = new char[contentLength];
            int bytesRead = in.read(bodyBuffer, 0, contentLength);
            if (bytesRead > 0) {
                sb.append(bodyBuffer, 0, bytesRead);
            }
            httpRequest.setBody(sb.toString());
            request.append(sb.toString());
        }
        //System.out.println(request);
        return httpRequest;
    }

    /**
     * Send the response to the client
     * @param response
     * @throws IOException
     */
    private void sendResponse(String response, BufferedWriter out) throws IOException{
        out.write(response);
        out.flush();
    }
}
