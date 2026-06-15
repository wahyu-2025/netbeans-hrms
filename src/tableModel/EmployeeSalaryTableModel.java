/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.EmployeeSalaryModel;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 *
 * @author USer
 */
public class EmployeeSalaryTableModel extends AbstractTableModel {

    private final List<EmployeeSalaryModel> list;

    public EmployeeSalaryTableModel() {
        this.list = new ArrayList<>();
    }

    // Interface dari service
    public void addEmployeeSalary(EmployeeSalaryModel employeesalary) {
        list.add(employeesalary);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
//        JOptionPane.showMessageDialog(null, "Data Berhasil ditambahkan");
    }

    public void deleteEmployeeSalary(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
        JOptionPane.showMessageDialog(null, "Success delete data!");
    }

    public void clear() {
        list.clear();
        fireTableDataChanged();
    }

    public void setData(List<EmployeeSalaryModel> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }

    public void setData(int id, EmployeeSalaryModel employeesalarymodel) {
        list.set(id, employeesalarymodel);
        fireTableRowsUpdated(id, id);
    }

    public EmployeeSalaryModel getData(int id) {
        return list.get(id);
    }

    // End interface dari service
    @Override
    public int getRowCount() {
        return list.size();
    }

    private final String[] columnNames = {"Id","NIK", "Employee Name","Departemen","Position", "Salary","Overtime Bonus","Late Cut","Total Salary","Month","Year"};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    private String formatRupiah(int amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(amount);
    }
    
    private String getMonthName(String monthNumber) {
        switch (monthNumber) {
            case "1": return "Januari";
            case "2": return "Februari";
            case "3": return "Maret";
            case "4": return "April";
            case "5": return "Mei";
            case "6": return "Juni";
            case "7": return "Juli";
            case "8": return "Agustus";
            case "9": return "September";
            case "10": return "Oktober";
            case "11": return "November";
            case "12": return "Desember";
            default: return "-";
        }
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> list.get(rowIndex).getId();  
            case 1 -> list.get(rowIndex).getEmployeeNik();
            case 2 -> list.get(rowIndex).getEmployeeName();
            case 3 -> list.get(rowIndex).getDeptName();
            case 4 -> list.get(rowIndex).getLevelName();
            case 5 -> formatRupiah(list.get(rowIndex).getBasicSalary());
            case 6 -> formatRupiah(list.get(rowIndex).getOvertimeBonus());
            case 7 -> formatRupiah(list.get(rowIndex).getCutLate());
            case 8 -> formatRupiah(list.get(rowIndex).getSalaryThisMonth());
            case 9 -> {
                LocalDate period = list.get(rowIndex).getSalaryPeriod();
                yield period != null
                        ? period.getMonth().getDisplayName(
                                TextStyle.FULL,
                                new Locale("id", "ID"))
                        : "-";
            }

            case 10 -> {
                LocalDate period = list.get(rowIndex).getSalaryPeriod();
                yield period != null ? period.getYear() : "-";
            }
                        default -> null;
                    };

    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
