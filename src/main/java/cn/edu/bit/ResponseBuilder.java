package cn.edu.bit;

import java.io.File;

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
        StringBuilder sb = new StringBuilder("HTTP/1.1 200 OK\r\n" +
                                            "Content-Type: text/html\r\n" +
                                            "\r\n");
        switch (request.getMethod()){
            case GET:
                return buildGetResponse().toString();
            default:

        }

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<h1>Hello World!</h1>";
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
        if(file.exists()){
            return new HttpResponse(200,"OK",file);
        }else{
            return new HttpResponse(404,"Not Found",new File(rootPath+"/404.html"));
        }
    }

    /**
     * Build the response for POST method
     * @return response
     */
    /*private HttpResponse buildPostResponse(){
        return new HttpResponse();
    }*/
}
