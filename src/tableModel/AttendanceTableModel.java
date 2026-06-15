/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.AttendanceModel;

/**
 *
 * @author myama
 */
public class AttendanceTableModel extends AbstractTableModel {
    private final List<AttendanceModel> list;

    public AttendanceTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addAttendance(AttendanceModel attendancemodel) {
        list.add(attendancemodel);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
        JOptionPane.showMessageDialog(null, "Data Berhasil ditambahkan");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }
//
    public void setData(List<AttendanceModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"Id","NIK", "Employee Name","Department","Position","Date Of Absence","Late","On Time","Time Comes","Time To Go"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

   @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;

        switch (columnIndex) {
            case 0:
                value = list.get(rowIndex).getIdEmployee();
                break;
            case 1:
                value = list.get(rowIndex).getNikEmployee();
                break;
            case 2:
                value = list.get(rowIndex).getEmployeeName();
                break;
            case 3:
                value = list.get(rowIndex).getDeptname();
                break;
            case 4:
                value = list.get(rowIndex).getLevelname();
                break;
            case 5:
                value = list.get(rowIndex).getAttendancedate();
                break;
            case 6:
                int lateMinutes = list.get(rowIndex).getLatePerDays();
                if (lateMinutes > 60) {
                    int hours = lateMinutes / 60;
                    int minutes = lateMinutes % 60;
                    value = String.format("%d jam %d menit", hours, minutes);
                } else {
                    value = lateMinutes + " menit";
                }
                break;
            case 7:
                int isLate = list.get(rowIndex).getIsLate();
                value = (isLate == 1) ? "Terlambat" : "Tepat Waktu";
                break;
            case 8:
                String checkin = list.get(rowIndex).getCheckin();
                value = (checkin == null || checkin.equals("00:00:00")) ? "-" : formatTime(checkin);
                break;
            case 9:
                String checkout = list.get(rowIndex).getCheckout();
                value = (checkout == null || checkout.equals("00:00:00")) ? "-" : formatTime(checkout);
                break;

            default:
                value = null;
                break;
        }

        return value;
    }

    private String formatTime(String time24) {
        try {
            java.text.SimpleDateFormat sdf24 = new java.text.SimpleDateFormat("HH:mm");
            java.text.SimpleDateFormat sdf12 = new java.text.SimpleDateFormat("h:mm a");
            java.util.Date date = sdf24.parse(time24);
            return sdf12.format(date).toLowerCase(); // output seperti 6:00 pm
        } catch (Exception e) {
            return time24; // fallback jika parsing gagal
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}
