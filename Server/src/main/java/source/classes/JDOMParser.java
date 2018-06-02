package source.classes;

/**
 * JDOM парсер
 */
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JDOMParser {

    private static final Logger log = Logger.getLogger(JDOMParser.class);

    /**
     * Считывает архив из xml файла
     * @param file xml файл
     * @return архив
     */
    public static Archive readXML(File file) {
        log.info("Чтение xml файла (JDOM)");

        SAXBuilder builder = new SAXBuilder();

        Archive newArchive = new Archive();

        try {

            Document document = (Document) builder.build(file);
            Element rootNode = document.getRootElement();

            newArchive.setSurname(rootNode.getChildText("surname"));
            newArchive.setName(rootNode.getChildText("name"));
            newArchive.setPatronymic(rootNode.getChildText("patronymic"));
            newArchive.setPhone(rootNode.getChildText("phone"));
            newArchive.setJob(rootNode.getChildText("job"));
        } catch (IOException io) {
            log.error(io.toString());
        } catch (JDOMException jdomex) {
            log.error(jdomex.toString());
        }

        return newArchive;
    }
}

