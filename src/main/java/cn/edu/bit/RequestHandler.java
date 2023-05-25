package cn.edu.bit;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("I get a request!");

    }
}
