/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SalaryMasterData;
import service.SalaryMasterDatas;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.time.LocalDateTime;



/**
 *
 * @author myama
 */
public class SalaryMasterDataDAO implements SalaryMasterDatas {
    
       private final Connection conn;

    public SalaryMasterDataDAO() {
        conn = Koneksi.koneksi();
    }
    
     public void addSalary(SalaryMasterData salarymasterdata) {
        PreparedStatement st = null;
//        String sql = "INSERT INTO basicsalary(idemployeebasicsalary, basicsalary) VALUES (?,?)";
           String sql = "INSERT INTO basicsalary(idemployeebasicsalary, basicsalary, created_at, updated_at) VALUES (?,?, NOW(), NOW())";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, salarymasterdata.getIdEmployee());
            st.setInt(2, salarymasterdata.getSalary());
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil ditambahkan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tambah data gagal");
            System.err.println("Error add basic salary : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally add basic salary : " + e);
                }
            }
        }
    }
     
      @Override
    public void editSalary(SalaryMasterData salarymasterdata) {
        PreparedStatement st = null;
        String sql = "UPDATE basicsalary SET idemployeebasicsalary=?, basicsalary=?,updated_at=? WHERE idsalary=?";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, salarymasterdata.getIdEmployee());
            st.setInt(2, salarymasterdata.getSalary());
            st.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); 
            st.setInt(4, salarymasterdata.getId()); // ID harus tipe integer sesuai DB

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil diperbarui");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Perbarui data gagal");
            System.err.println("Error edit basic salary : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally edit basic salary : " + e);
                }
            }
        }
    }

     @Override
    public void deleteSalary(SalaryMasterData salarymasterdata) {
        PreparedStatement st = null;
        String sql = "UPDATE basicsalary SET isdeleted = 1 WHERE idsalary=?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, salarymasterdata.getId());

            st.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hapus data gagal");
            System.err.println("Error ddelete basic salary : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally hapus basic salary : " + e);
                }
            }
        }
    }
    
     @Override
    public SalaryMasterData getById(int id) {
        String sql = "select b.idsalary,b.idemployeebasicsalary,e.employeename,b.basicsalary from basicsalary as b left join employee as e on b.idemployeebasicsalary = e.idemployee "
                + "where b.isdeleted=0 and b.idsalary = ?";
        return getUserByQuery(sql, id);
    }

    @Override
    public List<SalaryMasterData> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    @Override
    public List<SalaryMasterData> getData() {
        List<SalaryMasterData> list = new ArrayList<>();
        String sql = "SELECT b.idsalary, b.idemployeebasicsalary, e.employeename, b.basicsalary " +
                     "FROM basicsalary b LEFT JOIN employee e ON b.idemployeebasicsalary = e.idemployee " +
                     "WHERE b.isdeleted = 0 ORDER BY b.idsalary DESC";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SalaryMasterData m = new SalaryMasterData();
                m.setId(rs.getInt("idsalary"));
                m.setIdEmployee(rs.getInt("idemployeebasicsalary"));
                m.setEmployeeName(rs.getString("employeename"));
                m.setSalary(rs.getInt("basicsalary"));
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error getData: " + e);
        }
        return list;
    }
    
    
      private SalaryMasterData getUserByQuery(String sql, Object... params) {
        SalaryMasterData basicsalarymodel = null;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                basicsalarymodel = mapUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error => " + e);
        }
        return basicsalarymodel;
    }
      
    
     private SalaryMasterData mapUser(ResultSet rs) throws SQLException {
        SalaryMasterData bs = new SalaryMasterData();
        bs.setId(rs.getInt("idsalary"));
        bs.setIdEmployee(rs.getInt("idemployeebasicsalary"));
        bs.setEmployeeName(rs.getString("employeename"));
        bs.setSalary(rs.getInt("basicsalary"));
        return bs;
    }
    
    
}
