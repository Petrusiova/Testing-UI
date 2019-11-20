import io.qameta.allure.Description;
import org.junit.Test;
import pages.YandexPage;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
//    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        YandexPage page = new YandexPage();
        page.goToYandex();
        new YandexPage().setSearch("Яндекс маркет");
        validateLoadPage();
        changeRegion("сан");
    }


}
