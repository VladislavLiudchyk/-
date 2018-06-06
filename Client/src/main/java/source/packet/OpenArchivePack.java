package source.packet;

import source.classes.Archive;
import source.gui.CreateArchiveWindow;
import source.gui.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение о просмотре архива
 */

public class OpenArchivePack extends AbstractPack{
    private Archive openArchive;
    private int id;

    public OpenArchivePack() {

    }

    public OpenArchivePack(int id) {
        this.id = id;
    }

    @Override
    public short getId() {
        return 7;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeInt(id);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        try {
            openArchive = (Archive) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() {
        Main.setCreateArchiveWindow(new CreateArchiveWindow(openArchive));
    }
}
