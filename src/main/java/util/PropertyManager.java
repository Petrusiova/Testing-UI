package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static PropertyManager instance;
    private static String driverPath;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
            instance.loadData();
        }
        return instance;
    }

    private void loadData() {
        Properties prop = new Properties();

        try {
            FileInputStream file = new FileInputStream(".\\src\\test\\resources\\application.properties");
            prop.load(file);
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found");
        }
        driverPath = prop.getProperty("webdriver.chrome.driver");
    }

    public String getDriverPath() {
        return driverPath;
    }
}

