import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;
import pages.MarketPage;
import pages.YandexPage;

public class YandexSearchSteps extends BasePage {

    ChromeDriver chromeDriver = getChromeDriver();

    @Step("Открываем страницу Яндекса")
    public void openYandexAndCheck(){
        YandexPage page = new YandexPage();
        checkStartPage(page, chromeDriver);
        page.setSearch("Яндекс маркет");
        page.redirectToMarket();
    }

    @Step("Проверяем страницу Яндекс.Маркета")
    public void checkMarket(){
        MarketPage market = new MarketPage();
        checkSearchMarketPage(chromeDriver);
        market.anotherCity();
        market.changeCity("сан");
        try {
            Thread.sleep(5000);
            market.choseSpb();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Step("Проверяем открылась ли страница Яндекса") //////
    public void checkStartPage(YandexPage page, ChromeDriver chromeDriver) {
        Assert.assertEquals("Заголовок страницы не соответствует ожидаемому",
                "Яндекс", chromeDriver.getTitle());
        String currentUrl = chromeDriver.getCurrentUrl();
        Assert.assertTrue("Переход на стартовую страницу Яндекса не был выполнен",
                currentUrl.startsWith("https://yandex.ru") || currentUrl.startsWith("https://www.yandex.ru"));
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет") //////
    public void checkSearchMarketPage(ChromeDriver chromeDriver) {
        Assert.assertTrue("Не произошел переход на стартовую станицу Яндекс Маркета",
                chromeDriver.getCurrentUrl().startsWith("https://market.yandex.ru"));
    }
}
