/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.EmployeeModel;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.filechooser.FileSystemView;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
import service.EmployeeService;

/**
 *
 * @author wayan
 */
public class EmployeeDAO implements EmployeeService {

    private final Connection conn;

    public EmployeeDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addEmployee(EmployeeModel userModel) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String checkSql = "SELECT nik FROM employee WHERE nik = ? and isdeleted = 0";
        String insertSql = "INSERT INTO employee(nik, employeename, address, phonenumber, iddeptemployee, idlevelemployee, password) VALUES (?,?,?,?,?,?,?)";

        try {
            // Cek apakah NIK sudah ada
            st = conn.prepareStatement(checkSql);
            st.setString(1, userModel.getNik());
            rs = st.executeQuery();

            if (rs.next()) {
                // Jika ada data, berarti NIK sudah digunakan
                JOptionPane.showMessageDialog(null, "NIK sudah ada");
                return;
            }

            // Tutup statement sebelumnya sebelum membuat yang baru
            st.close();

            // NIK belum ada, lanjutkan dengan insert
            st = conn.prepareStatement(insertSql);
            st.setString(1, userModel.getNik());
            st.setString(2, userModel.getEmployeeName());
            st.setString(3, userModel.getAddress());
            st.setString(4, userModel.getPhoneNumber());
            st.setInt(5, userModel.getIdDept());
            st.setInt(6, userModel.getIdLevel());
            st.setString(7, userModel.getPassword());

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tambah data gagal");
            System.err.println("Error add employee : " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                System.err.println("Error finally add employee : " + e);
            }
        }
    }


    @Override
    public void editEmployee(EmployeeModel userModel) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String checkNikSql = "SELECT COUNT(*) FROM employee WHERE nik = ? AND idemployee != ? AND isdeleted = 0";
        String updateSql = "UPDATE employee SET nik=?, employeename=?, address=?, phonenumber=?, iddeptemployee=?, idlevelemployee=?, password=?, updated_at=? WHERE idemployee=?";

        try {
            // Cek apakah NIK sudah digunakan oleh employee lain
            st = conn.prepareStatement(checkNikSql);
            st.setString(1, userModel.getNik());
            st.setInt(2, userModel.getId());
            rs = st.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "NIK sudah ada");
                return;
            }
            st.close();

            // Lanjutkan update jika NIK tidak duplikat
            st = conn.prepareStatement(updateSql);
            st.setString(1, userModel.getNik());
            st.setString(2, userModel.getEmployeeName());
            st.setString(3, userModel.getAddress());
            st.setString(4, userModel.getPhoneNumber());
            st.setInt(5, userModel.getIdDept());
            st.setInt(6, userModel.getIdLevel());
            st.setString(7, userModel.getPassword());
            st.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            st.setInt(9, userModel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Perbarui data gagal");
            System.err.println("Error edit employee: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                System.err.println("Error finally edit employee: " + e);
            }
        }
    }


    @Override
    public void deleteEmployee(EmployeeModel userModel) {
        PreparedStatement st = null;
        String sql = "UPDATE employee SET isdeleted = 1 WHERE idemployee=?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, userModel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hapus data gagal");
            System.err.println("Error ddelete employee : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally hapus user : " + e);
                }
            }
        }
    }

    @Override
    public EmployeeModel getById(int id) {
        String sql = "select e.idemployee,e.nik,e.iddeptemployee,d.deptname,e.idlevelemployee,l.levelname,e.employeename,e.address,e.phonenumber,e.password  \n" +
                     "from employee as e left join level as l on e.idlevelemployee = l.idlevel left join departement as d on e.iddeptemployee = d.iddept where e.isdeleted=0 and e.idemployee = ?";
        return getUserByQuery(sql, id);
    }

    @Override
    public List<EmployeeModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EmployeeModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "select e.idemployee,e.nik,e.iddeptemployee,d.deptname,e.idlevelemployee,l.levelname,e.employeename,e.address,e.phonenumber,e.password  \n" +
        "from employee as e left join level as l on e.idlevelemployee = l.idlevel left join departement as d on e.iddeptemployee = d.iddept where e.isdeleted=0 order by e.idemployee desc";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                EmployeeModel userModel = new EmployeeModel();

                userModel.setId(rs.getInt("idemployee"));
                userModel.setNik(rs.getString("nik"));
                userModel.setEmployeeName(rs.getString("employeename"));
                userModel.setAddress(rs.getString("address"));
                userModel.setPhoneNumber(rs.getString("phonenumber"));
                userModel.setDeptName(rs.getString("deptname"));
                userModel.setLevelName(rs.getString("levelname"));
                userModel.setIdDept(rs.getInt("iddeptemployee"));
                userModel.setIdLevel(rs.getInt("idlevelemployee"));

                list.add(userModel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data employee : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data employee : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data employee : " + e);
                }
            }
        }
    }


    private EmployeeModel getUserByQuery(String sql, Object... params) {
        EmployeeModel userModel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                userModel = mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return userModel;
    }

    private EmployeeModel mapUser(ResultSet rs) throws SQLException {
        EmployeeModel user = new EmployeeModel();
        
        user.setId(rs.getInt("idemployee"));
        user.setNik(rs.getString("nik"));
        user.setEmployeeName(rs.getString("employeename"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNumber(rs.getString("phonenumber"));
        user.setDeptName(rs.getString("deptname"));
        user.setLevelName(rs.getString("levelname"));
        user.setIdDept(rs.getInt("iddeptemployee"));
        user.setIdLevel(rs.getInt("idlevelemployee"));
        return user;
    }
    
    @Override
    public void exportEmployeeToExcel() {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

//    @Override
//    public void exportEmployeeToExcel() {
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        String sql = "SELECT nik, employeename, address, phonenumber, deptname, levelname FROM employee WHERE isdeleted = 0";
//
//        try {
//            st = conn.prepareStatement(sql);
//            rs = st.executeQuery();
//
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Data");
//            Row header = sheet.createRow(0);
//
//            int columnCount = rs.getMetaData().getColumnCount();
//
//            // Header
//            for (int i = 1; i <= columnCount; i++) {
//                Cell cell = header.createCell(i - 1);
//                cell.setCellValue(rs.getMetaData().getColumnName(i));
//            }
//
//            // Insert Data to cell
//            int rowNum = 1;
//            while (rs.next()) {
//                Row row = sheet.createRow(rowNum++);
//                for (int i = 1; i <= columnCount; i++) {
//                    row.createCell(i - 1).setCellValue(rs.getString(i));
//                }
//            }
//
//            // Filepath
//            File downloadFolder = FileSystemView.getFileSystemView().getDefaultDirectory();
//            String filePath = downloadFolder.getAbsolutePath() + File.separator + "data_employee.xlsx";
//
//            try (FileOutputStream out = new FileOutputStream(filePath)) {
//                workbook.write(out);
//                workbook.close();
//                JOptionPane.showMessageDialog(null, "Export to excel success \nDownloaded on : " + filePath);
//            } catch (IOException ex) {
//                Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
//                JOptionPane.showMessageDialog(null, "Failed Export to excel");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error get data user : " + e);
//        } finally {
//            if (st != null) {
//                try {
//                    st.close();
//                } catch (SQLException e) {
//                    System.out.println("Error close st get data user : " + e);
//                }
//            }
//
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    System.out.println("Error close rs get data user : " + e);
//                }
//            }
//        }
//    }
}
