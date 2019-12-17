package util;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DOMExample {

    private static Document document;

    public DOMExample(String filePath) throws ParserConfigurationException, SAXException, IOException {
        // ѕолучение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // ѕолучили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // «апарсили XML, создав структуру Document. “еперь у нас есть доступ ко всем элементам, каким нам нужно.
        document = builder.parse(new File(filePath));
    }

    /**
     * »змен€ет значение существующего элемента и перезаписывает в новый файл
     *
     * @param fileName им€ файла, в который необходимо записать новый xml документ
     * @param parentTagName родительский тег, внутри которого располагаетс€ n тегов, подлежащих изменению
     * @param childTagName тег, подлежащий изменению
     * @param value новое значение искомого тега в формате String
     */
    public void changeTagValue(String fileName, String parentTagName, String childTagName, String value) {
        document.getDocumentElement().normalize();
        updateElementValue(parentTagName, childTagName, value);
        document.getDocumentElement().normalize();
        transformResultIntoFile(fileName);
    }

    /**
     * ќбновл€ет значение существующего элемента
     *
     * @param parentTagName родительский тег, внутри которого располагаетс€ n тегов, подлежащих изменению
     * @param childTagName тег, подлежащий изменению
     * @param value новое значение искомого тега в формате String
     */
    private static void updateElementValue(String parentTagName, String childTagName, String value) {

        NodeList nodes = document.getElementsByTagName(parentTagName);
        Element element = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            element = (Element) nodes.item(i);
            Node node = element.getElementsByTagName(childTagName).item(0).getFirstChild();
            node.setNodeValue(value);
        }
    }

    /**
     * ƒобавл€ет новый элемент в XML документ
     *
     * @param parentTagName родительский тег, внутри которого будет располагатьс€ новый тег
     * @param newElementName им€ нового тега
     * @param newElementTextValue значение нового тега в формате String
     */
    private static void addElement(String parentTagName, String newElementName, String newElementTextValue) {
        NodeList nodes = document.getElementsByTagName(parentTagName);
        Element element = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            element = (Element) nodes.item(i);
            Element newElement = document.createElement(newElementName);
            newElement.appendChild(document.createTextNode(newElementTextValue));
            element.appendChild(newElement);
        }
    }

    /**
     * ѕолучает список всех элементов по имени внутри корневого элемента
     * (getDocumentElement возвращает ROOT элемент XML файла)
     *
     * @param tagName им€ тега(ов), значени€ которых необходимо получить
     *
     * @return список значений тегов в формате String
     */
    public ArrayList<String> getStringElementsByTagName(String tagName) {
        NodeList elements = document.getDocumentElement().getElementsByTagName(tagName);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < elements.getLength(); i++) {
            list.add(elements.item(i).getTextContent());
        }
        return list;
    }

    private void transformResultIntoFile(String fileName) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
