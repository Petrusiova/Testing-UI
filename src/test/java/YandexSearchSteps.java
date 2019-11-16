import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;
import pages.YandexMarketPage;

public class YandexSearchSteps extends BasePage {

    @Step("ѕровер€ем открылась ли страница яндекса")
    public void checkStartPage(YandexMarketPage page, ChromeDriver chromeDriver) {
        Assert.assertFalse("Chromdriver не был инициализирован", page.getDriverPath().isEmpty());
        Assert.assertEquals("«аголовок страницы не соответствует ожидаемому",
                "яндекс", chromeDriver.getTitle());
        Assert.assertTrue("ѕереход на стартовую страницу яндекса не был выполнен",
                chromeDriver.getCurrentUrl().startsWith("https://yandex.ru"));
    }

    @Step("ѕровер€ем открылась ли страница яндекс.ћаркет")
    public void checkMarketPage(YandexMarketPage page, ChromeDriver chromeDriver) {
        Assert.assertTrue("Ќе был произведен поиск по ключу 'яндекс маркет'",
                chromeDriver.getTitle().startsWith("яндекс маркет"));

    }
}
