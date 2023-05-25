package cn.edu.bit;

/**
 * 一个功能不完全的HttpRequest类
 */
public class HttpRequest {
    enum Method{
        GET,POST,HEAD
    }
    // 方法字段，本次实验我们只实现GET,POST和HEAD
    private Method method;

    private String url;

    private String version;

    private String host;

    private String userAgent;

    private String accept;

    private String contentType;

    private int contentLength;

    private String boundary;

    private String body;

    public HttpRequest() {
    }

    /**
     * 解析请求行
     * @param requestLine
     */
    public void parse(String requestLine){
        switch (requestLine.split(" ")[0]){
            case "GET":
                this.method = Method.GET;
                this.url = requestLine.split(" ")[1];
                this.version = requestLine.split(" ")[2];
                break;
            case "POST":
                this.method = Method.POST;
                this.url = requestLine.split(" ")[1];
                this.version = requestLine.split(" ")[2];
                break;
            case "HEAD":
                this.method = Method.HEAD;
                this.url = requestLine.split(" ")[1];
                this.version = requestLine.split(" ")[2];
                break;
            case "User-Agent:":
                this.userAgent = requestLine.split(" ")[1];
                break;
            case "Accept:":
                this.accept = requestLine.split(" ")[1];
                break;
            case "Host:":
                this.host = requestLine.split(" ")[1];
                break;
            case "Content-Type:":
                this.contentType = requestLine.split(" ")[1];
                this.boundary = requestLine.split("boundary=")[1];
                break;
            case "Content-Length:":
                this.contentLength = Integer.parseInt(requestLine.split(" ")[1]);
                break;
            default:
                return;
        }
    }

    // Getter & Setter

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }
}
