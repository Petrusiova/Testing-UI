import io.qameta.allure.Step;
import pages.BasePage;
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

    @Step("—мена города по первым трем буквам {0}")
    public void changeCityBy(String firstLetters, String fullName) {
        MarketPage page = new MarketPage();
        page.anotherCity();
        page.changeCity(firstLetters, fullName);
    }


}
