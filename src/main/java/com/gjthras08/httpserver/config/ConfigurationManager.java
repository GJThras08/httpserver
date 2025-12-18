package com.gjthras08.httpserver.config;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;
    
    private ConfigurationManager() {}
    
    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }
    
    /**
     * Used to load a configuration file by the path provided.
     * Will need to throw exceptions.
     */
    public void loadConfigurationFile(String filePath) {
    
    }
    
    /**
     * returns current loaded configuraiton.
     * if no configruation is loaded, throw exception.
     */
    public void getCurrentConfigruation() {
    
    }
    
}
