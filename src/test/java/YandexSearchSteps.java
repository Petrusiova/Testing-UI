import io.qameta.allure.Step;
import org.junit.Assert;
import pages.BasePage;
import pages.CompTechPage;
import pages.MarketPage;
import pages.YandexPage;
import util.XmlDOMParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class YandexSearchSteps extends BasePage {


    @Step("Открываем страницу Яндекса")
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
        getChromeDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        page.changeSection(category);
    }

    @Step("Change section on {0}")
    public void changeSection(String section) {
        CompTechPage compTechPage = new CompTechPage();
        compTechPage.changeCategory(section);
        compTechPage.changeShowedCount();
    }

    @Step("Change manufacturer")
    public  void changeManufacturer(String name, String min, String max, String manufacturer){
        CompTechPage compTechPage = new CompTechPage();
        compTechPage.changeProducer(name);
        compTechPage.changeLowestPrice(min);
        compTechPage.changeHighestPrice(max);
        compTechPage.chooseThirdElement();
        ArrayList<String> tabs = new ArrayList<>(getChromeDriver().getWindowHandles());
        getChromeDriver().switchTo().window(tabs.get(1));
        compTechPage.validateManufacturer(manufacturer);
    }

    @Step("Change shops")
    public void changeShops(List<String> excludedVendors, String rating, String value){
        CompTechPage compTechPage = new CompTechPage();
        compTechPage.changeShops(excludedVendors);
        compTechPage.changeRating(rating);
        compTechPage.sortBy(value);
    }

    @Step ("Search max price in XML")
    public String searchPriceValue(String filePath, String tagName, String tagValue, String childTagName){
        String element = new XmlDOMParser(filePath).getStringByNeighbourValue(tagName, tagValue, childTagName);
        Assert.assertFalse("Список значений тегов " + tagName + " пустой", element.isEmpty());
        return element;
    }

    @Step("SearchProducerInXML")
    public List<String> searchElements(String filePath, String tagName){
        List<String> elements = new XmlDOMParser(filePath).getListElementsByTagName(tagName);
        Assert.assertFalse("Список значений тегов " + tagName + " пустой", elements.isEmpty());
        return elements;
    }

    public String getNodeAttributes(String filePath, String tagName, int nodeIndex, String attrName){
        Assert.assertNotNull("Невозможно вычислить значение атрибута с именем null", attrName);
        String attribute = new XmlDOMParser(filePath).getNodeAttributes(tagName, nodeIndex).getNamedItem(attrName).getNodeValue();
        Assert.assertNotNull("Не найден атрибут с именем " + attrName, attribute);
        return attribute;
    }

    @Step("Get notebook characteristics")
    public Map<String, String> getCharacteristics(String...chars){
        return new CompTechPage().getChars(chars);
    }

    @Step("Unselect producer")
    public void unselectManufacturer(String name){
        ArrayList<String> tabs = new ArrayList<>(getChromeDriver().getWindowHandles());
        getChromeDriver().switchTo().window(tabs.get(1));
        getChromeDriver().close();
        getChromeDriver().switchTo().window(tabs.get(0));
        CompTechPage compTechPage = new CompTechPage();
        compTechPage.cancelProducer(name);
    }

}
