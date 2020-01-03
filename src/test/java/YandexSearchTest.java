import io.qameta.allure.Description;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        String filePath = "C:\\Users\\Olia\\Test3\\parsing.xml";
        String rating = getNodeAttributes(filePath, "Excluded_vendors", 0, "rating");
        String maxPrice = searchElements(filePath, "Price").get(0).trim();
        List<String> excludedVendors = searchElements(filePath, "Vendor");
        List<String> manufacturers = searchElements(filePath, "Name");
        Map<String, String> ourMap = new HashMap<>();
        ourMap.put("Экран", "0");
        ourMap.put("Вес", "0");

        openPageRedirectAndCheck("http://yandex.ru", "Яндекс маркет");
        changeCityAndCategory("сан", "Санкт-Петербург", "Компьютерная техника");
        changeSection("Ноутбуки");
        changeShops(excludedVendors, rating, "по цене");

        for (String producer : manufacturers) {
            Map<String, String> newMap = check(filePath, producer, rating, maxPrice);
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

    }

    public Map<String, String> check(String filePath, String manufacturer, String rating, String maxPrice) {
        String min = searchPriceValue(filePath, "Name", manufacturer, "Min");
        String max = searchPriceValue(filePath, "Name", manufacturer, "Max");
        if (Long.valueOf(max) > Long.valueOf(maxPrice)) {
            max = maxPrice;
        }
        changeManufacturer(manufacturer, min, max, manufacturer);
        makeScreenShot();
        Map<String, String> characteristics = getCharacteristics("Экран", "Вес");
        unselectManufacturer(manufacturer);
        return characteristics;
    }
}
