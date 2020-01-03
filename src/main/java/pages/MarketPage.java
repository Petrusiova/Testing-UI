package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class MarketPage extends BasePage {

    @FindBy(xpath = "//div[2]/div[2]/span")
    private WebElement answerNo;

    @FindBy(xpath = "//div[1]/span/input")
    private WebElement region;

    @FindBy(xpath = "//*[contains(text(),'���������� � ����� ��������')]")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[contains(text(), '��� ���������')]")
    private WebElement allCategories;

    public void anotherCity() {
        Assert.assertTrue("�� ������� ������ ������ ������� ������", answerNo.isDisplayed());
        clickElement(answerNo);
    }

    @Step("����� ������ �� {0}")
    public void changeCity(String city, String fullName) {
        Assert.assertTrue("�� ������� ���� ��� ����� �������", region.isDisplayed());
        region.clear();
        region.sendKeys(city);
        region.click();
        String cityXPath = "//*[contains(text(),'" + fullName + "')]";
        checkElementOnPage(By.xpath(cityXPath));
        WebElement spb = getChromeDriver().findElementByXPath(cityXPath);
        Assert.assertTrue("�� ������� ����������� ���� ������ �������", spb.isDisplayed());
        spb.click();
        region.sendKeys(Keys.ENTER);
    }

    @Step("Change category on {0}")
    public void changeSection(String category) {
        checkElementOnPage(allCategories);
        clickElement(allCategories);
        WebElement categoryElement = getChromeDriver().findElementByXPath("//*[contains(text(), '" + category + "')]");
        checkElementOnPage(categoryElement);
        clickElement(categoryElement);
    }
}
