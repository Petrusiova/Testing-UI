package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void changeProducer(String name) {
        checkElementOnPage(allProducers);
        allProducers.click();
        String fieldXPath = "//*[@id=\"7893318-suggester\"]";
        checkElementOnPage(By.xpath(fieldXPath));
        WebElement searchField = getChromeDriver().findElementByXPath(fieldXPath);
        searchField.click();
        searchField.sendKeys(name);
        WebElement producer = getChromeDriver().findElementByXPath("//span[contains(text(), '" + name + "')]");
        Assert.assertTrue("Ќе найден подход€щий производитель", producer.isDisplayed());
        producer.click();
    }

    @Step("Change lowest price")
    public void changeLowestPrice(String value) {
        checkElementOnPage(lowestPrice);
        lowestPrice.click();
        lowestPrice.sendKeys(value);
    }

    @Step("Change highest price")
    public void changeHighestPrice(String value) {
        checkElementOnPage(highestPrice);
        highestPrice.click();
        highestPrice.sendKeys(value);
    }

    @Step("Change count of showed items")
    public void changeShowedCount() {
        checkElementOnPage(showCount);
//            checkIsInvisible(By.xpath("[@class=\"preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja\"]"));
        clickElement(showCount);
        getChromeDriver().findElementByXPath("//span[contains(text(), 'ѕоказывать по 12')]").click();
    }

    @Step("Change shops without included")
    public void changeShops(List<String> excludedVendors) {
        waitFor(10);
        By allShops = By.xpath("//*[@id=\"search-prepack\"]//div[30]/div/div/fieldset/footer/button");
        checkElementOnPage(allShops);
        waitFor(10);
        clickElement(getChromeDriver().findElement(allShops));
        scrollElementsAndClick(
                "//*[@id=\"search-prepack\"]//div[2]/ul/li[*]/div/label/div/span", excludedVendors, new ArrayList<>());
        getChromeDriver().findElement(allShops).click();
    }

    private void scrollElementsAndClick(String xPath, List<String> excludedVendors, ArrayList<String> old) {
        waitFor(8);
        // —обираем в коллекцию все отображаемые на странице магазины
        List<WebElement> shopList = getChromeDriver().findElementsByXPath(xPath);
        shopList.forEach(this::checkElementOnPage);
        for (int i = 0; i < excludedVendors.size(); i++) {
            String vendor = excludedVendors.get(i);
            String target = vendor.substring(0, 1);
            // ¬се нежелательные магазины из файла теперь с большой буквы (как на странице)
            excludedVendors.set(i, vendor.replace(target, target.toUpperCase()));
        }
        // —обираем все магазины, на которые не нужно кликать: нежелательные + на которые уже кликали
        excludedVendors.addAll(old);
        ArrayList<String> unSelected = new ArrayList<>();
        // —обираем все отображаемые магазины
        shopList.forEach(item -> unSelected.add(item.getText()));

        if (!old.containsAll(unSelected)) {
            for (WebElement shop : shopList) {
                if (!excludedVendors.contains(shop.getText()) && shop.isDisplayed()) {
                    //  ликаем на магазин
                    checkElementOnPage(shop);
                    clickElement(shop);
                    // ¬ыполн€ем скроллинг, при котором искомый магазин находитс€ на первой строчке
                    ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", shop);
                }
            }
            // ѕерезапускаем метод с новым набором прокликанных магазинов
            scrollElementsAndClick(xPath, excludedVendors, unSelected);
        }
    }

    @Step("Change rating")
    public void changeRating(String rating) {
        getChromeDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        getChromeDriver().manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        if (rating == "" || rating == null) {
            chooseRating("//*[@class=\"_1FbxpCIr0K _3A2H6kwJcC\"]");
        } else if (Integer.parseInt(rating) <= 3) {
            chooseRating("//*[@class=\"_1FbxpCIr0K XNE5y9RjQT\"]");
        } else if (Integer.parseInt(rating) > 3) {
            chooseRating("//*[@class=\"_1FbxpCIr0K _3RxxCpjiKz\"]");
        }
    }

    private void chooseRating(String xPath) {
        WebElement rating = getChromeDriver().findElementByXPath(xPath);
        checkElementOnPage(rating);
        rating.click();
    }

    public void sortBy(String value) {
        WebElement sort = getChromeDriver().findElementByXPath("//*[contains(text(), '" + value + "')]");
        checkElementOnPage(sort);
        sort.click();
        sort.click();
    }

    @Step("Choose third notebook on page")
    public void chooseThirdElement() {
        checkIsInvisible(By.xpath("//*[@class=\"preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja\"]"));
        By noteBook = By.xpath("//div[6]/div[2]/div[1]/div[2]/div/div[1]/div[3]/div[4]/div[1]/h3/a");
        checkElementOnPage(noteBook);
        getChromeDriver().findElement(noteBook).click();
        closePreviousWindow();
    }

    @Step("Checking producer is chosen")
    public void validateManufacturer(String manufacturer){
        By producer = By.xpath("//*[@id=\"n-breadcrumbs\"]/li[2]/a/span");
        checkElementOnPage(producer);
        Assert.assertEquals("ѕроизводитель не соответствует ожидаемому", manufacturer,
                getChromeDriver().findElement(producer).getText());

        By chars = By.xpath("//div[8]/div/div/div/ul/li[2]/a");
        checkElementOnPage(chars);
        getChromeDriver().findElement(chars).click();
    }
}
