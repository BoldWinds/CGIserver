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
        HttpResponse httpResponse;
        if (isFound()){
            httpResponse = new HttpResponse(200,"OK",new File(rootPath+request.getUrl()));
        }else {
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
        HttpResponse httpResponse;
        if (isFound()){
            httpResponse =  new HttpResponse(200, "OK", new File(rootPath+request.getUrl()));
            httpResponse.dynamicWeb(extractArg());
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
        HttpResponse httpResponse;
        if(isFound()){
            // 通过不设置content的方法来实现对head请求的响应
            httpResponse = new HttpResponse(200,"OK",new File(rootPath+request.getUrl()));
        }else{
            httpResponse = new HttpResponse(404,"Not Found",new File(rootPath+"/404.html"));
            httpResponse.staticWeb();
        }
        return httpResponse;
    }

    /**
     * Check if the file exists
     * @return true if the file exists
     */
    private boolean isFound(){
        if(request.getUrl().equals("/")){
            request.setUrl("/index.html");
        }
        String url = request.getUrl();
        String filePath = rootPath+url;
        File file = new File(filePath);
        return file.exists();
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