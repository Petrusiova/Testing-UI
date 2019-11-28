package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MarketPage extends BasePage {

    @FindBy(xpath = "//div[2]/div[2]/span")
    private WebElement answerNo;

    @FindBy(xpath = "//div[1]/span/input")
    private WebElement region;

    @FindBy(xpath = "//*[contains(text(),'Санкт-Петербург')]")//!динамически
    private WebElement spb;

    @FindBy(xpath = "//*[contains(text(),'Продолжить с новым регионом')]")
    private WebElement continueBtn;

    public void anotherCity() {
        Assert.assertTrue("Не найдена кнопка выбора другого города", answerNo.isDisplayed());
        answerNo.click();
    }

    @Step("Смена города на {0}")
    public void changeCity(String city){
        Assert.assertTrue("Не найдено поле для ввода региона", region.isDisplayed());
        region.clear();
        region.sendKeys(city);
    }


    public void choseSpb(){//это метод объединить с changeCity
        try {//сделать динамический xpath для города
            spb.click();
        } catch (NoSuchElementException e) {//вместо этого добавить ассерт
            region.click();
            Assert.assertTrue("Не найдено всплывающее поле выбора региона", spb.isDisplayed());
            spb.click();
        }
        region.sendKeys(Keys.ENTER);
    }


    @Step("Проверяем открылась ли страница Яндекс.Маркет") //////
    public void checkSearchMarketPage() {
        Assert.assertTrue("Не произошел переход на стартовую станицу Яндекс Маркета",
                getChromeDriver().getCurrentUrl().startsWith("https://market.yandex.ru"));
    }
}
