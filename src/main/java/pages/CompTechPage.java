package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CompTechPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"search-prepack\"]//div[4]/div/div/fieldset/footer/button")
    private WebElement allProducers;

    @FindBy(xpath = "//*[@id=\"glpricefrom\"]")
    private WebElement lowestPrice;

    @FindBy(xpath = "//*[@id=\"glpriceto\"]")
    private WebElement highestPrice;

    @FindBy(xpath = "//div[2]/div/div[3]/span")
    private WebElement showCount;

    @FindBy(xpath = "//*[@id=\"search-prepack\"]//div[30]/div/div/fieldset/footer/button")
    private WebElement showAllShops;

    @Step("Change product category on {0}")
    public void changeCategory(String section) {
        WebElement ourSection = getChromeDriver().findElementByXPath(".//a[text() = '" + section + "']");
        checkElementOnPage(ourSection);
        ourSection.click();
    }

    @Step("Select producer filter: {0}")
    public void changeProducer(String name){
        checkElementOnPage(allProducers);
        allProducers.click();
        String fieldXPath = "//*[@id=\"7893318-suggester\"]";
        checkElementOnPage(By.xpath(fieldXPath));
        ChromeDriver driver = getChromeDriver();
        WebElement searchField = driver.findElementByXPath(fieldXPath);
        searchField.click();
        searchField.sendKeys(name);
        WebElement producer = driver.findElementByXPath("//span[contains(text(), '" + name + "')]");
        Assert.assertTrue("Не найден подходящий производитель", producer.isDisplayed());
        producer.click();
    }

    @Step("Change lowest price")
    public  void changeLowestPrice(String value){
        Assert.assertTrue("Не найдено поле ввода минимальной цены", lowestPrice.isDisplayed());
        lowestPrice.click();
        lowestPrice.sendKeys(value);
    }

    @Step("Change highest price")
    public  void changeHighestPrice(String value){
        Assert.assertTrue("Не найдено поле ввода максимальной цены", highestPrice.isDisplayed());
        highestPrice.click();
        highestPrice.sendKeys(value);
    }

    @Step("Change count of showed items")
    public void changeShowedCount(){
        Assert.assertTrue("Не найдено поле выбора количество показанных товаров", showCount.isDisplayed());
        showCount.click();
        getChromeDriver().findElementByXPath("//span[contains(text(), 'Показывать по 12')]").click();
    }

    @Step("Change shops without included")
    public void changeShops(List<String> excludedVendors){
        Assert.assertTrue("Не найдено поле выбора количество показанных товаров", showAllShops.isDisplayed());
        showAllShops.click();
        scrollElementsAndClick(
                "//*[@id=\"search-prepack\"]//div[2]/ul/li[*]/div/label/div/span", excludedVendors, new ArrayList<>());
    }

    private void scrollElementsAndClick(String xPath, List<String> excludedVendors, ArrayList<String> old) {
        List<WebElement> shopList = getChromeDriver().findElementsByXPath(xPath);
        for (int i = 0; i < excludedVendors.size(); i++){
            String vendor = excludedVendors.get(i);
            String target = vendor.substring(0, 1);
            excludedVendors.set(i, vendor.replace(target, target.toUpperCase()));
        }
        excludedVendors.addAll(old);
        ArrayList<String> unSelected = new ArrayList<>();

        shopList.forEach(item -> unSelected.add(item.getText()));

        if (!old.containsAll(unSelected)) {
            for (WebElement shop : shopList) {
                if (!excludedVendors.contains(shop.getText()) && shop.isDisplayed()) {
                    shop.click();
                    ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", shop);
                }
            }
            scrollElementsAndClick(xPath, excludedVendors, unSelected);
        }
    }
}
