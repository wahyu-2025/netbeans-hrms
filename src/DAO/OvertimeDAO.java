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
import model.OvertimeModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
import service.OvertimeService;

/**
 *
 * @author USer
 */
public class OvertimeDAO implements OvertimeService {

    private final Connection conn;

    public OvertimeDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addOvertime(OvertimeModel overtimemodel) {
        PreparedStatement st = null;
        String sql = "INSERT INTO overtime(idemployeeovertime,overtimereason,starttime,endtime,duration) VALUES (?,?,?,?,?)";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, overtimemodel.getIdEmployee());
            st.setString(2, overtimemodel.getOvertimeReason());
            st.setString(3, overtimemodel.getStartTime());
            st.setString(4, overtimemodel.getEndTime());
            st.setInt(5, overtimemodel.getDuration());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tambah data gagal");
            System.err.println("Error add user : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally add overtime : " + e);
                }
            }
        }
    }

    @Override
    public void editOvertime(OvertimeModel overtimemodel) {
        PreparedStatement st = null;
        String sql = "UPDATE overtime SET idemployeeovertime=?, overtimereason=?,starttime=?,endtime=?,duration=?,updated_at=? WHERE idovertime=?";

        try {
            st = conn.prepareStatement(sql);
            st = conn.prepareStatement(sql);
            st.setInt(1, overtimemodel.getIdEmployee());
            st.setString(2, overtimemodel.getOvertimeReason());
            st.setString(3, overtimemodel.getStartTime());
            st.setString(4, overtimemodel.getEndTime());
            st.setInt(5, overtimemodel.getDuration());
            st.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now())); 
            st.setInt(7, overtimemodel.getId()); 

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Perbarui data gagal");
            System.err.println("Error edit overtime : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally edit overtime : " + e);
                }
            }
        }
    }

    @Override
    public void deleteOvertime(OvertimeModel overtimemodel) {
        PreparedStatement st = null;
        String sql = "UPDATE overtime SET isdeleted = 1 WHERE idovertime=?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, overtimemodel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hapus data gagal");
            System.err.println("Error delete overtime : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally hapus overtime : " + e);
                }
            }
        }
    }

    @Override
    public OvertimeModel getById(int id) {
        String sql = "select o.idovertime,o.idemployeeovertime,e.employeename,o.overtimereason,o.starttime,o.endtime,o.duration,date(o.created_at) as overtimedate"
                + " from overtime as o\n" +
                      "left join employee as e on o.idemployeeovertime = e.idemployee where o.isdeleted=0 and o.idovertime = ?";
        return getUserByQuery(sql, id);
    }

    @Override
    public List<OvertimeModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<OvertimeModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "select o.idovertime,o.idemployeeovertime,e.employeename,o.overtimereason,o.starttime,o.endtime,o.duration,date(o.created_at) as overtimedate"
                + " from overtime as o\n" +
                     "left join employee as e on o.idemployeeovertime = e.idemployee where o.isdeleted=0 order by o.idovertime desc";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                OvertimeModel overtimemodel = new OvertimeModel();

                overtimemodel.setId(rs.getInt("idovertime"));
                overtimemodel.setEmployeeName(rs.getString("employeename"));
                overtimemodel.setStartTime(rs.getString("starttime"));
                overtimemodel.setEndTime(rs.getString("endtime"));
                overtimemodel.setOvertimeReason(rs.getString("overtimereason"));
                overtimemodel.setDuration(rs.getInt("duration"));
                overtimemodel.setOvertimeDate(rs.getString("overtimedate"));
                
                list.add(overtimemodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data overtinme : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data overtime : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data overtime : " + e);
                }
            }
        }
    }

    private OvertimeModel getUserByQuery(String sql, Object... params) {
        OvertimeModel overtimemodel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                overtimemodel = mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return overtimemodel;
    }

    private OvertimeModel mapUser(ResultSet rs) throws SQLException {
        OvertimeModel bs = new OvertimeModel();
        bs.setId(rs.getInt("idovertime"));
        bs.setEmployeeName(rs.getString("employeename"));
        bs.setStartTime(rs.getString("starttime"));
        bs.setEndTime(rs.getString("endtime"));
        bs.setOvertimeReason(rs.getString("overtimereason"));
        bs.setDuration(rs.getInt("duration"));
        bs.setOvertimeDate(rs.getString("overtimedate"));
        return bs;
    }
    
    @Override
    public void exportOvertimeToExcel() {
           try {
               String reportPath = "src/report/Overtime.jasper";

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
