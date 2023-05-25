package cn.edu.bit;

import java.io.*;

public class HttpResponse {

    private int statusCode;

    private String statusMessage;

    private File file;

    public HttpResponse(int statusCode, String statusMessage, File file) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.file = file;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HTTP/1.1 "+statusCode+" "+statusMessage+"\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n");
        // read file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();

    }
}
