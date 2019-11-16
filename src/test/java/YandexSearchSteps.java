import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;
import pages.YandexMarketPage;

import java.util.concurrent.TimeUnit;

public class YandexSearchSteps extends BasePage {

    @Step("Проверяем открылась ли страница Яндекса")
    public void checkStartPage(YandexMarketPage page, ChromeDriver chromeDriver) {
        Assert.assertFalse("Chromdriver не был инициализирован", page.getDriverPath().isEmpty());
        Assert.assertEquals("Заголовок страницы не соответствует ожидаемому",
                "Яндекс", chromeDriver.getTitle());
        Assert.assertTrue("Переход на стартовую страницу Яндекса не был выполнен",
                chromeDriver.getCurrentUrl().startsWith("https://yandex.ru"));
        Assert.assertTrue("Не найдена строка для поиска",
                chromeDriver.findElementById("text").isDisplayed());
        Assert.assertTrue("Не найдена кнопка 'Найти'",
                chromeDriver.findElementByClassName("search2__button").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkSearchMarketPage(ChromeDriver chromeDriver) {
        Assert.assertTrue("Не был произведен поиск по ключу 'Яндекс маркет'",
                chromeDriver.getTitle().startsWith("Яндекс маркет"));
        Assert.assertTrue("В поисковой выдаче не найден Яндекс Маркет",
                chromeDriver.findElementByLinkText("Маркет").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkMarketPage(ChromeDriver chromeDriver) {
        Assert.assertTrue("Не произошел переход на стартовую станицу Яндекс Маркета",
                chromeDriver.getCurrentUrl().startsWith("https://market.yandex.ru"));
        Assert.assertTrue("Не найдено всплывающее окно с вопросом о регионе",
                chromeDriver.findElementByXPath("//*[@class='n-region-notification__header']").isDisplayed());
        Assert.assertTrue("Не найдена кнопка выбора другого региона",
                chromeDriver.findElementByXPath("//*[contains(text(), 'Нет, другой')]").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkAnotherCity(ChromeDriver chromeDriver) {
        Assert.assertTrue("Не найдено всплывающее окно выбора другого региона",
                chromeDriver.findElementByXPath("//*[@class='header2-region-popup']").isDisplayed());
        Assert.assertTrue("Не найдено поле ввода региона",
                chromeDriver.findElementByXPath("//div[1]/span/input").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkSpbPage(ChromeDriver chromeDriver) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue("При выборе региона Санкт-Петербург не произошел корректный переход",
                chromeDriver.findElementsByXPath("//*[contains(text(),'Санкт-Петербург')]").size() > 0);
    }
}
