package source.packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение об удалении архива
 */

public class DeleteArchivePack extends AbstractPack{
    private int id;

    public DeleteArchivePack() {

    }

    public DeleteArchivePack(int id) {
        this.id = id;
    }

    @Override
    public short getId() {
        return 9;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeInt(id);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {

    }

    @Override
    public void handle() {

    }
}
