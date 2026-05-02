/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.LevelModel;

/**
 *
 * @author wayan
 */
public class LevelTableModel extends AbstractTableModel {

    private final List<LevelModel> list;

    public LevelTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addLevel(LevelModel levelmodel) {
        list.add(levelmodel);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data Berhasil ditambahkan");
    }

    public void editLevel(int id, LevelModel levelmodel) {
        list.add(id, levelmodel);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Data Berhasil diperbarui");
    }

    public void deleteLevel(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
        JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<LevelModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void setData(int id, LevelModel coursesModel) {
        list.set(id, coursesModel);
        fireTableRowsUpdated(id, id);
    }

    public LevelModel getData(int id) {
        return list.get(id);
    }

    // End interface dari service
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"ID", "No", "Position Name"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        System.out.println("column index" + columnIndex);
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getId();
            case 1:
                return " " + (rowIndex + 1);
            case 2:
                return list.get(rowIndex).getLevelName();
            default:
                return null;
        }

    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
