package com.gjthras08.httpserver.core;

import com.gjthras08.httpserver.core.io.WebRootHandler;
import com.gjthras08.httpserver.core.io.WebRootNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    
    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }
    
    @Override
    public void run() {
        try {
            WebRootHandler webRootHandler = new WebRootHandler(webroot);
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                
                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());
                
                HttpConnnectionWorkerThread workerThread = new HttpConnnectionWorkerThread(socket, webRootHandler);
                workerThread.start();
            }
            
        } catch (IOException | WebRootNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("Problem with setting socket", e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }
    }
}
