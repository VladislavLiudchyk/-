package source.classes;

import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * StAX парсер
 */
public class StAXParser {

    private static final Logger log = Logger.getLogger(StAXParser.class);

    /**
     * Считывает архив из xml файла
     * @param file xml файл
     * @return архив
     */
    public static Archive readXML(File file) {
        log.info("Чтение xml файла (StAX)");

        Archive newArchive = new Archive();

        boolean bSurname = false;
        boolean bName = false;
        boolean bPatronymic = false;
        boolean bPhone = false;
        boolean bJob = false;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new FileReader(file.getAbsolutePath()));

            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("surname")) {
                            bSurname = true;
                        } else if (qName.equalsIgnoreCase("name")) {
                            bName = true;
                        } else if (qName.equalsIgnoreCase("patronymic")) {
                            bPatronymic = true;
                        } else if (qName.equalsIgnoreCase("phone")) {
                            bPhone = true;
                        } else if (qName.equalsIgnoreCase("job")) {
                            bJob = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if(bSurname) {
                            newArchive.setSurname(characters.getData());
                            bSurname = false;
                        }
                        if(bName) {
                            newArchive.setName(characters.getData());
                            bName = false;
                        }
                        if(bPatronymic) {
                            newArchive.setPatronymic(characters.getData());
                            bPatronymic = false;
                        }
                        if(bPhone) {
                            newArchive.setPhone(characters.getData());
                            bPhone = false;
                        }
                        if(bJob) {
                            newArchive.setJob(characters.getData());
                            bJob = false;
                        }

                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return newArchive;
    }
}

