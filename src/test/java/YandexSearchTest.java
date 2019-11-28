import io.qameta.allure.Description;
import org.junit.Test;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        openYandexAndCheck();
        changeCityBy("сан");
    }

}
