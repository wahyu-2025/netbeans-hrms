/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.EmployeeModel;

/**
 *
 * @author USer
 */
public class EmployeeTableModel extends AbstractTableModel {

    private final List<EmployeeModel> list;

    public EmployeeTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addUser(EmployeeModel userModel) {
        list.add(userModel);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
//        JOptionPane.showMessageDialog(null, "Data Berhasil ditambahkan");
    }

    public void editUser(int id, EmployeeModel userModel) {
        list.add(id, userModel);
        fireTableDataChanged();
//        JOptionPane.showMessageDialog(null, "Data Berhasil diperbarui");
    }

    public void deleteUser(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
        JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<EmployeeModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void setData(int id, EmployeeModel userModel) {
        list.set(id, userModel);
        fireTableRowsUpdated(id, id);
    }

    public EmployeeModel getData(int id) {
        return list.get(id);
    }

    // End interface dari service
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"Id", "No", "NIK", "Nama","Alamat","Nomor Telepon", "Departemen", "Level"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        System.out.println("EMOLYEE COLUMN INDEX " + columnIndex);
        return switch (columnIndex) {
            case 0 ->
                list.get(rowIndex).getId();  
            case 1 ->
                " " + (rowIndex + 1); 
            case 2 ->
                list.get(rowIndex).getNik();
            case 3 ->
                list.get(rowIndex).getEmployeeName();
            case 4 ->
                list.get(rowIndex).getAddress();
            case 5 ->
                list.get(rowIndex).getPhoneNumber();
            case 6 ->
                list.get(rowIndex).getDeptName();
            case 7 ->
                list.get(rowIndex).getLevelName();
            default ->
                null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
