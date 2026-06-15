/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.AttendanceLimitModel;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author USer
 */
public class AttendanceLimitTableModel extends AbstractTableModel {

    private final List<AttendanceLimitModel> list;

    public AttendanceLimitTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addAttendanceLimit(AttendanceLimitModel attendanceLimitModel) {
        list.add(attendanceLimitModel);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Success add data!");
    }

    public void editAttendanceLimit(int id, AttendanceLimitModel attendanceLimitModel) {
        list.add(id, attendanceLimitModel);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Success update data!");
    }

    public void deleteAttendanceLimit(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
        JOptionPane.showMessageDialog(null, "Success delete data!");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<AttendanceLimitModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void setData(int id, AttendanceLimitModel attendanceLimit) {
        list.set(id, attendanceLimit);
        fireTableRowsUpdated(id, id);
    }

    public AttendanceLimitModel getData(int id) {
        return list.get(id);
    }

    // End interface dari service
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"Id","Departement Name", "Attendance Name", "Attendance Start Time","Attendance Limit"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> list.get(rowIndex).getId();  
            case 1 -> list.get(rowIndex).getDeptname();
            case 2 -> list.get(rowIndex).getAttendancename(); 
            case 3 -> list.get(rowIndex).getStarttimestr();
            case 4 -> list.get(rowIndex).getEndtime(); 
            default -> null;
        };

    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
