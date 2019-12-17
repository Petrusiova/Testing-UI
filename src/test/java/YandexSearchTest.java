import io.qameta.allure.Description;
import org.junit.Test;
import util.DOMExample;

import java.util.List;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        openPageRedirectAndCheck("http://yandex.ru", "Яндекс маркет");
        changeCityBy("сан", "Санкт-Петербург");
    }

    @Test
    @Description("Тест для проверки парсера")
    public void parse() {
        DOMExample domExample = new DOMExample("C:\\Users\\Olia\\Desktop\\parsing.xml");
        List<String> maxValues = domExample.getStringElementsByTagName("Max");
        List<String> parents = domExample.getStringElementsByTagName("Name");
        List<String> minValues = domExample.getStringElementsByTagName("Min");

        domExample.changeTagValue("C:\\Users\\Olia\\Desktop\\parsed.xml",
                "Price", null, "Max", maxValues.get(0) + "0");
        for (int i = 0; i < parents.size(); i++) {
            domExample.changeTagValue("C:\\Users\\Olia\\Desktop\\parsed.xml",
                    "Name", parents.get(i), "Max", maxValues.get(i + 1) + "0");
        }
        for (int i = 0; i < parents.size(); i++) {
            domExample.changeTagValue("C:\\Users\\Olia\\Desktop\\parsed.xml",
                    "Name", parents.get(i), "Min", minValues.get(i) + "0");
        }
    }
}
