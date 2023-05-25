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
        System.out.println("I get a request!");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            HttpRequest request = getRequest(in);
            System.out.println(request.getMethod()+" "+request.getUrl()+" "+request.getVersion()+" "+request.getHost());
            String response = new ResponseBuilder(request,rootPath).build();
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
        HttpRequest httpRequest = new HttpRequest();
        String request = "";
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            // TODO 验证正确性    读取body
            httpRequest.parse(line);
            request += line + "\n";
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
