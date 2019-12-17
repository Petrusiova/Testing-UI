package pages;

import io.qameta.allure.Step;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.PropertyManager;

import java.io.File;

public class BasePage{
    private static ChromeDriver chromeDriver;

    public BasePage() {
        initWebDriver();
        PageFactory.initElements(chromeDriver, this);
    }

    public ChromeDriver getChromeDriver() {
        return chromeDriver;
    }

    private void initWebDriver() {
        if (chromeDriver == null) {
            File chromeDriverFile = new File(PropertyManager.getInstance().getDriverPath());
            System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
            chromeDriver = new ChromeDriver();
        }
    }

    @Step("Переходим на страницу по ссылке {0}")
    public void goTo(String url){
        chromeDriver.get(url);
    }

    @Step("Переходим на страницу по ссылке {0}")
    public void pageIsLoad(By by){
        WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }


    @After
    @Step("Закрываем браузер")
    public void closeBrowser() {
        if (chromeDriver != null) {
            chromeDriver.close();
            chromeDriver.quit();
        }
    }
}