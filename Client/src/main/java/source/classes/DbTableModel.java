package source.classes;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbTableModel extends AbstractTableModel implements Serializable {
    private ArrayList<String> columnNames = new ArrayList<String>();
    private ArrayList<Class> columnTypes = new ArrayList<Class>();
    private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
    private static final long serialVersionUID = 1L;

    /**
     * Инициализация названий столбцов, типов, данных из БД
     *
     * @param rs ResultSet, который возвращается при обращении
     * к БД, отсюда берется вся информация.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setDataSource(ResultSet rs) throws SQLException, ClassNotFoundException {
        ResultSetMetaData rsmd = rs.getMetaData();
        columnNames.clear();
        columnTypes.clear();
        data.clear();

        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            columnNames.add(rsmd.getColumnName(i + 1));
            Class type = Class.forName(rsmd.getColumnClassName(i + 1));
            columnTypes.add(type);
        }
        fireTableStructureChanged();
        while (rs.next()) {
            ArrayList rowData = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                    rowData.add(rs.getString(i + 1));
                else
                    rowData.add(rs.getObject(i + 1));
            }
            synchronized (data) {
                data.add(rowData);
                this.fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        }
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Class getColumnClass(int col) {
        return columnTypes.get(col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return columnNames.get(0).equals("Username") && col == 2;
    }

    public void setValueAt(Object obj, int row, int col) {
        synchronized (data) {
            data.get(row).set(col, obj);
        }
    }

    public int getRowCount() {
        synchronized (data) {
            return data.size();
        }
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public Object getValueAt(int row, int col) {
        synchronized (data) {
            return data.get(row).get(col);
        }
    }
}

