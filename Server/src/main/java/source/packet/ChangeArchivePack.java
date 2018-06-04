package source.packet;

import source.DataBase;
import source.ServerLoader;
import source.classes.ZIP;

import java.io.*;

/**
 * Сообщение о изменении архива
 */

public class ChangeArchivePack extends AbstractPack{
    int id;
    File file;
    String name;
    DataBase db = new DataBase();

    public ChangeArchivePack() {

    }

    @Override
    public short getId() {
        return 8;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        file = write(ois.readUTF());
        name = ois.readUTF();
        id = ois.readInt();
    }

    /**
     * Создает файл из переданной строки
     * @param text исходная строка
     * @return результат файл
     */
    public static File write(String text) {

        File file = new File("fromClient.xml");

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                // иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    @Override
    public void handle() {
        db.connectToDataBase();
        db.changeName(id, name);
        db.closeDataBase();
        new File(ServerLoader.getRoot().getAbsolutePath()+ "\\" + id + ".zip").delete(); //удаляем старый))))
        ZIP.toZIP(file, ServerLoader.getRoot().getAbsolutePath()+ "\\" + id + ".zip");
    }
}
