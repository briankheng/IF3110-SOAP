package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
    private Properties prop;

    public PropertiesHandler(String filename) {
        this.prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input != null) {
                this.prop.load(input);
            } else {
                // Handle the case where the file is not found
                System.err.println("Error: Unable to find file " + filename);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String get(String key) {
        return this.prop.getProperty(key);
    }
}