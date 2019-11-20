import io.qameta.allure.Step;
import pages.BasePage;
import pages.YandexMarketPage;

public class YandexSearchSteps extends BasePage {

    @Step("Переключение на регион по первым трем буквам \"{0}\"")
    public void changeRegion(String firstRegionLetters) {
        YandexMarketPage page = new YandexMarketPage();
        page.anotherCity();
        page.checkAnotherCity();
        page.changeCity(firstRegionLetters);
        page.choseSpb();
        page.checkSpbPage();
    }

    @Step("Проверяем страницу Яндекс маркет")
    public void validateLoadPage() {
        YandexMarketPage page = new YandexMarketPage();
        page.checkSearchMarketPage();
        page.checkMarketPage();
    }


}
