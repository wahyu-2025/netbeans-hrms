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
import model.LevelModel;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.LevelService;

/**
 *
 * @author USer
 */
public class LevelDAO implements LevelService {

    private final Connection conn;

    public LevelDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addLevel(LevelModel levelmodel) {
        PreparedStatement st = null;
        String sql = "INSERT INTO level(levelname) VALUES (?)";

        try {
            st = conn.prepareStatement(sql);

            st.setString(1, levelmodel.getLevelName());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tambah data gagal");
            System.err.println("Error add level : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally add level : " + e);
                }
            }
        }
    }

    @Override
    public void editLevel(LevelModel levelmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE level SET levelname=?,updated_at=? WHERE idlevel=?";

        try {
            st = conn.prepareStatement(sql);

            st.setString(1, levelmodel.getLevelName());
            st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now())); 
            st.setInt(3, levelmodel.getId()); // ID harus tipe integer sesuai DB

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Perbarui data gagal");
            System.err.println("Error edit level : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally edit level : " + e);
                }
            }
        }
    }

    @Override
    public void deleteLevel(LevelModel levelmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE level set isdeleted = 1 WHERE idlevel=?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, levelmodel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hapus data gagal");
            System.err.println("Error edit level : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally hapus level : " + e);
                }
            }
        }
    }

    @Override
    public LevelModel getById(int id) {
        String sql = "SELECT * FROM level WHERE idlevel = ? and isdeleted=0";
        return getCoursesByQuery(sql, id);
    }

    @Override
    public List<LevelModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LevelModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "SELECT * FROM level where isdeleted=0 order by idlevel desc";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                LevelModel levelmodel = new LevelModel();

                levelmodel.setId(rs.getInt("idlevel"));
                levelmodel.setLevelName(rs.getString("levelname"));

                list.add(levelmodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data level : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data level : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data level : " + e);
                }
            }
        }
    }

    @Override
    public List<LevelModel> searching(String nama) {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "SELECT * FROM level WHERE isdeleted = 0 and levelname LIKE '%" + nama + "%'";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                LevelModel levelmodel = new LevelModel();

                levelmodel.setId(rs.getInt("idlevel"));
                levelmodel.setLevelName(rs.getString("levelname"));

                list.add(levelmodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data level : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data level : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data level : " + e);
                }
            }
        }
    }

    private LevelModel getCoursesByQuery(String sql, Object... params) {
        LevelModel coursesModel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                coursesModel = mapCourses(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return coursesModel;
    }

    private LevelModel mapCourses(ResultSet rs) throws SQLException {
        LevelModel levelmodel = new LevelModel();
        levelmodel.setId(rs.getInt("idlevel"));
        levelmodel.setLevelName(rs.getString("levelname"));
        return levelmodel;
    }
    
    @Override
    public void exportLevelToExcel() {
        throw new UnsupportedOperationException("Not supported yet");
    }

//    @Override
//    public void exportLevelToExcel() {
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        String sql = "SELECT levelname FROM level";
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
//            String filePath = downloadFolder.getAbsolutePath() + File.separator + "data_courses.xlsx";
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
