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
import model.AttendanceLimitModel;
import service.AttendanceLimitService;

/**
 *
 * @author USer
 */
public class AttendanceLimitDAO implements AttendanceLimitService {

    private final Connection conn;

    public AttendanceLimitDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addAttendanceLimit(AttendanceLimitModel thresholdmodel) {
        PreparedStatement st = null;
        String sql = "INSERT INTO threshold(iddeptthreshold,attendancename, starttimestr,endtimestr,thresholdtimestr) VALUES (?,?,?,?,?)";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, thresholdmodel.getIdDept());
            st.setString(2, thresholdmodel.getAttendancename());
            st.setString(3, thresholdmodel.getStarttimestr());
            st.setString(4, thresholdmodel.getEndtime());
            st.setString(5, thresholdmodel.getAttendanceLimittime());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed add data");
            System.err.println("Error add user : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally add user : " + e);
                }
            }
        }
    }

    @Override
    public void editAttendanceLimit(AttendanceLimitModel thresholdmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE threshold SET iddeptthreshold=?, attendancename=?, starttimestr=?,endtimestr=?,thresholdtimestr=?,updated_at=? WHERE idthreshold=?";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, thresholdmodel.getIdDept());
            st.setString(2, thresholdmodel.getAttendancename());
            st.setString(3, thresholdmodel.getStarttimestr());
            st.setString(4, thresholdmodel.getEndtime());
            st.setString(5, thresholdmodel.getAttendanceLimittime());
            st.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now())); 
            st.setInt(7, thresholdmodel.getId()); // ID harus tipe integer sesuai DB

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed update data");
            System.err.println("Error edit user : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally edit user : " + e);
                }
            }
        }
    }

    @Override
    public void deleteAttendanceLimit(AttendanceLimitModel thresholdmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE threshold SET isdeleted = 1 WHERE idthreshold=?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, thresholdmodel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed delete data");
            System.err.println("Error ddelete employee : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally delete user : " + e);
                }
            }
        }
    }

    @Override
    public AttendanceLimitModel getById(int id) {
        String sql = "select t.idthreshold,t.iddeptthreshold,d.deptname,t.attendancename,t.starttimestr,t.endtimestr,t.thresholdtimestr from threshold as t left join departement as d on \n" +
                      "t.iddeptthreshold = d.iddept  where t.isdeleted=0 and t.idthreshold = ?";
        return getUserByQuery(sql, id);
    }

    @Override
    public List<AttendanceLimitModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AttendanceLimitModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "select t.idthreshold,t.iddeptthreshold,d.deptname,t.attendancename,t.starttimestr,t.endtimestr,t.thresholdtimestr from threshold as t left join departement as d on \n" +
                     "t.iddeptthreshold = d.iddept  where t.isdeleted=0 order by t.idthreshold desc";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                AttendanceLimitModel thresholdmodel = new AttendanceLimitModel();

                thresholdmodel.setId(rs.getInt("idthreshold"));
                thresholdmodel.setIdDept(rs.getInt("iddeptthreshold"));
                thresholdmodel.setAttendancename(rs.getString("attendancename"));
                thresholdmodel.setStarttimestr(rs.getString("starttimestr"));
                thresholdmodel.setEndtime(rs.getString("endtimestr"));
                thresholdmodel.setAttendanceLimittime(rs.getString("thresholdtimestr"));
                thresholdmodel.setDeptname(rs.getString("deptname"));

                list.add(thresholdmodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data basic threshold : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data threshold : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data threshold : " + e);
                }
            }
        }
    }

    private AttendanceLimitModel getUserByQuery(String sql, Object... params) {
        AttendanceLimitModel thresholdmodel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                thresholdmodel = mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return thresholdmodel;
    }

    private AttendanceLimitModel mapUser(ResultSet rs) throws SQLException {
        AttendanceLimitModel bs = new AttendanceLimitModel();
        bs.setId(rs.getInt("idthreshold"));
        bs.setIdDept(rs.getInt("iddeptthreshold"));
        bs.setAttendancename(rs.getString("attendancename"));
        bs.setStarttimestr(rs.getString("starttimestr"));
        bs.setEndtime(rs.getString("endtimestr"));
        bs.setAttendanceLimittime(rs.getString("thresholdtimestr"));
        bs.setDeptname(rs.getString("deptname"));

        return bs;
    }
}
