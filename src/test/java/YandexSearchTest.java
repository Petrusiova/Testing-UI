import io.qameta.allure.Description;
import org.junit.Test;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        openPageRedirectAndCheck("http://yandex.ru", "Яндекс маркет");
        changeCityBy("сан");
    }

}
