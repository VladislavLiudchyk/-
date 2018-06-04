package source.packet;

import source.classes.DbTableModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение о загрузке содержания таблицы админа
 */

public class AdminTablePack extends AbstractPack{

    private DbTableModel model;
    private int typeOfParser;

    public AdminTablePack() {

    }

    @Override
    public short getId() {
        return 3;
    }

    @Override
    public void write(ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {
        try {
            model = (DbTableModel) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        typeOfParser = ois.readInt();
    }

    @Override
    public void handle() {

    }
}
