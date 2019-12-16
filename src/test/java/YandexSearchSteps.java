import io.qameta.allure.Step;
import pages.BasePage;
import pages.MarketPage;
import pages.YandexPage;

public class YandexSearchSteps extends BasePage {


    @Step("ќткрываем страницу яндекса")
    public void openPageRedirectAndCheck(String goTo, String search) {
        YandexPage page = new YandexPage();
        goTo(goTo);
        page.checkStartPage();
        page.setSearch(search);
        page.redirectToMarket();
    }

    @Step("—мена города по первым трем буквам {0}")
    public void changeCityBy(String firstLetters) {
        MarketPage page = new MarketPage();
        page.checkSearchMarketPage();
        page.anotherCity();
        page.changeCity(firstLetters);
        try {
            Thread.sleep(5000);
            page.choseSpb();
        } catch (InterruptedException e) {//избавьс€ от этого
            e.printStackTrace();
        }
    }


}
