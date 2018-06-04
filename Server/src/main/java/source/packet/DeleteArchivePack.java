package source.packet;

import source.DataBase;
import source.ServerLoader;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение об удалении архива
 */

public class DeleteArchivePack extends AbstractPack{
    private int id;
    DataBase db = new DataBase();

    public DeleteArchivePack() {

    }

    @Override
    public short getId() {
        return 9;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        id = ois.readInt();
    }

    @Override
    public void handle() {
        db.connectToDataBase();
        db.removeFromFileBase(id);
        new File(ServerLoader.getRoot().getAbsolutePath()+ "\\" + id + ".zip").delete();
        db.closeDataBase();
    }
}
