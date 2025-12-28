package com.gjthras08.httpserver;

import com.gjthras08.httpserver.config.Configuration;
import com.gjthras08.httpserver.config.ConfigurationManager;
import com.gjthras08.httpserver.core.ServerListenerThread;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * Driver Class for the Http Server
 *
 */

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    
    public static void main(String[] args) {

        LOGGER.info("Server Starting...");
        
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        
        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using Webroot: " + conf.getWebroot());
        
        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
