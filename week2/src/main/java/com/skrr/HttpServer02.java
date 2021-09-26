package com.skrr;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer02 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8802);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(()->{
                try {
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                    pw.println("HTTP/1.1 200 OK");
                    pw.println("Content-Type:text/html;charset=utf-8");
                    String body = "hi, sb";
                    pw.println("Content-Length:" + body.getBytes().length);
                    pw.println();
                    pw.write(body);
                    pw.close();
                    socket.close();
                } catch (Exception e) {}
            }).start();
        }
    }
}
