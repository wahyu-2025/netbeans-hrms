/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.ResignModel;

/**
 *
 * @author wayan
 */
public class ResignTableModel extends AbstractTableModel {

    private final List<ResignModel> list;

    public ResignTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addResign(ResignModel resignmodel) {
        list.add(resignmodel);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Success add data!");
    }

    public void editResign(int id, ResignModel resignmodel) {
        list.add(id, resignmodel);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Success update data!");
    }

    public void deleteResign(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
        JOptionPane.showMessageDialog(null, "Success delete data!");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<ResignModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void setData(int id, ResignModel resignmodel) {
        list.set(id, resignmodel);
        fireTableRowsUpdated(id, id);
    }

    public ResignModel getData(int id) {
        return list.get(id);
    }

    // End interface dari service
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"Id", "Nama Karyawan", "Alasan Keluar"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                list.get(rowIndex).getId();  
            case 1 ->
                list.get(rowIndex).getEmployeename();
            case 2 ->
                list.get(rowIndex).getNotes();
            default ->
                null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
