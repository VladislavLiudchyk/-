package source.packet;

import source.classes.DbTableModel;
import source.gui.Main;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Сообщение о загрузке содержания главной таблицы пользователя
 */

public class MainTablePack extends AbstractPack{
    DbTableModel model;

    public MainTablePack() {

    }

    public MainTablePack(DbTableModel model) {
        this.model = model;
    }

    @Override
    public short getId() {
        return 5;
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
    }

    @Override
    public void handle() {
        final JTable table = Main.getMainWindow().getTable();
        table.setModel(model);
    }
}
