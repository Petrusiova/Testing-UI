package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CompTechPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"search-prepack\"]//div[4]/div/div/fieldset/footer/button")
    private WebElement allProducers;

    @FindBy(xpath = "//*[@id=\"glpricefrom\"]")
    private WebElement lowestPrice;

    @FindBy(xpath = "//*[@id=\"glpriceto\"]")
    private WebElement highestPrice;

    @FindBy(xpath = "//div[2]/div/div[3]/span")
    private WebElement showCount;

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
}
