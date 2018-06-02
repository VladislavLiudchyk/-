package source.classes;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс, обрабатывающий файлы соответствующим парсером
 */
public class Parser {

    /**
     * Считывает архив из xml файла
     * @param file xml файл
     * @return архив
     */
    public static Archive readXML(File file) {
        int type = getTypeOfParser();

        switch (type) {
            case 0: {
                return DOMParser.readXML(file);
            }
            case 1: {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = factory.newSAXParser();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                SAXPars saxp = new SAXPars();

                try {
                    parser.parse(file, saxp);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return saxp.getResult();
            }
            case 2: {
                return StAXParser.readXML(file);
            }
            case 3: {
                return JDOMParser.readXML(file);
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Получает тип парсера, который указал админ
     * @return тип парсера
     */
    public static int getTypeOfParser(){
        File typeParserFile = new File("typeParserFile");
        int typeOfParser = -1;
        try {
            Scanner scanner = new Scanner(typeParserFile);
            typeOfParser = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return typeOfParser;
    }
}
