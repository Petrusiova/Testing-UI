import io.qameta.allure.Description;
import org.junit.Test;

import java.util.List;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        openPageRedirectAndCheck("http://yandex.ru", "Яндекс маркет");
        changeCityAndCategory("сан", "Санкт-Петербург", "Компьютерная техника");
        List<String> searchProducer = searchElements("C:\\Users\\Olia\\Desktop\\parsing.xml", "Name");
        changeSection("Ноутбуки", searchProducer.get(0));
        String s = "";
    }
}
