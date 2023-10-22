package utils;

public class ConfigHandler {
    private static ConfigHandler instance = null;
    private PropertiesHandler ph;
    private final String CONFIG_FILE = "config.properties";

    private ConfigHandler() {
        this.ph = new PropertiesHandler(CONFIG_FILE);
    }

    public static ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    public String get(String key) {
        return this.ph.get(key);
    }

    public PropertiesHandler getPropertyHandler() {
        return this.ph;
    }
}