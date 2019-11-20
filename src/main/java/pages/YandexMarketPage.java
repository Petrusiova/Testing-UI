package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YandexMarketPage extends BasePage {
    private WebDriver webDriver;

    @FindBy(xpath = "//div[2]/div[2]/span")
    private WebElement answerNo;

    @FindBy(xpath = "//div[1]/span/input")
    private WebElement region;

    @FindBy(xpath = "//*[contains(text(),'Санкт-Петербург')]")
    private WebElement spb;

    @FindBy(xpath = "//*[contains(text(),'Продолжить с новым регионом')]")
    private WebElement continueBtn;


    //добавить аннотации + проверки
    public void anotherCity() {
        answerNo.click();
    }

    public void changeCity(String city){
        region.clear();
        region.sendKeys(city);
    }

    public void choseSpb(){
        try {
            spb.click();
        } catch (NoSuchElementException e) {
            region.click();
            spb.click();
        }
        region.sendKeys(Keys.ENTER);
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkSearchMarketPage() {
        Assert.assertTrue("Не был произведен поиск по ключу 'Яндекс маркет'",
                getChromeDriver().getTitle().startsWith("Яндекс маркет"));
        Assert.assertTrue("В поисковой выдаче не найден Яндекс Маркет",
                getChromeDriver().findElementByLinkText("Маркет").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkMarketPage() {
        Assert.assertTrue("Не произошел переход на стартовую станицу Яндекс Маркета",
                getChromeDriver().getCurrentUrl().startsWith("https://market.yandex.ru"));
        Assert.assertTrue("Не найдено всплывающее окно с вопросом о регионе",
                getChromeDriver().findElementByXPath("//*[@class='n-region-notification__header']").isDisplayed());
        Assert.assertTrue("Не найдена кнопка выбора другого региона",
                getChromeDriver().findElementByXPath("//*[contains(text(), 'Нет, другой')]").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkAnotherCity() {
        Assert.assertTrue("Не найдено всплывающее окно выбора другого региона",
                getChromeDriver().findElementByXPath("//*[@class='header2-region-popup']").isDisplayed());
        Assert.assertTrue("Не найдено поле ввода региона",
                getChromeDriver().findElementByXPath("//div[1]/span/input").isDisplayed());
    }

    @Step("Проверяем открылась ли страница Яндекс.Маркет")
    public void checkSpbPage() {
        Assert.assertTrue("При выборе региона Санкт-Петербург не произошел корректный переход",
                getChromeDriver().findElementsByXPath("//*[contains(text(),'Санкт-Петербург')]").size() > 0);
    }

}
