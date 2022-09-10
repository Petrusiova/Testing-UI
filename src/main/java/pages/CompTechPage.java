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

    @FindBy(xpath = "//legend[contains(text(), '�������������')]/..//button")
    private WebElement allProducers;

    @FindBy(xpath = "//*[@id=\"glpricefrom\"]")
    private WebElement lowestPrice;

    @FindBy(xpath = "//*[@id=\"glpriceto\"]")
    private WebElement highestPrice;

    @FindBy(xpath = "//button[contains(text(), '���������� ��')]/../../..")
    private WebElement showCount;


    @Step("�������� ������������ {0}")
    public void changeCategory(String section) {
        WebElement ourSection = getChromeDriver().findElementByXPath(".//a[text() = '" + section + "']");
        checkElementOnPage(ourSection);
        ourSection.click();
    }

    @Step("�������� �������������: {0}")
    public void changeProducer(String name) {
        checkElementOnPage(allProducers);
        allProducers.click();
        String fieldXPath = "//legend[contains(text(), \"�������������\")]/..//input[@type='text']";
        try {
            boolean isTextInputDisplayed = !getChromeDriver().findElementByXPath(fieldXPath).isDisplayed();
        } catch (Exception e){
            allProducers.click();
        }
        checkElementOnPage(By.xpath(fieldXPath));
        WebElement searchField = getChromeDriver().findElementByXPath(fieldXPath);
        searchField.click();
        searchField.clear();
        searchField.sendKeys(name);
        WebElement producer = getChromeDriver().findElementByXPath("//span[contains(text(), '" + name + "')]");
        Assert.assertTrue("�� ������ ���������� �������������", producer.isDisplayed());
        producer.click();
    }

    @Step("������� ������ ������ �������������")
    public void cancelProducer(String name){
        WebElement producer = getChromeDriver().findElementByXPath("//span[text()='" + name + "']");
        checkElementOnPage(producer);
        producer.click();
    }

    @Step("�������� ����������� ����")
    public void changeLowestPrice(String value) {
        checkElementOnPage(lowestPrice);
        lowestPrice.click();
        lowestPrice.clear();
        lowestPrice.sendKeys(value);
    }

    @Step("�������� ������������ ����")
    public void changeHighestPrice(String value) {
        checkElementOnPage(highestPrice);
        highestPrice.click();
        highestPrice.clear();
        highestPrice.sendKeys(value);
    }

    @Step("�������� ���������� ������������ ��������� �� ��������")
    public void changeShowedCount() {
        checkElementOnPage(showCount);
//            checkIsInvisible(By.xpath("[@class=\"preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja\"]"));
        clickElement(showCount);
        String twelve = "//button[contains(text(), '���������� �� 12')]";
        checkElementOnPage(By.xpath(twelve));
        clickElement(getChromeDriver().findElementByXPath(twelve));
    }

    @Step("�������� ��������, �������� �������������")
    public void changeShops(List<String> excludedVendors) {
        waitFor(10);
        By allShops = By.xpath("//legend[contains(text(), '��������')]/..//button");
        checkElementOnPage(allShops);
        waitFor(10);
        clickElement(getChromeDriver().findElement(allShops));
        scrollElementsAndClick(
                "//*[@id=\"search-prepack\"]//div[2]/ul/li[*]//span", excludedVendors, new ArrayList<>());
        getChromeDriver().findElement(allShops).click();
    }

    private void scrollElementsAndClick(String xPath, List<String> excludedVendors, ArrayList<String> old) {
        waitFor(8);
        // �������� � ��������� ��� ������������ �� �������� ��������
        List<WebElement> shopList = getChromeDriver().findElementsByXPath(xPath);
        shopList.forEach(this::checkElementOnPage);
        for (int i = 0; i < excludedVendors.size(); i++) {
            String vendor = excludedVendors.get(i);
            String target = vendor.substring(0, 1);
            // ��� ������������� �������� �� ����� ������ � ������� ����� (��� �� ��������)
            excludedVendors.set(i, vendor.replace(target, target.toUpperCase()));
        }
        // �������� ��� ��������, �� ������� �� ����� �������: ������������� + �� ������� ��� �������
        excludedVendors.addAll(old);
        ArrayList<String> unSelected = new ArrayList<>();
        // �������� ��� ������������ ��������
        shopList.forEach(item -> unSelected.add(item.getText()));

        if (!old.containsAll(unSelected)) {
            for (WebElement shop : shopList) {
                if (!excludedVendors.contains(shop.getText()) && shop.isDisplayed()) {
                    // ������� �� �������
                    checkElementOnPage(shop);
                    clickElement(shop);
                    // ��������� ���������, ��� ������� ������� ������� ��������� �� ������ �������
                    ((JavascriptExecutor) getChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", shop);
                }
            }
            // ������������� ����� � ����� ������� ������������ ���������
            scrollElementsAndClick(xPath, excludedVendors, unSelected);
        }
    }

    @Step("�������� ������� ���������")
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

    @Step("�������� ����������")
    public void sortBy(String value) {
        WebElement sort = getChromeDriver().findElementByXPath("//*[contains(text(), '" + value + "')]");
        checkElementOnPage(sort);
        sort.click();
        sort.click();
    }

    @Step("�������� ������ ������� �� ��������")
    public void chooseThirdElement() {
        waitFor(10);
        checkIsInvisible(By.xpath("//*[@class=\"preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja\"]"));
        // ������ ������
        By noteBook = By.xpath("//div[3]//h3/a");
        checkElementOnPage(noteBook);
        clickElement(getChromeDriver().findElement(noteBook));
//        closePreviousWindow();
        ArrayList<String> tabs = new ArrayList<>(getChromeDriver().getWindowHandles());
        getChromeDriver().switchTo().window(tabs.get(1));
    }

    @Step("��������� ������������� �� ������������ �����������")
    public void validateManufacturer(String manufacturer){
        ArrayList<String> tabs = new ArrayList<>(getChromeDriver().getWindowHandles());
        getChromeDriver().switchTo().window(tabs.get(1));

        Assert.assertTrue("������������� �� ������������� ����������",
                getChromeDriver().findElement(By.xpath("//h1")).getText().contains("������� " + manufacturer));
        // "��������������"
        getChromeDriver().findElement(By.xpath("//span[contains(text(), '��������������')]")).click();
    }

    @Step("�������� �������������� ��������")
    public Map<String, String> getChars(String...characts){
        Map<String, String> map = new HashMap<>();
        Arrays.asList(characts).forEach(item -> map.put(item,
                getChromeDriver().findElementByXPath("//span[text()='" + item + "']/../../../dd").getText().split(",")[0]));
        return map;
    }
}
