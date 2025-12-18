package com.gjthras08.httpserver.config;

public class Configuration {
    private int port;
    private String webroot;
    
    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getWebroot() {
        return this.webroot;
    }
    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
}
