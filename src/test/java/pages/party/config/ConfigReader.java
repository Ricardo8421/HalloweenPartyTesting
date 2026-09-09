package pages.party.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = null;

    public static void initializeConfig(){
        properties = new Properties();

        String env = System.getProperty("env", "dev").toLowerCase();
        String path = "src/test/resources/env." + env + ".properties";

        try(InputStream input = new FileInputStream(path)){
            properties.load(input);
        }catch(IOException ex){
            throw new RuntimeException("The configuration file could not be loaded.");
        }
    }

    public static String getProperty(String key){
        if (properties == null){
            initializeConfig();
        }
        return properties.getProperty(key);
    }
}
