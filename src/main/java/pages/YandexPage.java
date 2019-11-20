package pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Link;

import java.util.ArrayList;

public class YandexPage extends BasePage {

    @FindBy(id = "text")
    private WebElement resultStats;

    @FindBy(className = "search2__button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@class='iUh30']") ////что не так??
    private WebElement pointButton;


    @Step("Переходит на страницу www.yandex.ru")
    public void goToYandex(){
        getChromeDriver().get("http://www.yandex.ru");
        checkStartPage();
    }

    @Step("Поиск по запросу \"{0}\"")
    public void setSearch(String market) {
        resultStats.clear();
        resultStats.sendKeys(market);
        searchButton.click();
        redirectToMarket();//передаешь market из аргумкнтов setSearch
        //добавить ассерты на наличие элементов

        Link newFind = new Link(getChromeDriver().findElement(By.xpath("...."+market+"")));
        newFind.click();
    }

    //добавить @Step + проверки на наличие элементоа
    private void redirectToMarket() {
        pointButton.click();
        ArrayList<String> tabs2 = new ArrayList<String>(getChromeDriver().getWindowHandles());
        getChromeDriver().switchTo().window(tabs2.get(0));
        getChromeDriver().close();
        getChromeDriver().switchTo().window(tabs2.get(1));
    }


    @Step("Проверяем открылась ли страница Яндекса")
    private void checkStartPage() {
        Assert.assertEquals("Заголовок страницы не соответствует ожидаемому",
                "Яндекс", getChromeDriver().getTitle());
        Assert.assertTrue("Переход на стартовую страницу Яндекса не был выполнен",
                getChromeDriver().getCurrentUrl().startsWith("https://yandex.ru"));
        Assert.assertTrue("Не найдена строка для поиска",
                getChromeDriver().findElementById("text").isDisplayed());//строка для поиска - вынести как отдельный элемент
        Assert.assertTrue("Не найдена кнопка 'Найти'",
                getChromeDriver().findElementByClassName("search2__button").isDisplayed());//вынести как элемент кнопку найти
    }


}