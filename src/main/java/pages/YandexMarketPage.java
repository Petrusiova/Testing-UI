package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YandexMarketPage extends BasePage {
    private WebDriver webDriver;
    private By resultStats = By.id("text");
    private By searchButton = By.className("search2__button");
    private By pointButton = By.className("organic__url-text");

    public YandexMarketPage(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public YandexMarketPage setSearch(String market){
        webDriver.findElement(resultStats).clear();
        webDriver.findElement(resultStats).sendKeys(market);
        startSearch();
        return this;
    }

    public void startSearch() {
        webDriver.findElement(searchButton).click();
    }

    public void redirectToMarket(){
        webDriver.findElement(pointButton).click();
    }
}
