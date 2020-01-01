package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CompTechPage extends BasePage {

    @FindBy(xpath = "//*[@data-autotest-id=\"7893318\"]//button")
    private WebElement allProducers;

    @FindBy(xpath = "//*[@id=\"glpricefrom\"]")
    private WebElement lowestPrice;

    @FindBy(xpath = "//*[@id=\"glpriceto\"]")
    private WebElement highestPrice;

    @FindBy(xpath = "//*[@role=\"listbox\"]")
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
        Assert.assertTrue("Не найден подходящий производитель", producer.isDisplayed());
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
        String twelve = "//span[contains(text(), 'Показывать по 12')]";
        checkElementOnPage(By.xpath(twelve));
        clickElement(getChromeDriver().findElementByXPath(twelve));
    }

    @Step("Change shops without included")
    public void changeShops(List<String> excludedVendors) {
        waitFor(10);
        By allShops = By.xpath("//*[@id=\"search-prepack\"]//div[30]//button");
        checkElementOnPage(allShops);
        waitFor(10);
        clickElement(getChromeDriver().findElement(allShops));
        scrollElementsAndClick(
                "//*[@id=\"search-prepack\"]//div[2]/ul/li[*]//span", excludedVendors, new ArrayList<>());
        getChromeDriver().findElement(allShops).click();
    }

    private void scrollElementsAndClick(String xPath, List<String> excludedVendors, ArrayList<String> old) {
        waitFor(8);
        // Собираем в коллекцию все отображаемые на странице магазины
        List<WebElement> shopList = getChromeDriver().findElementsByXPath(xPath);
        shopList.forEach(this::checkElementOnPage);
        for (int i = 0; i < excludedVendors.size(); i++) {
            String vendor = excludedVendors.get(i);
            String target = vendor.substring(0, 1);
            // Все нежелательные магазины из файла теперь с большой буквы (как на странице)
            excludedVendors.set(i, vendor.replace(target, target.toUpperCase()));
        }
        // Собираем все магазины, на которые не нужно кликать: нежелательные + на которые уже кликали
        excludedVendors.addAll(old);
        ArrayList<String> unSelected = new ArrayList<>();
        // Собираем все отображаемые магазины
        shopList.forEach(item -> unSelected.add(item.getText()));

        if (!old.containsAll(unSelected)) {
            for (WebElement shop : shopList) {
                if (!excludedVendors.contains(shop.getText()) && shop.isDisplayed()) {
                    // Кликаем на магазин
                    checkElementOnPage(shop);
                    clickElement(shop);
                    // Выполняем скроллинг, при котором искомый магазин находится на первой строчке
                    ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", shop);
                }
            }
            // Перезапускаем метод с новым набором прокликанных магазинов
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
        clickElement(rating);
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
        // Третий элмент
        By noteBook = By.xpath("//div[3]//h3/a");
        checkElementOnPage(noteBook);
        clickElement(getChromeDriver().findElement(noteBook));
        closePreviousWindow();
    }

    @Step("Checking producer is chosen")
    public void validateManufacturer(String manufacturer){
        By producer = By.xpath("//*[@id=\"n-breadcrumbs\"]/li[2]/a/span");
        checkElementOnPage(producer);
        Assert.assertEquals("Производитель не соответствует ожидаемому", manufacturer,
                getChromeDriver().findElement(producer).getText());
        // "Характеристики"
        By chars = By.xpath("//*[@data-name=\"spec\"]");
        checkElementOnPage(chars);
        getChromeDriver().findElement(chars).click();
    }

    @Step("ЗАМЕНИТЬ НА НОРМАЛЬНОЕ НАЗВАНИЕ ШАГА")
    public Map<String, String> getChars(String...characts){
        Map<String, String> map = new HashMap<>();
        Arrays.asList(characts).forEach(item -> map.put(item,
                getChromeDriver().findElementByXPath("//*[contains(text(), '" + item + "')]/../../dd/span").getText().split(",")[0]));
        return map;
    }
}
