package utils;

import java.util.Map;

public class ConfigHandler {
    private static ConfigHandler instance = null;
    private PropertiesHandler ph;
    private final String CONFIG_FILE = "config.properties";
    private final String CONFIG_FILE_DOCKER = "config.docker.properties";

    private ConfigHandler() {
        String useDockerConfig = System.getenv("USE_DOCKER_CONFIG");
        System.out.println("CONFIG: " + useDockerConfig);
        if (useDockerConfig == null || useDockerConfig.equals("false")) {
            this.ph = new PropertiesHandler(CONFIG_FILE);
        } else {
            this.ph = new PropertiesHandler(CONFIG_FILE_DOCKER);
        }
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