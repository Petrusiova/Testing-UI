package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

public class YandexPage extends BasePage{

    @FindBy(id = "text")
    private WebElement resultStats;

    @FindBy(className = "search2__button")
    private WebElement searchButton;

    @FindBy(linkText = "Маркет")
    private WebElement pointButton;


    @Step("Устанавливаем значение {0} для поиска")
    public YandexPage setSearch(String market) {
        Assert.assertTrue("Не найдена строка для вводка запроса для поиска", resultStats.isDisplayed());
        resultStats.clear();
        resultStats.sendKeys(market);
        searchButton.click();
        return this;
    }

    public void redirectToMarket() {
        ChromeDriver driver = getChromeDriver();
        Assert.assertTrue("Среди поисковой выдачи нет страницы Яндекс Маркета", pointButton.isDisplayed());
        pointButton.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertEquals("Страница Яндекс Маркета не открылась в новом окне", 2, tabs.size());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
        Assert.assertEquals("Закрытие предыдущей страницы не произошло",
                1, driver.getWindowHandles().size());
    }
}
