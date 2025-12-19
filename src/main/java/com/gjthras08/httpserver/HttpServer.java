package com.gjthras08.httpserver;

import com.gjthras08.httpserver.config.Configuration;
import com.gjthras08.httpserver.config.ConfigurationManager;

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
    }
}
