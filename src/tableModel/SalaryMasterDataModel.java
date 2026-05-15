/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.SalaryMasterData;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author myama
 */
public class SalaryMasterDataModel extends AbstractTableModel {
    private final List<SalaryMasterData>list;
    
    public SalaryMasterDataModel() {
        this.list = new ArrayList<>();
    }
    
    public void addSalary(SalaryMasterData salaryData) {
        list.add(salaryData);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }
    
    public void editSalary(int id, SalaryMasterData salaryData) {
        list.add(id, salaryData);
        fireTableDataChanged();
    }
    
    public void deleteSalary(int id) {
        list.remove(id);
        fireTableRowsDeleted(id, id);
    }
    
    public void clear() {
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData(List<SalaryMasterData> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }
    
    public void setData(int id, SalaryMasterData salaryData) {
        list.set(id, salaryData);
        fireTableRowsUpdated(id, id);
    }
    
    public SalaryMasterData getData(int id) {
        return list.get(id);
    }
    
    public int getRowCount() {
        return list.size();
    }
    
      private final String[] columnNames = {"Id", "Nama", "Gaji Utama Karyawan"};
      
        @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    private String formatRupiah(int amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(amount);
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> list.get(rowIndex).getId();  
            case 1 -> list.get(rowIndex).getEmployeeName();
            case 2 -> formatRupiah(list.get(rowIndex).getSalary()); // Format rupiah di sini
            default -> null;
        };

    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
