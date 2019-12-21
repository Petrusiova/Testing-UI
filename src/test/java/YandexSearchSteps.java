import io.qameta.allure.Step;
import pages.BasePage;
import pages.CompTechPage;
import pages.MarketPage;
import pages.YandexPage;

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
        page.changeCategory(category);
    }

    @Step("Change section on {0}")
    public void changeSection(String section) {
        CompTechPage compTechPage = new CompTechPage();
        compTechPage.chooseSection(section);
    }


}
