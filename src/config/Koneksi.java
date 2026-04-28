package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USEr
 */
public class Koneksi {

    public static Connection koneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_salary_employee", "root", "");
            System.out.println("Koneksi" + koneksi);
            return koneksi;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
            throw new RuntimeException("Koneksi database gagal", e);
            
        }
    }
}
