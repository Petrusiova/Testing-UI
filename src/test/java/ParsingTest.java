import io.qameta.allure.Description;
import org.junit.Test;
import util.DOMExample;

import java.util.List;

public class ParsingTest {

    @Test
    @Description("Тест для проверки парсера")
    public void parse() {
        String filePath = "C:\\Users\\Olia\\Desktop\\parsing.xml";
        DOMExample domExample = new DOMExample(filePath);
        domExample.getListByNeighbourValue("Vendor", "microprice", "Vendor");
        domExample.getListByNeighbourAttrValue("Manufacturer", 0,"products", "pc", "Min");

        List<String> maxValues = domExample.getListElementsByTagName("Max");
        List<String> parents = domExample.getListElementsByTagName("Name");
        List<String> minValues = domExample.getListElementsByTagName("Min");

        domExample.changeTagValue(
                filePath, "Price", null, "Max", maxValues.get(0) + "0", 0);
        for (int i = 0; i < parents.size(); i++) {
            domExample.changeTagValue(
                    filePath, "Name", parents.get(i), "Max", maxValues.get(i + 1) + "0", 0);
            domExample.changeTagValue(
                    filePath, "Name", parents.get(i), "Min", minValues.get(i) + "0", 0);
        }
    }
}
