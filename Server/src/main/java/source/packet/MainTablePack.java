package source.packet;


import source.DataBase;
import source.ServerLoader;
import source.classes.DbTableModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Сообщение о загрузке содержания главной таблицы с данными
 */

public class MainTablePack extends AbstractPack{

    DbTableModel model;
    DataBase db = new DataBase();

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
        oos.writeObject(model);
    }

    @Override
    public void read(ObjectInputStream ois) throws IOException {

    }

    @Override
    public void handle() {
        db.connectToDataBase();
        model = new DbTableModel();
        try {
            model.setDataSource(db.getFileBaseResultSet());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        db.closeDataBase();
    }
}
