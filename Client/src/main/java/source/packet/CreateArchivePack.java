package source.packet;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import source.classes.Archive;
import source.classes.DOMParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * Сообщение о создании нового архива
 */

public class CreateArchivePack extends AbstractPack{

    File file;
    String name;

    public CreateArchivePack() {

    }

    public CreateArchivePack(Archive newArchive) {
        name = newArchive.getSurname() + " " + newArchive.getName();
        file = DOMParser.writeXML(newArchive);
    }

    @Override
    public short getId() {
        return 6;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(read(file));
        oos.writeUTF(name);
    }

    /**
     * Функция преобразует сорержимое файла в строку для корректной передачи
     * @param file исходный файл
     * @return результат строка
     * @throws FileNotFoundException
     */
    public static String read(File file) throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader(file));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb.toString();
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {

    }

    @Override
    public void handle() {

    }
}
