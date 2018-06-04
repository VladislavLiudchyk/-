package source.packet;

import source.classes.Archive;
import source.classes.DOMParser;

import java.io.*;

/**
 * Сообщение о изменении архива
 */

public class ChangeArchivePack extends AbstractPack{
    int id;
    File file;
    String name;

    public ChangeArchivePack() {

    }

    public ChangeArchivePack(int id, Archive newArchive) {
        this.id = id;
        name = newArchive.getSurname() + " " + newArchive.getName();
        file = DOMParser.writeXML(newArchive);
    }

    @Override
    public short getId() {
        return 8;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(read(file));
        oos.writeUTF(name);
        oos.writeInt(id);
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
        } catch (IOException e) {
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
