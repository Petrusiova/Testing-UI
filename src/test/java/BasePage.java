import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class BasePage extends PropertyManager{
    private static ChromeDriver chromeDriver;

    public BasePage(){
        initWebDriver();
    }

    public ChromeDriver getChromeDriver(){
//        initWebDriver();
        return chromeDriver;
    }

    private void initWebDriver(){
        if (chromeDriver == null){
            File chromeDriverFile = new File(PropertyManager.getInstance().getDriverPath());
            System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
            chromeDriver = new ChromeDriver();
        }
    }
}
