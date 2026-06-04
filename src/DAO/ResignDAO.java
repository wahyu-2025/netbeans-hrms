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
import model.ResignModel;
import service.ResignService;

/**
 *
 * @author wayan
 */
public class ResignDAO implements ResignService {

    private final Connection conn;

    public ResignDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addResign(ResignModel resignmodel) {
        PreparedStatement st = null;
        PreparedStatement stUpdate = null;
        String sqlInsert = "INSERT INTO resign(employeename, notes) VALUES (?, ?)";
        String sqlUpdate = "UPDATE employee SET isdeleted = 1 WHERE employeename = ?";

        try {
            // Insert resign data
            st = conn.prepareStatement(sqlInsert);
            st.setString(1, resignmodel.getEmployeename());
            st.setString(2, resignmodel.getNotes());
            st.executeUpdate();

            // Update employee isdeleted flag
            stUpdate = conn.prepareStatement(sqlUpdate);
            stUpdate.setString(1, resignmodel.getEmployeename());
            stUpdate.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed add data.");
            System.err.println("Error add user: " + e);
        } finally {
            try {
                if (st != null) st.close();
                if (stUpdate != null) stUpdate.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e);
            }
        }
    }

    @Override
    public void editResign(ResignModel resignmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE resign SET employeename=?, notes=?,updated_at=? WHERE idresign=?";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, resignmodel.getEmployeename());
            st.setString(2, resignmodel.getNotes());
            st.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); 
            st.setInt(4, resignmodel.getId()); // ID harus tipe integer sesuai DB

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed update data.");
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
    public void deleteResign(ResignModel resignmodel) {
        PreparedStatement st = null;
        String sql = "UPDATE resign SET isdeleted = 1 WHERE idresign=? order by idresign desc";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, resignmodel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed delete data.");
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
    public ResignModel getById(int id) {
        String sql = "select * from resign where isdeleted=0 and idresign = ?";
        return getUserByQuery(sql, id);
    }

    @Override
    public List<ResignModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ResignModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "select * from resign where isdeleted=0";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ResignModel resignmodel = new ResignModel();

                resignmodel.setId(rs.getInt("idresign"));
                resignmodel.setEmployeename(rs.getString("employeename"));
                resignmodel.setNotes(rs.getString("notes"));
                list.add(resignmodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data basic salary : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data basic salary : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data basic salary : " + e);
                }
            }
        }
    }


    private ResignModel getUserByQuery(String sql, Object... params) {
        ResignModel resignmodel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                resignmodel = mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return resignmodel;
    }

    private ResignModel mapUser(ResultSet rs) throws SQLException {
        ResignModel bs = new ResignModel();
        bs.setId(rs.getInt("idresign"));
        bs.setEmployeename(rs.getString("employeename"));
        bs.setNotes(rs.getString("notes"));

        return bs;
    }
}
