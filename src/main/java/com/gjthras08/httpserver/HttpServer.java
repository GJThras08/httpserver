package com.gjthras08.httpserver;

import com.gjthras08.httpserver.config.Configuration;
import com.gjthras08.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

/**
 *
 * Driver Class for the Http Server
 *
 */

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using Webroot: " + conf.getWebroot());
        
        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();
            
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my simple Java HTTP Server</h1></body></html>";
            
            final String CRLF = "\r\n";
            //HTTP 1.1 Response
                //Status Line Formula: HTTP/[version] [response code] [response message]
            String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF;
            
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
