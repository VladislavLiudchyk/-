package source.classes;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
 * Парсер DOM
 */
public class DOMParser {

    private static final Logger log = Logger.getLogger(DOMParser.class);

    /**
     * Функция записывает архив в xml файл
     * @param newArchive объект архива
     * @return созданный xml файл
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
            log.info("Создан xml файл");

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
