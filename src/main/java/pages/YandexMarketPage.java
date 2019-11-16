package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class YandexMarketPage extends BasePage {
    private WebDriver webDriver;
    private By resultStats = By.id("text");
    private By searchButton = By.className("search2__button");
    private By pointButton = By.linkText("Маркет");
    private By answerNo = By.xpath("//div[2]/div[2]/span");
    private By region = By.xpath("//div[1]/span/input");
    private By spb = By.xpath("//*[contains(text(),\"Санкт-Петербург\")]");
    private By continueBtn = By.xpath("//*[contains(text(),\"Продолжить с новым регионом\")]");

    public YandexMarketPage(final WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriver.get("http://www.yandex.ru");
    }

    public YandexMarketPage setSearch(String market) {
        webDriver.findElement(resultStats).clear();
        webDriver.findElement(resultStats).sendKeys(market);
        startSearch();
        return this;
    }

    public void startSearch() {
        webDriver.findElement(searchButton).click();
    }

    public void redirectToMarket() {
        webDriver.findElement(pointButton).click();
        ArrayList<String> tabs2 = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs2.get(0));
        webDriver.close();
        webDriver.switchTo().window(tabs2.get(1));
    }

    public void switchCity(String city){
        webDriver.findElement(answerNo).click();
        WebElement region = webDriver.findElement(this.region);
        region.clear();
        region.sendKeys(city);
        region.click();
        region.click();
        webDriver.findElement(spb).click();
    }
}
