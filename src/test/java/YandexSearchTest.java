import org.junit.After;
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
        page.setSearch("яндекс маркет");
        checkSearchMarketPage(chromeDriver);
        page.redirectToMarket();
        checkMarketPage(chromeDriver);
        page.anotherCity();
        checkAnotherCity(chromeDriver);
        page.changeCity("сан");
        page.choseSpb();
        checkSpbPage(chromeDriver);

    }

    @After
    public void closeBrowser() {
        chromeDriver.close();
        chromeDriver.quit();
    }


}
