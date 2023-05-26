package cn.edu.bit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder {

    private final HttpRequest request;

    private final String rootPath;

    public ResponseBuilder(HttpRequest request,String rootPath) {
        this.request = request;
        this.rootPath = rootPath;
    }

    /**
     * Build the response
     * @return response
     */
    public String build() {
        return switch (request.getMethod()){
            case GET -> buildGetResponse().toString();
            case POST -> buildPostResponse().toString();
            case HEAD -> buildHeadResponse().toString();
        };
    }

    /**
     * Build the response for GET method
     * @return response
     */
    private HttpResponse buildGetResponse(){
        String url = request.getUrl();
        if(url.equals("/")){
            url = "/index.html";
        }
        String filePath = rootPath+url;
        File file = new File(filePath);
        HttpResponse httpResponse;
        if(file.exists()){
            httpResponse = new HttpResponse(200,"OK",file);
        }else{
            httpResponse = new HttpResponse(404,"Not Found",new File(rootPath+"/404.html"));
        }
        httpResponse.staticWeb();
        return httpResponse;
    }

    /**
     * Build the response for POST method
     * @return response
     */
    private HttpResponse buildPostResponse(){
        String url = request.getUrl();
        String filePath = rootPath+url;
        File file = new File(filePath);
        HttpResponse httpResponse;

        if(file.exists()){
            if (url.endsWith("calculator.py") || url.endsWith("query.py")) {
                httpResponse =  new HttpResponse(200, "OK", file);
                httpResponse.dynamicWeb(extractArg());
            } else {
                httpResponse = new HttpResponse(404,"Not Found",new File(rootPath+"/404.html"));
                httpResponse.staticWeb();
            }
        }else{
            httpResponse = new HttpResponse(404,"Not Found",new File(rootPath+"/404.html"));
            httpResponse.staticWeb();
        }
        return httpResponse;
    }

    /**
     * Build the response for HEAD method
     * @return response
     */
    private HttpResponse buildHeadResponse(){
        String url = request.getUrl();
        if(url.equals("/")){
            url = "/index.html";
        }
        String filePath = rootPath+url;
        File file = new File(filePath);
        HttpResponse httpResponse;
        if(file.exists()){
            // 通过不设置content的方法来实现对head请求的响应
            httpResponse = new HttpResponse(200,"OK",file);
        }else{
            httpResponse = new HttpResponse(404,"Not Found",new File(rootPath+"/404.html"));
        }
        httpResponse.staticWeb();
        return httpResponse;
    }

    /**
     * Extract the arguments from the request body
     * @return arguments
     */
    private String[] extractArg(){
        String body = request.getBody();
        String boundary = request.getBoundary();

        List<String> args = new ArrayList<>();

        String[] lines = body.split("\n");
        for (int i = 0; i < lines.length; i++){
            if (lines[i].contains(boundary) && i+3<lines.length) {
                args.add(lines[i+3]);
            }
        }
        return args.toArray(new String[0]);
    }
}