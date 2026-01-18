package com.gjthras08.httpserver.core;

import com.gjthras08.http.*;
import com.gjthras08.httpserver.core.io.ReadFileException;
import com.gjthras08.httpserver.core.io.WebRootHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpConnnectionWorkerThread extends Thread{
    private Socket clientSocket;
    private final WebRootHandler webRootHandler;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnnectionWorkerThread.class);
    
    public HttpConnnectionWorkerThread(Socket clientSocket, WebRootHandler webRootHandler) {
        this.clientSocket = clientSocket;
        this.webRootHandler = webRootHandler;
    }
    @Override
    public void run() {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream()) {
            
            // 1️⃣ Parse HTTP request
            HttpParser parser = new HttpParser();
            HttpRequest request;
            try {
                request = parser.parseHttpRequest(input);
            } catch (HttpParsingException e) {
                sendErrorResponse(output, e.getErrorCode());
                return;
            }
            
            // 2️⃣ Handle GET request
            if (request.getMethod() != HttpMethod.GET) {
                sendErrorResponse(output, HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                return;
            }
            
            // 3️⃣ Use WebRootHandler to get file
            String path = request.getRequestTarget();
            if (path.equals("/")) {
                path = "/index.html"; // default page
            }
            
            byte[] body;
            String mimeType;
            try {
                body = webRootHandler.getFileByteArrayData(path);
                mimeType = webRootHandler.getFileMimeType(path);
            } catch (FileNotFoundException e) {
                sendErrorResponse(output, HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND);
                return;
            } catch (ReadFileException e) {
                sendErrorResponse(output, HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
                return;
            }
            
            // 4️⃣ Send response
            String statusLine = "HTTP/1.1 200 OK\r\n";
            String headers = "Content-Type: " + mimeType + "\r\n" +
                    "Content-Length: " + body.length + "\r\n" +
                    "Connection: close\r\n\r\n";
            
            output.write(statusLine.getBytes(StandardCharsets.US_ASCII));
            output.write(headers.getBytes(StandardCharsets.US_ASCII));
            output.write(body);
            output.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
    
    private void sendErrorResponse(OutputStream output, HttpStatusCode code) throws IOException {
        String statusLine = "HTTP/1.1 " + code.getCode() + " " + code.getReason() + "\r\n";
        String headers = "Content-Length: 0\r\nConnection: close\r\n\r\n";
        output.write(statusLine.getBytes(StandardCharsets.US_ASCII));
        output.write(headers.getBytes(StandardCharsets.US_ASCII));
        output.flush();
    }
}
