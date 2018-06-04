package source.packet;

import source.ServerLoader;
import source.classes.Archive;
import source.classes.DOMParser;
import source.classes.Parser;
import source.classes.ZIP;

import java.io.File;
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

    public OpenArchivePack(Archive openCase) {
        this.openArchive = openCase;
    }

    @Override
    public short getId() {
        return 7;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {
        oos.writeObject(openArchive);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        id = ois.readInt();
    }

    @Override
    public void handle() {
        openArchive = Parser.readXML(ZIP.fromZIP(new File(ServerLoader.getRoot().getAbsolutePath()+ "\\" + id + ".zip")));
    }
}
