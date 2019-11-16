import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;
import pages.YandexMarketPage;

public class YandexSearchTest extends BasePage {
    ChromeDriver chromeDriver = getChromeDriver();


    @Test
    public void open() {
        YandexMarketPage page = new YandexMarketPage(chromeDriver);
        page.setSearch("яндекс маркет");
        page.redirectToMarket();
        driverStatus(chromeDriver);
    }

    @Step
    public void driverStatus(ChromeDriver driver){
        Assert.assertTrue(driver.getCurrentUrl().contains("market.yandex.ru"));
    }

    @After
    public void closeBrowser(){
        chromeDriver.close();
        chromeDriver.quit();
    }


}
