import io.qameta.allure.Description;
import org.junit.Test;
import util.XmlDOMParser;

import java.util.List;

public class ParsingTest {

    @Test
    @Description("Тест для проверки парсера")
    public void parse() {
        String filePath = "C:\\Users\\Olia\\Desktop\\parsing.xml";
        XmlDOMParser xmlDomParser = new XmlDOMParser(filePath);
        xmlDomParser.getListByNeighbourValue("Vendor", "microprice", "Vendor");
        xmlDomParser.getListByNeighbourAttrValue("Manufacturer", 0,"products", "pc", "Min");

        List<String> maxValues = xmlDomParser.getListElementsByTagName("Max");
        List<String> parents = xmlDomParser.getListElementsByTagName("Name");
        List<String> minValues = xmlDomParser.getListElementsByTagName("Min");

        xmlDomParser.changeTagValue(
                filePath, "Price", null, "Max", maxValues.get(0) + "0", 0);
        for (int i = 0; i < parents.size(); i++) {
            xmlDomParser.changeTagValue(
                    filePath, "Name", parents.get(i), "Max", maxValues.get(i + 1) + "0", 0);
            xmlDomParser.changeTagValue(
                    filePath, "Name", parents.get(i), "Min", minValues.get(i) + "0", 0);
        }
    }
}
