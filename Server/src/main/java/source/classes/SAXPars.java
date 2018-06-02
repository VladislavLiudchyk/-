package source.classes;

/**
 * Created by Vladislav Liudchyk on 02.06.2018
 */
import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/**
 * SAX парсер
 */
public class SAXPars extends DefaultHandler{

    Archive newArchive = new Archive();
    String thisElement = "";

    private static final Logger log = Logger.getLogger(SAXPars.class);

    /**
     * Возвращает архив, сгенерированный из xml файла
     * @return архив
     */
    public Archive getResult(){
        return newArchive;
    }

    @Override
    public void startDocument() throws SAXException {
        log.info("Чтение xml файла (SAX)");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (thisElement.equals("surname")) {
            newArchive.setSurname(new String(ch, start, length));
        }
        if (thisElement.equals("name")) {
            newArchive.setName(new String(ch, start, length));
        }
        if (thisElement.equals("patronymic")) {
            newArchive.setPatronymic(new String(ch, start, length));
        }
        if (thisElement.equals("phone")) {
            newArchive.setPhone(new String(ch, start, length));
        }
        if (thisElement.equals("job")) {
            newArchive.setJob(new String(ch, start, length));
        }
    }

    @Override
    public void endDocument() {

    }
}
