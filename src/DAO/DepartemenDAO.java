/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import javax.swing.filechooser.FileSystemView;
import model.DeptModel;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.DeptService;

/**
 *
 * @author USer
 */
public class DepartemenDAO implements DeptService {

    private final Connection conn;

    public DepartemenDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addDept(DeptModel deptmodel) {
        PreparedStatement st = null;
        String sql = "INSERT INTO departement(deptname, deptcode, description) VALUES (?, ?, ?)";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, deptmodel.getDeptName());
            st.setString(2, deptmodel.getDeptCode());
            st.setString(3, deptmodel.getDescription());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed add data!");
            System.err.println("Error add department : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally add department : " + e);
                }
            }
        }
    }

    @Override
    public void editDept(DeptModel deptmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE departement SET deptname=?, deptcode=?, description=?,updated_at=? WHERE iddept=?";

        try {
            st = conn.prepareStatement(sql);

            st.setString(1, deptmodel.getDeptName());
            st.setString(2, deptmodel.getDeptCode());
            st.setString(3, deptmodel.getDescription());
            st.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); 
            st.setInt(5, deptmodel.getId()); // ID harus tipe integer sesuai DB

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed edit data");
            System.err.println("Error edit departement : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally edit departement : " + e);
                }
            }
        }
    }

    @Override
    public void deleteDept(DeptModel deptmodel) {
        System.out.println(deptmodel);
        PreparedStatement st = null;
        String sql = "UPDATE departement set isdeleted = 1 where iddept = ?";

        try {
            st = conn.prepareStatement(sql);
            
            st.setInt(1, deptmodel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed delete data!");
            System.err.println("Error delete department : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally delete department : " + e);
                }
            }
        }
    }

    
    @Override
    public DeptModel getById(int id) {
        String sql = "SELECT * FROM departement WHERE isdeleted = 0 AND iddept = ?";
        return getClassesByQuery(sql, id);
    }


    @Override
    public List<DeptModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DeptModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "SELECT * FROM departement where isdeleted = 0 order by iddept desc";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                DeptModel deptmodel = new DeptModel();
                deptmodel.setId(rs.getInt("iddept"));
                deptmodel.setDeptName(rs.getString("deptname"));
                deptmodel.setDeptCode(rs.getString("deptcode"));
                deptmodel.setDescription(rs.getString("description"));
                
                list.add(deptmodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data department : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data department : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data department : " + e);
                }
            }
        }
    }

    @Override
    public List<DeptModel> searching(String nama) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private DeptModel getClassesByQuery(String sql, Object... params) {
        DeptModel deptmodel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                deptmodel = mapDept(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return deptmodel;
    }

    private DeptModel mapDept(ResultSet rs) throws SQLException {
        // Masukkan data hasil akhir
        DeptModel deptmodel = new DeptModel();
        deptmodel.setId(rs.getInt("iddept"));
        deptmodel.setDeptName(rs.getString("deptname"));
        deptmodel.setDeptCode(rs.getString("deptcode"));
        deptmodel.setDescription(rs.getString("description"));
       
        return deptmodel;
    }
    
    @Override
    public void exportDeptToExcel() {
        throw new UnsupportedOperationException("Not Supported Yet.");
    }

//    @Override
//    public void exportDeptToExcel() {
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        String sql = "SELECT * FROM departement where isdeleted = 0";
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
//            String filePath = downloadFolder.getAbsolutePath() + File.separator + "data_classes.xlsx";
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
