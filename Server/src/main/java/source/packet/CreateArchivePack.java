package source.packet;

import source.DataBase;
import source.ServerLoader;
import source.classes.ZIP;

import java.io.*;
import java.util.Scanner;

/**
 * Сообщение о создании нового архива
 */

public class CreateArchivePack extends AbstractPack{
    File file;
    String name;
    private static int id = 1;
    DataBase db = new DataBase();

    public CreateArchivePack() {

    }

    @Override
    public short getId() {
        return 6;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        file = write(ois.readUTF());
        name = ois.readUTF();
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
        File idFile = new File("idFile");
        try {
            Scanner scanner = new Scanner(idFile);
            id = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        db.connectToDataBase();
        db.insertIntoFileBase(id, name);
        db.closeDataBase();
        ZIP.toZIP(file, ServerLoader.getRoot().getAbsolutePath()+ "\\" + id + ".zip");

        id++;
        try {
            PrintWriter out = new PrintWriter(idFile.getAbsoluteFile());
            out.print(id);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
