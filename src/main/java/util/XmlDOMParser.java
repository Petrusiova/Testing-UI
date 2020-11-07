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
import java.util.List;

public class XmlDOMParser {

    private static Document document;

    public XmlDOMParser(String filePath) {
        try {
            // Получение фабрики, чтобы впоследствии получить билдер документов.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
            document = builder.parse(new File(filePath));

        } catch (ParserConfigurationException|IOException|SAXException e){
            e.printStackTrace();
        }
    }

    /**
     * Изменяет значение существующего элемента и перезаписывает в новый файл
     *
     * @param fileName      имя файла, в который необходимо записать новый xml документ
     * @param parentTagName родительский тег, внутри которого располагается n тегов, подлежащих изменению
     * @param childTagName  тег, подлежащий изменению
     * @param value         новое значение искомого тега в формате String
     */
    public void changeTagValue(String fileName,
                               String parentTagName,
                               String parentTagValue,
                               String childTagName,
                               String value,
                               int index) {

        document.getDocumentElement().normalize();
        if (parentTagValue != null) {
            updateElementValueWithParentValue(parentTagName, parentTagValue, childTagName, value, index);
        }
        else {
            updateElementValueWithParentTag(parentTagName, childTagName, value, index);
        }
        document.getDocumentElement().normalize();
        transformResultIntoFile(fileName);
    }

    /**
     * Обновляет значение существующего элемента
     *
     * @param parentTagName родительский тег, внутри которого располагается n тегов, подлежащих изменению
     * @param childTagName  тег, подлежащий изменению
     * @param value         новое значение искомого тега в формате String
     * @param index порядковый номер тега, подлежащий изменению
     */
    private void updateElementValueWithParentValue(String parentTagName,
                                                   String parentTagValue,
                                                   String childTagName,
                                                   String value,
                                                   int index) {

        getAllNodeElementsByNeighbourTagValue(parentTagName, parentTagValue, childTagName)
                .item(index).getFirstChild().setNodeValue(value);
    }

    private void updateElementValueWithParentValue(String parentTagName,
                                                   String parentTagValue,
                                                   String childTagName,
                                                   String value) {

        getAllNodeElementsByNeighbourTagValue(parentTagName, parentTagValue, childTagName)
                .item(0).setNodeValue(value);
    }

    private static void updateElementValueWithParentTag(String parentTagName,
                                                        String childTagName,
                                                        String value,
                                                        int index) {

        NodeList nodes = document.getElementsByTagName(parentTagName);
        Element element = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            element = (Element) nodes.item(i);
            Node node = element.getElementsByTagName(childTagName).item(index).getFirstChild();
            node.setNodeValue(value);
        }
    }

    private static void updateElementValueWithParentTag(String parentTagName,
                                                        String childTagName,
                                                        String value) {

        NodeList nodes = document.getElementsByTagName(parentTagName);
        Element element = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            element = (Element) nodes.item(i);
            Node node = element.getElementsByTagName(childTagName).item(0).getFirstChild();
            node.setNodeValue(value);
        }
    }

    /**
     * Добавляет новый элемент в XML документ
     *
     * @param parentTagName       родительский тег, внутри которого будет располагаться новый тег
     * @param newElementName      имя нового тега
     * @param newElementTextValue значение нового тега в формате String
     */
    private static void addElement(String parentTagName,
                                   String newElementName,
                                   String newElementTextValue) {

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
     * Получает список всех элементов по имени внутри корневого элемента
     * (getDocumentElement возвращает ROOT элемент XML файла)
     *
     * @param tagName имя тега(ов), значения которых необходимо получить
     * @return список значений тегов в формате String
     */
    public ArrayList<String> getListElementsByTagName(String tagName) {

        NodeList elements = document.getDocumentElement().getElementsByTagName(tagName);
        return createList(elements);
    }

    public String getStringByNeighbourValue(String neighbourTagName,
                                            String neighbourTagValue,
                                            String childTagName,
                                            int index){

        return getAllNodeElementsByNeighbourTagValue(neighbourTagName, neighbourTagValue, childTagName)
                .item(index).getTextContent();
    }

    public String getStringByNeighbourValue(String neighbourTagName,
                                            String neighbourTagValue,
                                            String childTagName){

        return getAllNodeElementsByNeighbourTagValue(neighbourTagName, neighbourTagValue, childTagName)
                .item(0).getTextContent();
    }

    public List<String> getListByNeighbourValue(String neighbourTagName,
                                                String neighbourTagValue,
                                                String childTagName){

        NodeList list = getAllNodeElementsByNeighbourTagValue(neighbourTagName, neighbourTagValue, childTagName);
        return createList(list);
    }

    public List<String> getListByNeighbourAttrValue(String neighbourTagName,
                                                    int attrIndex,
                                                    String attrName,
                                                    String attrValue,
                                                    String childTagName){

        NodeList list = getAllNodeElementsByNeighbourAttrValue(neighbourTagName, attrIndex, attrName, attrValue, childTagName);
        return createList(list);
    }

    public NamedNodeMap getNodeAttributes(String tagName, int nodeIndex) {
        NodeList nodes = document.getElementsByTagName(tagName);
        Element element = (Element) nodes.item(nodeIndex);
        NamedNodeMap attributes = element.getAttributes();


        return attributes;
    }

    /**
     * Получает NodeList элементов по имени соседнего или родительского элемента
     *
     * @param neighbourTagName имя тега(ов), по значению которого производится поиск
     * @param neighbourTagValue значение тега, по которому производится поиск
     * @param childTagName тег, ноду которого необходимо найти
     * @return список тегов
     */
    private NodeList getAllNodeElementsByNeighbourTagValue(String neighbourTagName,
                                                           String neighbourTagValue,
                                                           String childTagName) {

        NodeList nodes = document.getElementsByTagName(neighbourTagName);
        NodeList nodeList = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals(neighbourTagValue)) {
                nodeList = createNodeList(nodes.item(i), childTagName);
            }
        }
        return nodeList;
    }

    /**
     * Получает NodeList элементов по атрибутам соседнего или родительского элемента
     *
     * @param neighbourTagName имя тега(ов), по значению которого производится поиск
     * @param attrName наименование атрибута тега, по которому производится поиск
     * @param attrValue значение атрибута тега, по которому производится поиск
     * @param childTagName тег, ноду которого необходимо найти
     * @return список тегов
     */
    private NodeList getAllNodeElementsByNeighbourAttrValue(String neighbourTagName,
                                                            int attrIndex,
                                                            String attrName,
                                                            String attrValue,
                                                            String childTagName) {

        NodeList nodes = document.getElementsByTagName(neighbourTagName);
        NodeList nodeList = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Node node = element.getAttributes().item(attrIndex);
            if (node.getNodeName().equals(attrName) && node.getNodeValue().equals(attrValue)) {
                nodeList = createNodeList(nodes.item(i), childTagName);
            }
        }
        return nodeList;
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

    private ArrayList<String> createList(NodeList nodeList){

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++){
            arrayList.add(nodeList.item(i).getTextContent());
        }
        return arrayList;
    }

    private NodeList createNodeList(Node node, String childTagName){

        Element element = (Element) node;
        NodeList nodeList = null;
        nodeList = element.getElementsByTagName(childTagName);

        if (nodeList.getLength() < 1) {
            element = (Element) element.getParentNode();
            nodeList = element.getElementsByTagName(childTagName);
        }
        return nodeList;
    }
}
