package source.classes;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * DOM парсер
 */
public class DOMParser {

    private static final Logger log = Logger.getLogger(DOMParser.class);

    /**
     * Достает архив из xml файла
     * @param file xml файла
     * @return архив
     */
    public static Archive readXML(File file) {
        log.info("Чтение xml файла (DOM)");
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();


            Node node = document.getDocumentElement();

            Element element = (Element) node;
            String surname = element.getElementsByTagName("surname").item(0).getChildNodes().item(0).getNodeValue();
            String name = element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
            String patronymic = element.getElementsByTagName("patronymic").item(0).getChildNodes().item(0).getNodeValue();
            String phone = element.getElementsByTagName("phone").item(0).getChildNodes().item(0).getNodeValue();
            String job = element.getElementsByTagName("job").item(0).getChildNodes().item(0).getNodeValue();


            return new Archive(surname, name, patronymic, phone, job);
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * Записывает архив в xml файл
     * @param newArchive архив
     * @return xml файл
     */
    public static File writeXML(Archive newArchive) {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //Корневой элемент
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("archive");
            document.appendChild(rootElement);

            Element surname = document.createElement("surname");
            surname.appendChild(document.createTextNode(newArchive.getSurname()));
            rootElement.appendChild(surname);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(newArchive.getName()));
            rootElement.appendChild(name);

            Element patronymic = document.createElement("patronymic");
            patronymic.appendChild(document.createTextNode(newArchive.getPatronymic()));
            rootElement.appendChild(patronymic);

            Element phone = document.createElement("phone");
            phone.appendChild(document.createTextNode(newArchive.getPhone()));
            rootElement.appendChild(phone);

            Element job = document.createElement("job");
            job.appendChild(document.createTextNode(newArchive.getJob()));
            rootElement.appendChild(job);


            //Теперь запишем контент в XML файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);

            File file = new File("archive.xml");
            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);
            System.out.println("Файл сохранен!");

            return file;
        }
        catch (ParserConfigurationException pce)
        {
            System.out.println(pce.getLocalizedMessage());
            pce.printStackTrace();
        }
        catch (TransformerException te)
        {
            System.out.println(te.getLocalizedMessage());
            te.printStackTrace();
        }
        return null;
    }
}
