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

//    @FindBy(xpath = "//*[@id=\"search-prepack\"]//div[30]/div/div/fieldset/footer/button")
//    private WebElement showAllShops;

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
        Assert.assertTrue("Ќе найден подход€щий производитель", producer.isDisplayed());
        producer.click();
    }

    @Step("Change lowest price")
    public  void changeLowestPrice(String value){
        Assert.assertTrue("Ќе найдено поле ввода минимальной цены", lowestPrice.isDisplayed());
        lowestPrice.click();
        lowestPrice.sendKeys(value);
    }

    @Step("Change highest price")
    public  void changeHighestPrice(String value){
        Assert.assertTrue("Ќе найдено поле ввода максимальной цены", highestPrice.isDisplayed());
        highestPrice.click();
        highestPrice.sendKeys(value);
    }

    @Step("Change count of showed items")
    public void changeShowedCount(){
        Assert.assertTrue("Ќе найдено поле выбора количество показанных товаров", showCount.isDisplayed());
        checkElementOnPage(showCount);
        showCount.click();
        getChromeDriver().findElementByXPath("//span[contains(text(), 'ѕоказывать по 12')]").click();
    }

    @Step("Change shops without included")
    public void changeShops(List<String> excludedVendors){
        String allShops = "//*[@id=\"search-prepack\"]//div[30]/div/div/fieldset/footer/button";
//        checkElementOnPage(By.xpath(allShops));
        try {
            Thread.sleep(9*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getChromeDriver().findElementByXPath(allShops).click();
        scrollElementsAndClick(
                "//*[@id=\"search-prepack\"]//div[2]/ul/li[*]/div/label/div/span", excludedVendors, new ArrayList<>());
    }

    private void scrollElementsAndClick(String xPath, List<String> excludedVendors, ArrayList<String> old) {
        getChromeDriver().manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        // —обираем в коллекцию все отображаемые на странице магазины
        List<WebElement> shopList = getChromeDriver().findElementsByXPath(xPath);
        shopList.forEach(this::checkElementOnPage);
        for (int i = 0; i < excludedVendors.size(); i++){
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
//                    checkElementOnPage(shop);
                    shop.click();
                    // ¬ыполн€ем скроллинг, при котором искомый магазин находитс€ на первой строчке
                    ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", shop);
                }
            }
            // ѕерезапускаем метод с новым набором прокликанных магазинов
            scrollElementsAndClick(xPath, excludedVendors, unSelected);
        }
    }

    @Step("Choose third notebook on page")
    public void checkItem(String manufacturer){
        String thirdPositionPath = "//div[6]/div[2]/div[1]/div[2]/div/div[1]/div[3]/div[4]/div[1]/h3/a";
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", findElement(By.xpath(thirdPositionPath), 20));
        getChromeDriver().findElementByXPath(thirdPositionPath).click();
        closePreviousWindow();
        String characteristics = "//*[contains(text(), '’арактеристики')]";
        ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", findElement(By.xpath(characteristics), 20));
        try {
            Thread.sleep(9*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getChromeDriver().findElementByXPath(characteristics).click();
        Assert.assertEquals("ѕроизводитель не соответствует ожидаемому", manufacturer,
                getChromeDriver().findElementsByXPath("//*[@class='n-breadcrumbs__item']").get(1).getText());
    }

    @Step("Change rating")
    public void changeRating(String rating) {
        if (rating == "" || rating == null) {
            chooseRating("//*[@class=\"_1FbxpCIr0K _3A2H6kwJcC\"]");
        } else if (Integer.parseInt(rating) <= 3) {
            chooseRating("//*[@class=\"_1FbxpCIr0K XNE5y9RjQT\"]");
        } else if (Integer.parseInt(rating) > 3) {
            chooseRating("//*[@class=\"_1FbxpCIr0K _3RxxCpjiKz\"]");
        }
    }

    public void sortBy(String value){
        WebElement sort = getChromeDriver().findElementByXPath("//*[contains(text(), '" + value + "')]");
        checkElementOnPage(sort);
        sort.click();
        sort.click();
    }

    private void chooseRating(String xPath){
        WebElement rating = getChromeDriver().findElementByXPath(xPath);
        checkElementOnPage(rating);
        rating.click();
    }
}
