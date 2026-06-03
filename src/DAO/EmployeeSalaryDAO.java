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
import model.EmployeeSalaryModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.view.JasperViewer;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
import service.EmployeeSalaryService;

/**
 *
 * @author wayan
 */
public class EmployeeSalaryDAO implements EmployeeSalaryService {

    private final Connection conn;

    public EmployeeSalaryDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void addEmployeeSalary(EmployeeSalaryModel employeesalary) {
        PreparedStatement st = null;
        String sql = "INSERT INTO salarypermonth(basicsalary,overtimebonus,cut_late,totalsalarythismonth,nik,idemployee) VALUES (?,?,?,?,?,?)";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, employeesalary.getBasicSalary());
            st.setInt(2, employeesalary.getOvertimeBonus());
            st.setInt(3, employeesalary.getCutLate());
            st.setInt(4, employeesalary.getSalaryThisMonth());
            st.setString(5, employeesalary.getEmployeeNik());
            st.setInt(6, employeesalary.getIdEmployee());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tambah data gagal");
            System.err.println("Error add user : " + e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.err.println("Error finally add employee salary : " + e);
                }
            }
        }
    }

    @Override
    public void deleteEmployeeSalary(EmployeeSalaryModel employeesalarymodel) {
        PreparedStatement st = null;
        String sql = "UPDATE salarypermonth SET isdeleted = 1 WHERE idsalarypermonth=?";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, employeesalarymodel.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hapus data gagal");
            System.err.println("Error delete overtime : " + e);
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
    public EmployeeSalaryModel getById(int id) {
        String sql = "select s.idsalarypermonth ,s.basicsalary ,s.overtimebonus,s.cut_late,e.nik, e.employeename, l.levelname, d.deptname,\n" +
                    " s.totalsalarythismonth,\n" +
                    " MONTH(s.created_at) as month_number,\n" +
                    " YEAR(s.created_at) AS year\n" +
                    " from salarypermonth s  left join employee as e on s.idemployee = e.idemployee \n" +
                    " left join departement as d on e.iddeptemployee=d.iddept \n" +
                    " left join level as l on e.idlevelemployee = l.idlevel where s.isdeleted=0 and s.idsalarypermonth = ?";
        return getUserByQuery(sql, id);
    }

    @Override
    public List<EmployeeSalaryModel> getDataById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EmployeeSalaryModel> getData() {
        PreparedStatement st = null;
        List list = new ArrayList();
        ResultSet rs = null;
        String sql = "select s.idsalarypermonth ,s.basicsalary ,s.overtimebonus,s.cut_late,e.nik, e.employeename, l.levelname, d.deptname, \n" +
                    " s.totalsalarythismonth,\n" +
                    " MONTH(s.created_at) as month_number,\n" +
                    " YEAR(s.created_at) AS year\n" +
                    " from salarypermonth s  left join employee as e on s.idemployee = e.idemployee \n" +
                    " left join departement as d on e.iddeptemployee=d.iddept \n" +
                    " left join level as l on e.idlevelemployee = l.idlevel WHERE s.isdeleted=0 order by s.idsalarypermonth desc";

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                EmployeeSalaryModel employeesalarymodel = new EmployeeSalaryModel();

                employeesalarymodel.setId(rs.getInt("idsalarypermonth"));
                employeesalarymodel.setEmployeeNik(rs.getString("nik"));
                employeesalarymodel.setEmployeeName(rs.getString("employeename"));
                employeesalarymodel.setDeptName(rs.getString("deptname"));
                employeesalarymodel.setLevelName(rs.getString("levelname"));
                employeesalarymodel.setBasicSalary(rs.getInt("basicsalary"));
                employeesalarymodel.setOvertimeBonus(rs.getInt("overtimebonus"));
                employeesalarymodel.setCutLate(rs.getInt("cut_late"));
                employeesalarymodel.setSalaryThisMonth(rs.getInt("totalsalarythismonth"));
                employeesalarymodel.setMonth(rs.getString("month_number"));
                employeesalarymodel.setYear(rs.getString("year"));
                list.add(employeesalarymodel);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error get data employee salary per month : " + e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("Error close st get data employee salary per month : " + e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error close rs get data employee salary per month : " + e);
                }
            }
        }
    }

    private EmployeeSalaryModel getUserByQuery(String sql, Object... params) {
        EmployeeSalaryModel overtimemodel = null;
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

    private EmployeeSalaryModel mapUser(ResultSet rs) throws SQLException {
        EmployeeSalaryModel employeesalarymodel = new EmployeeSalaryModel();
        
         employeesalarymodel.setId(rs.getInt("idsalarypermonth"));
         employeesalarymodel.setEmployeeNik(rs.getString("nik"));
         employeesalarymodel.setEmployeeName(rs.getString("employeename"));
         employeesalarymodel.setDeptName(rs.getString("deptname"));
         employeesalarymodel.setLevelName(rs.getString("levelname"));
         employeesalarymodel.setBasicSalary(rs.getInt("basicsalary"));
         employeesalarymodel.setOvertimeBonus(rs.getInt("overtimebonus"));
         employeesalarymodel.setCutLate(rs.getInt("cut_late"));
         employeesalarymodel.setSalaryThisMonth(rs.getInt("totalsalarythismonth"));
         employeesalarymodel.setMonth(rs.getString("month_number"));
         employeesalarymodel.setYear(rs.getString("year"));
                
        return employeesalarymodel;
    }
    
    @Override
    public void exportEmployeeSalaryToExcel() {
//           try {
//               String reportPath = "src/dao/Salary.jasper";
//
//               HashMap<String, Object> parameters = new HashMap<>();
//               JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
//               JasperViewer viewer = new JasperViewer(print, false);
//               viewer.setVisible(true);
//           } catch (Exception e) {
//               e.printStackTrace();
//           }
throw new UnsupportedOperationException("Not Supported Yet.");
    }
}
