import io.qameta.allure.Description;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() throws IOException {
        String filePath = "parsing.xml";
        Map<String, String> ourMap = new HashMap<>();
        ourMap.put("Экран", "0");
        ourMap.put("Вес", "0");

        String rating = getNodeAttributes(filePath, "Excluded_vendors", 0, "rating");
        String maxPrice = searchElements(filePath, "Price").get(0).trim();
        List<String> excludedVendors = searchElements(filePath, "Vendor");
        List<String> manufacturers = searchElements(filePath, "Name");

        openPageRedirectAndCheck("http://yandex.ru", "Яндекс маркет");
        changeCityAndCategory("сан", "Санкт-Петербург", "Компьютерная техника");
        changeSection("Ноутбуки");
        changeShops(excludedVendors, rating, "по цене");

        for (String producer : manufacturers) {
            Map<String, String> newMap = check(filePath, producer, maxPrice);
            double ourScreen = Double.parseDouble(ourMap.get("Экран").replaceAll("\\s.+", ""));
            double newScreen = Double.parseDouble(newMap.get("Экран").replaceAll("\\s.+", ""));

            if (ourScreen < newScreen) {
                ourMap = newMap;
            } else if (ourScreen == newScreen) {
                if (Double.parseDouble(ourMap.get("Вес").replaceAll("\\s.+", "")) >
                        Double.parseDouble(newMap.get("Вес").replaceAll("\\s.+", ""))) {
                    ourMap = newMap;
                }
            }
        }
        attachScreenShot(ourMap.get("Имя файла"));
    }
}
