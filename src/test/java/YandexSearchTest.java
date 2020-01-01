import io.qameta.allure.Description;
import org.junit.Test;

import java.util.List;

public class YandexSearchTest extends YandexSearchSteps {

    @Test
    @Description("Тест для проверки перехода на Санкт-Петербургский Яндекс Маркет")
    public void open() {
        String filePath = "C:\\Users\\Olia\\Test3\\parsing.xml";
        String rating = getNodeAttributes(filePath, "Excluded_vendors", 0, "rating");
        String maxPrice = searchElements(filePath, "Price").get(0).trim();
        List<String> manufacturers = searchElements(filePath, "Name");
        List<String> minValues = searchValue(filePath, "Name", manufacturers.get(0), "Min");
        List<String> maxValues = searchValue(filePath, "Name", manufacturers.get(0), "Max");
        List<String> excludedVendors = searchElements(filePath, "Vendor");
//        for (int i = 0; i < maxValues.size(); i++) {
//            if (Long.valueOf(maxValues.get(i)) > Long.valueOf(maxPrice)) {
//                maxValues.set(i, maxPrice);
//            }
//        }


        openPageRedirectAndCheck("http://yandex.ru", "Яндекс маркет");
        changeCityAndCategory("сан", "Санкт-Петербург", "Компьютерная техника");
        changeSection("Ноутбуки", manufacturers.get(0), minValues.get(0), maxValues.get(0));
        changeShops(excludedVendors, rating, "по цене", manufacturers.get(0));
        String s = "";
    }
}
