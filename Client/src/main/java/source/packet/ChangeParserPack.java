package source.packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение об изменении типа парсера на сервере
 */

public class ChangeParserPack extends AbstractPack {
    int typeOfParser;

    public ChangeParserPack() {

    }

    public ChangeParserPack(int typeOfParser) {
        this.typeOfParser = typeOfParser;
    }

    @Override
    public short getId() {
        return 10;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeInt(typeOfParser);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {

    }

    @Override
    public void handle() {

    }
}
