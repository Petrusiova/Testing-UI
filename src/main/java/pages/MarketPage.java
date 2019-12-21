package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MarketPage extends BasePage {

    @FindBy(xpath = "//div[2]/div[2]/span")
    private WebElement answerNo;

    @FindBy(xpath = "//div[1]/span/input")
    private WebElement region;

    @FindBy(xpath = "//*[contains(text(),'Продолжить с новым регионом')]")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[contains(text(), 'Все категории')]")
    private WebElement allCategories;

    public void anotherCity() {
        Assert.assertTrue("Не найдена кнопка выбора другого города", answerNo.isDisplayed());
        answerNo.click();
    }

    @Step("Смена города на {0}")
    public void changeCity(String city, String fullName) {
        Assert.assertTrue("Не найдено поле для ввода региона", region.isDisplayed());
        region.clear();
        region.sendKeys(city);
        region.click();
        String cityXPath = "//*[contains(text(),'" + fullName + "')]";
        checkElementOnPage(By.xpath(cityXPath));
        WebElement spb = getChromeDriver().findElementByXPath(cityXPath);
        Assert.assertTrue("Не найдено всплывающее поле выбора региона", spb.isDisplayed());
        spb.click();
        region.sendKeys(Keys.ENTER);
    }

    @Step("Change category on {0}")
    public void changeCategory(String category) {
        checkElementOnPage(allCategories);
        allCategories.click();
        WebElement categoryElement = getChromeDriver().findElementByXPath("//*[contains(text(), '" + category + "')]");
        checkElementOnPage(categoryElement);
        categoryElement.click();
    }
}
