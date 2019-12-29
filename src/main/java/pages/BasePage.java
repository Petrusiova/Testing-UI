package pages;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.PropertyManager;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BasePage {
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
    public void goTo(String url) {
        chromeDriver.manage().window().maximize();
        chromeDriver.get(url);

    }

    @Step("Проверяем загрузился ли необходимый элемент на странице")
    public void checkElementOnPage(By by){
        WebDriverWait wait = new WebDriverWait(chromeDriver, 40);
        wait.until(ExpectedConditions.elementToBeClickable(by));
        Assert.assertTrue("Элемент" + by + " отсутствует на странице", chromeDriver.findElement(by).isDisplayed());
    }

    @Step("Проверяем загрузился ли необходимый элемент на странице")
    public void checkElementOnPage(WebElement webElement) {
        for (int i = 0; i < 40; i++){
            if (!webElement.isDisplayed()){
                getChromeDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
            else break;
        }

//        WebDriverWait wait = new WebDriverWait(chromeDriver, 35);
//        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        Assert.assertTrue("Элемент" + webElement + " отсутствует на странице", webElement.isDisplayed());
    }

    public WebElement findElement(By by, int timeOut) {
        WebDriverWait wait = new WebDriverWait(chromeDriver, timeOut);
        WebElement webElement = null;

        try {
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception ex) {
        }
        return webElement;
    }


    @After
    @Step("Закрываем браузер")
    public void closeBrowser() {
        if (chromeDriver != null) {
            chromeDriver.close();
            chromeDriver.quit();
        }
    }

    protected void closePreviousWindow(){
        ArrayList<String> tabs = new ArrayList<>(chromeDriver.getWindowHandles());
        chromeDriver.switchTo().window(tabs.get(0));
        chromeDriver.close();
        chromeDriver.switchTo().window(tabs.get(1));
    }
}