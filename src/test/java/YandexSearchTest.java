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
        String link = chromeDriver.getPageSource();
    }

    @Step
    public void driverStatus(){
        Assert.assertFalse(chromeDriver.getCurrentUrl().isEmpty());
    }

    @After
    public void closeBrowser(){
        chromeDriver.close();
        chromeDriver.quit();
    }


}
