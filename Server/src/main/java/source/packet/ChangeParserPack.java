package source.packet;

import source.ServerLoader;

import java.io.*;
import java.util.Scanner;

/**
 * Сообщение об изменении типа парсера на сервере
 */

public class ChangeParserPack extends AbstractPack{
    int typeOfParser;

    public ChangeParserPack() {

    }

    @Override
    public short getId() {
        return 10;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        typeOfParser = ois.readInt();
    }

    @Override
    public void handle() {
        File typeParserFile = new File("typeParserFile");
        try {
            PrintWriter out = new PrintWriter(typeParserFile.getAbsoluteFile());
            out.print(typeOfParser);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
