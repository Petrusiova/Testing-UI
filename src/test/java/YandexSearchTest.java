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
        try {
            DOMExample domExample = new DOMExample("C:\\Users\\Olia\\Desktop\\parsing.xml");
            List<String> result = domExample.getStringElementsByTagName("Max");
            domExample.changeTagValue("C:\\Users\\Olia\\Desktop\\parsed.xml",
                    "Price", "Max", result.get(0) + "0");


//            List result = domExample.getStringElementsByTagName("Vendor");
            System.out.println(result);

        } catch (Exception e){}
    }

}
