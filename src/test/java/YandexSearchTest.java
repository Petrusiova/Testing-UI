import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;
import pages.YandexMarketPage;

public class YandexSearchTest extends BasePage {
    ChromeDriver chromeDriver = getChromeDriver();


    @Test
    public void open() {
        chromeDriver.get("http://www.yandex.ru");
        driverStatus();
        searchInYandex();
    }

    public void searchInYandex(){
        YandexMarketPage page = new YandexMarketPage(chromeDriver);
        page.setSearch("Yandex market");
        driverStatus();
        page.redirectToMarket();
        driverStatus();
    }

    @Step
    public void driverStatus(){
        Assert.assertFalse(chromeDriver.getCurrentUrl().isEmpty());
    }

    @After
    public void closeBrowser(){
        chromeDriver.close();
    }


}
