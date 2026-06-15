/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.OvertimeModel;

/**
 *
 * @author USer
 */
public class OvertimeTableModel extends AbstractTableModel {

    private final List<OvertimeModel> list;

    public OvertimeTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addOvertime(OvertimeModel overtimemodel) {
        list.add(overtimemodel);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Success add data!");
    }

    public void editOvertime(int id, OvertimeModel overtimemodel) {
        list.add(id, overtimemodel);
        fireTableDataChanged();
        JOptionPane.showMessageDialog(null, "Success update data!");
    }

    public void deleteOvertime(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
        JOptionPane.showMessageDialog(null, "Success delete data!");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<OvertimeModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void setData(int id, OvertimeModel overtimemodel) {
        list.set(id, overtimemodel);
        fireTableRowsUpdated(id, id);
    }

    public OvertimeModel getData(int id) {
        return list.get(id);
    }

    // End interface dari service
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"Id", "Employees Name","Reason","Start Overtime","End Overtime","Duration","Overtime Date"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                list.get(rowIndex).getId();  // ID disimpan tapi akan disembunyikan di JTable
            case 1 ->
                list.get(rowIndex).getEmployeeName();
            case 2 ->
                list.get(rowIndex).getOvertimeReason();
            case 3 ->
                list.get(rowIndex).getStartTime();
            case 4 ->
                list.get(rowIndex).getEndTime();
            case 5 -> {
                int durationInMinutes = list.get(rowIndex).getDuration();
                int hours = durationInMinutes / 60;
                int minutes = durationInMinutes % 60;
                if (hours > 0 && minutes > 0) {
                    yield String.format("%d jam %d menit", hours, minutes);
                } else if (hours > 0) {
                    yield String.format("%d jam", hours);
                } else {
                    yield String.format("%d menit", minutes);
                }
            }
            case 6 ->
                list.get(rowIndex).getOvertimeDate();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
