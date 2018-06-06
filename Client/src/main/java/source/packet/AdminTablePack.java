package source.packet;

import source.classes.DbTableModel;
import source.gui.Main;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JTable table = Main.getAdminWindow().getTable();
        table.setModel(model);
        TableColumn priorityColumn = table.getColumnModel().getColumn(2);
        final JComboBox comboBox = new JComboBox();
        comboBox.addItem("0");
        comboBox.addItem("1");
        comboBox.addItem("2");
        priorityColumn.setCellEditor(new DefaultCellEditor(comboBox));
        comboBox.addActionListener(new ActionListener() {
            private int count = 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count % 2 != 0) {
                    count++;
                    return;
                }
                JComboBox cb = (JComboBox)e.getSource();
                int priority = Integer.parseInt((String)cb.getSelectedItem());
                JTable table = Main.getAdminWindow().getTable();
                int index = table.getSelectionModel().getLeadSelectionIndex();
                String username = (String)table.getModel().getValueAt(index, 0);
                Main.sendPacket(new ChangePriorityPack(username, priority));
                count++;
            }
        });
        table.removeColumn(table.getColumnModel().getColumn(1));

        Main.getAdminWindow().getComboBox().setSelectedIndex(typeOfParser);
    }
}
