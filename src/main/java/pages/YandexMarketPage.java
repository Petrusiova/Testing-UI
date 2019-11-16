package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

public class YandexMarketPage extends BasePage {
    private WebDriver webDriver;

    @FindBy(id = "text")
    private WebElement resultStats;

    @FindBy(className = "search2__button")
    private WebElement searchButton;

    @FindBy(linkText = "Маркет")
    private WebElement pointButton;

    @FindBy(xpath = "//div[2]/div[2]/span")
    private WebElement answerNo;

    @FindBy(xpath = "//div[1]/span/input")
    private WebElement region;

    @FindBy(xpath = "//*[contains(text(),'Санкт-Петербург')]")
    private WebElement spb;

    @FindBy(xpath = "//*[contains(text(),'Продолжить с новым регионом')]")
    private WebElement continueBtn;

    public YandexMarketPage(final WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriver.get("http://www.yandex.ru");
    }

    public YandexMarketPage setSearch(String market) {
        resultStats.clear();
        resultStats.sendKeys(market);
        searchButton.click();
        return this;
    }

    public void redirectToMarket() {
        pointButton.click();
        ArrayList<String> tabs2 = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs2.get(0));
        webDriver.close();
        webDriver.switchTo().window(tabs2.get(1));
    }

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
}
