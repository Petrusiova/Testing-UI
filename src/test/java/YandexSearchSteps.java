import io.qameta.allure.Step;
import org.junit.Assert;
import pages.BasePage;
import pages.CompTechPage;
import pages.MarketPage;
import pages.YandexPage;
import util.DOMExample;

import java.util.List;

public class YandexSearchSteps extends BasePage {


    @Step("ќткрываем страницу яндекса")
    public void openPageRedirectAndCheck(String goTo, String search) {
        YandexPage page = new YandexPage();
        goTo(goTo);
        page.setSearch(search);
        page.redirectToMarket();
    }

    @Step("Change city by first three letters {0} and category on {2}")
    public void changeCityAndCategory(String firstLetters, String fullName, String category) {
        MarketPage page = new MarketPage();
        page.anotherCity();
        page.changeCity(firstLetters, fullName);
        page.changeSection(category);
    }

    @Step("Change section on {0}")
    public void changeSection(String section, String name) {
        CompTechPage compTechPage = new CompTechPage();
        compTechPage.changeCategory(section);
        compTechPage.changeProducer(name);
    }

    @Step("SearchProducerInXML")
    public List<String> searchElements(String filePath, String tagName){
        DOMExample domExample = new DOMExample(filePath);
        List<String> elements = domExample.getListElementsByTagName(tagName);
        Assert.assertFalse("—писок значений тегов " + tagName + " не пустой", elements.isEmpty());
        return elements;
    }

}
