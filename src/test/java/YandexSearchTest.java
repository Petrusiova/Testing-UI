import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.YandexMarketPage;

public class YandexSearchTest extends YandexSearchSteps {

    ChromeDriver chromeDriver = getChromeDriver();
    YandexMarketPage page = PageFactory.initElements(chromeDriver, YandexMarketPage.class);


    @Test
    public void open() {
        checkStartPage(page, chromeDriver);
        checkMarketPage(page.setSearch("яндекс маркет"), chromeDriver);
        page.redirectToMarket();
//        driverStatus(chromeDriver);
        page.switchCity("сан");
    }

    @Step
    public void driverStatus(ChromeDriver driver, YandexMarketPage page) {
        Assert.assertTrue(driver.getCurrentUrl().contains("market.yandex.ru"));
    }

    @After
    public void closeBrowser() {
        chromeDriver.close();
        chromeDriver.quit();
    }


}
