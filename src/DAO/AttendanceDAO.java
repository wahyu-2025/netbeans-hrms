/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import service.AttendanceService;
import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.AttendanceModel;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import service.AttendanceService;


/**
 *
 * @author myama
 */
public class AttendanceDAO implements AttendanceService {
    
    private final Connection conn;
    
      public AttendanceDAO() {
        conn = Koneksi.koneksi();
    }
    
   @Override
    public boolean addAttendance(AttendanceModel attendancemodel) {
    PreparedStatement st = null;
    String sql = "INSERT INTO attendance(idemployee,nik,employeename,attendancename,attendancetime,islate,totallateperdays) VALUES (?,?,?,?,?,?,?)";

    try {
        st = conn.prepareStatement(sql);
        st.setInt(1, attendancemodel.getIdEmployee());
        st.setString(2, attendancemodel.getNikEmployee());
        st.setString(3, attendancemodel.getEmployeeName());
        st.setString(4, attendancemodel.getAttendanceName());
        st.setString(5, attendancemodel.getAttendanceTime());
        st.setInt(6, attendancemodel.getIsLate());
        st.setInt(7, attendancemodel.getLatePerDays());
        st.executeUpdate();
        return true; // ← berhasil
    } catch (SQLException e) {
        System.err.println("Error add attendance : " + e);
        return false; // ← gagal
    } finally {
        if (st != null) {
            try { st.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }
}
    
     @Override
    public List<AttendanceModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "select * from vw_attendance order by attendancedate DESC";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                AttendanceModel attendancemodel = new AttendanceModel();

                attendancemodel.setId(rs.getInt("idemployee"));
                attendancemodel.setNikEmployee(rs.getString("nik"));
                attendancemodel.setEmployeeName(rs.getString("employeename"));
                attendancemodel.setDeptname(rs.getString("deptname"));
                attendancemodel.setLevelname(rs.getString("levelname"));
                attendancemodel.setAttendancedate(rs.getString("attendancedate"));
                attendancemodel.setLatePerDays(rs.getInt("totallateperdays"));
                attendancemodel.setIsLate(rs.getInt("islates"));
                attendancemodel.setCheckin(rs.getString("timecheckin"));
                attendancemodel.setCheckout(rs.getString("timecheckout"));
            
                list.add(attendancemodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data attendance : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data attendance : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data attendance : " + e);
                }
            }
        }
    }
    
     private AttendanceModel getUserByQuery(String sql, Object... params) {
        AttendanceModel attendancemodel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                attendancemodel = mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return attendancemodel;
    }
     
     
    private AttendanceModel mapUser(ResultSet rs) throws SQLException {
        AttendanceModel bs = new AttendanceModel();
        
        bs.setIdEmployee(rs.getInt("idemployee"));
        bs.setNikEmployee(rs.getString("nik"));
        bs.setEmployeeName(rs.getString("employeename"));
        bs.setDeptname(rs.getString("deptname"));
        bs.setLevelname(rs.getString("levelname"));
        bs.setAttendancedate(rs.getString("attendancedate"));
        bs.setLatePerDays(rs.getInt("totallateperdays"));
        bs.setIsLate(rs.getInt("islates"));
        bs.setCheckin(rs.getString("timecheckin"));
        bs.setCheckout(rs.getString("timecheckout"));
                
        return bs;
    }
    
     @Override
    public void exportAttendance() {
           try {
               String reportPath = "src/report/Waves_Landscape_1 - Copy.jasper";

               HashMap<String, Object> parameters = new HashMap<>();
               JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
               JasperViewer viewer = new JasperViewer(print, false);
               viewer.setVisible(true);
           } catch (Exception e) {
               e.printStackTrace();
           }
//throw new UnsupportedOperationException("Not Supported Yet.");
    }
    
    @Override
    public void exportLate() {
           try {
               String reportPath = "src/report/Late.jasper";

               HashMap<String, Object> parameters = new HashMap<>();
               JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
               JasperViewer viewer = new JasperViewer(print, false);
               viewer.setVisible(true);
           } catch (Exception e) {
               e.printStackTrace();
           }
//throw new UnsupportedOperationException("Not Supported Yet.");
    }
}
