/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
import service.LoginService;
import model.LoginModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import hrms.MainMenu;
import view.Login;

/**
 *
 * @author USer
 */
public class LoginDAO implements LoginService {

    private final Connection conn;

    public LoginDAO() {
        conn = Koneksi.koneksi();
    }

    @Override
    public void prosesLogin(LoginModel loginModel) {
        System.out.println("MASUK SINI");
        PreparedStatement st = null;
        ResultSet rs = null;
        String name = null;

        String sql = "SELECT * FROM roles WHERE name = '" + loginModel.getUsername() + "' "
                + "AND password='" + loginModel.getPassword() + "'" ;

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
//                password = rs.getString("password");
                
                System.out.println("TRY");

                MainMenu menu = new MainMenu(name);
                menu.setVisible(true);
                menu.revalidate();

                Login.tutup = true;
                
                
            } else {
                JOptionPane.showMessageDialog(null, "Username atau Password salah", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                Login.tutup = false;
            }

        } catch (SQLException e) {
            System.out.println("Catch" + e.getMessage());
        }
    }
}
