import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static PropertyManager instance;
    private static final Object lock = new Object();
    public static String propertyFilePath = "C:\\Users\\Оля\\test\\resourses\\application.properties";
    private static String driverPath;
    public static Properties props;

    public static PropertyManager getInstance () {
        if (instance == null) {
                instance = new PropertyManager();
//                instance.loadData();

        }
        return instance;
    }

    public void PropertyManager() {
        InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream("application.properties");
        props = new Properties();

        try {
//            File file = new File("C:\\Users\\Оля\\test\\resourses\\application.properties");
            props.load(in);


        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found");
        }

        driverPath = props.getProperty("webdriver.path");

    }

    public String getDriverPath () {
        return driverPath;
    }
}

