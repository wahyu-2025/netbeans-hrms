/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;


import java.sql.Connection;
import config.Koneksi;
import DAO.SalaryMasterDataDAO;
import tableModel.SalaryMasterDataModel;
import model.SalaryMasterData;
import service.SalaryMasterDatas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.*;


class DoubleHitSalary {
    private int id;
    private String name;
    
    public DoubleHitSalary(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
    return id;
    }
     @Override
    public String toString() {
        return name;
    }
    
}

/**
 *
 * @author myama
 */
public class FormSalary extends javax.swing.JPanel {
 
     private SalaryMasterDatas SalaryMasterDatas = new SalaryMasterDataDAO();
    private SalaryMasterDataModel salaryMasterDataModel = new SalaryMasterDataModel();
    private Connection conn = new Koneksi().koneksi();
    
    public FormSalary() {
        initComponents();
        
        namakaryawan.removeAllItems();
        
           String sql = "SELECT idemployee, employeename FROM employee where isdeleted = 0";
           
            try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idemployee");
                String name = rs.getString("employeename");
                namakaryawan.addItem(new DoubleHitSalary(id, name).toString());
            }
            
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        tblBasicSalary.setModel(salaryMasterDataModel);
        loadData();
    }
    
      private void dataTabel() {
        dataUser.setVisible(false);
        addUser.setVisible(true);

        int row = tblBasicSalary.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data dari tabel terlebih dahulu.");
            return;
        }

        jLabel2.setText("Perbarui Data Gaji Pokok");

        int userId = (int) tblBasicSalary.getModel().getValueAt(row, 0);
        SalaryMasterData SalaryMasterData = SalaryMasterDatas.getById(userId);

        if (SalaryMasterData == null) {
            JOptionPane.showMessageDialog(null, "Data user tidak ditemukan.");
            return;
        }

        // Set nilai ke input form
        txtGajiPokok.setText(String.valueOf(SalaryMasterData.getSalary()));
        namakaryawan.setSelectedItem(SalaryMasterData.getEmployeeName());

        active();
        btnSimpan.setText("Perbarui");
        btnBatalTambah.setVisible(true);
    }
      
       private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {                                          
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(addUser);
        mainPanel.repaint();
        mainPanel.revalidate();

        btnSimpan.setText("Simpan");
        if (btnTambah.getText().equals("Ubah")) {
            dataTabel();
        }
    }    
       
         private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        deleteDataSalary();
    }    
         private void txtGajiPokokActionPerformed(java.awt.event.ActionEvent evt) {
    
}

private void namakaryawanActionPerformed(java.awt.event.ActionEvent evt) {
    
}
           private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showPanel();
        loadData();
    }   
            private void btnBatalTambahActionPerformed(java.awt.event.ActionEvent evt) {                                               
        showPanel();
        loadData();
        resetForm();
    }       
          private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        switch (btnSimpan.getText()) {
            case "Tambah" ->
                btnSimpan.setText("Simpan");
            case "Simpan" ->
                createDataSalary();
            case "Perbarui" ->
                updateDataSalary();
            default -> {
            }
        }
    } 
          private void tblBasicSalaryMouseClicked(java.awt.event.MouseEvent evt) {                                            
        if (btnTambah.getText().equals("Tambah")) {
            btnTambah.setText("Ubah");
        }

        btnHapus.setVisible(true);
        btnBatal.setVisible(true);
    } 
    
        private void active() {
        txtGajiPokok.setEnabled(true);
        namakaryawan.setEnabled(true);
    }
        private void loadData() {
        btnHapus.setVisible(false);
        btnBatal.setVisible(false);
        List<SalaryMasterData> list = SalaryMasterDatas.getData();
        salaryMasterDataModel.setData(list);

        tblBasicSalary.getColumnModel().getColumn(0).setMinWidth(0);
        tblBasicSalary.getColumnModel().getColumn(0).setMaxWidth(0);
        tblBasicSalary.getColumnModel().getColumn(0).setWidth(0);
    }
     private void createDataSalary() {
        if (validasiInput() == true) {
            String gajipokokStr = txtGajiPokok.getText();
            int gajipokok = Integer.parseInt(gajipokokStr);
            String getemployeename = namakaryawan.getSelectedItem().toString();
            String getidemployee = "SELECT idemployee FROM employee WHERE employeename=? AND isdeleted=0";
            String checkdata = "SELECT idemployeebasicsalary FROM basicsalary WHERE idemployeebasicsalary=? AND isdeleted=0";
            try{
                
                PreparedStatement psidemployee = conn.prepareStatement(getidemployee);
                psidemployee.setString(1, getemployeename);
                ResultSet rsidemployee = psidemployee.executeQuery();

                Integer idemployee = null;
                if (rsidemployee.next()) {
                    idemployee = rsidemployee.getInt("idemployee");
                    PreparedStatement checkdataemp = conn.prepareStatement(checkdata);
                    checkdataemp.setInt(1, idemployee);
                    ResultSet rscheckdata = checkdataemp.executeQuery();
                    if (rscheckdata.next()) {
                        JOptionPane.showMessageDialog(null, "Gaji Pokok Untuk Karyawan "+getemployeename+" sudah terdaftar");
                        return; 
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Departemen tidak ditemukan di database!");
                    return;
                }
            
                SalaryMasterData SalaryMasterData = new SalaryMasterData();
                SalaryMasterData.setSalary(gajipokok); // Pastikan tipe datanya int
                SalaryMasterData.setIdEmployee(idemployee);

                SalaryMasterDatas.addSalary(SalaryMasterData);
                salaryMasterDataModel.addSalary(SalaryMasterData);
                loadData();
                resetForm();
                showPanel();
                btnSimpan.setText("Tambah");
                
            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Terjadi kesalahan saat memproses data batas kehadiran:\n" + e.getMessage());
            }
        }
    }
     
     private void updateDataSalary() {
        int row = tblBasicSalary.getSelectedRow();
        if (row != -1) {
            int basicsalaryid = (int) tblBasicSalary.getModel().getValueAt(row, 0);
            SalaryMasterData um = salaryMasterDataModel.getData(tblBasicSalary.convertRowIndexToModel(row));

            SalaryMasterData getId = SalaryMasterDatas.getById(basicsalaryid);

            if (validasiInput() == true) {
           
                String gajipokokStr = txtGajiPokok.getText();
                int gajipokok = Integer.parseInt(gajipokokStr);
                String getemployeename = namakaryawan.getSelectedItem().toString();
                String getidemployee = "SELECT idemployee FROM employee WHERE employeename=? AND isdeleted=0";
                String checkdata = "SELECT idemployeebasicsalary,idsalary FROM basicsalary WHERE idemployeebasicsalary=? AND isdeleted=0";
                 try{
                
                    PreparedStatement psidemployee = conn.prepareStatement(getidemployee);
                    psidemployee.setString(1, getemployeename);
                    ResultSet rsidemployee = psidemployee.executeQuery();

                    Integer idemployee = null;
                    if (rsidemployee.next()) {
                        idemployee = rsidemployee.getInt("idemployee");
                         PreparedStatement checkdataemp = conn.prepareStatement(checkdata);
                        checkdataemp.setInt(1, idemployee);
                        ResultSet rscheckdata = checkdataemp.executeQuery();
                        if (rscheckdata.next() && basicsalaryid != rscheckdata.getInt("idsalary")) {
                            JOptionPane.showMessageDialog(null, "Gaji Pokok Untuk Karyawan "+getemployeename+" sudah terdaftar");
                            return; 
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Departemen tidak ditemukan di database!");
                        return;
                    }
                    
                     SalaryMasterData salaryMasterData = new SalaryMasterData();
                    salaryMasterData.setId(basicsalaryid);
                     salaryMasterData.setIdEmployee(idemployee);
                    salaryMasterData.setSalary(gajipokok);

                    SalaryMasterDatas.editSalary(salaryMasterData);
                    salaryMasterDataModel.editSalary(row, salaryMasterData);

                    loadData();
                    resetForm();
                    showPanel();
                    
                }catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Terjadi kesalahan saat memproses data batas kehadiran:\n" + e.getMessage());
                }
            }
        }
    }
     
      private void deleteDataSalary() {
        int index = tblBasicSalary.getSelectedRow();
        if (index != -1) {
            int basicsalaryid = (int) tblBasicSalary.getModel().getValueAt(index, 0);
            SalaryMasterData getemployeesalary = SalaryMasterDatas.getById(basicsalaryid);

            SalaryMasterData basicsalarymodel = getemployeesalary;

            if (JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                SalaryMasterDatas.deleteSalary(basicsalarymodel);
                salaryMasterDataModel.deleteSalary(index);
                loadData();
                resetForm();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih dahulu record yang akan dihapus");
        }
    }
      
         private boolean validasiInput() {
        boolean valid = false;
        if (txtGajiPokok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "gaji pokok tidak boleh kosong");
        } else {
            valid = true;
        }

        return valid;
    }
        
       private void showPanel() {
        mainPanel.removeAll();
        mainPanel.add(new FormSalary());
        mainPanel.repaint();
        mainPanel.revalidate();
    }
     
     private void resetForm() {
        btnTambah.requestFocus();
        btnTambah.setText("Tambah");
        txtGajiPokok.setText("");
        namakaryawan.setSelectedIndex(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        dataUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBasicSalary = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        addUser = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatalTambah = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtGajiPokok = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        namakaryawan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(899, 620));
        setLayout(new java.awt.CardLayout());

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        dataUser.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblBasicSalary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBasicSalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBasicSalaryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBasicSalary);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("Data Gaji Karyawan");

        btnTambah.setText("Tambah");
        btnTambah.setPreferredSize(new java.awt.Dimension(85, 30));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.setPreferredSize(new java.awt.Dimension(85, 30));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.setPreferredSize(new java.awt.Dimension(85, 30));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataUserLayout = new javax.swing.GroupLayout(dataUser);
        dataUser.setLayout(dataUserLayout);
        dataUserLayout.setHorizontalGroup(
            dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(dataUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataUserLayout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataUserLayout.setVerticalGroup(
            dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(dataUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(dataUser, "card2");

        addUser.setBackground(new java.awt.Color(255, 255, 255));
        addUser.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel2.setText("Tambah Data Gaji Pokok Karyawan");
        addUser.add(jLabel2);
        jLabel2.setBounds(6, 6, 887, 36);

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSimpan.setText("Tambah");
        btnSimpan.setPreferredSize(new java.awt.Dimension(74, 30));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        addUser.add(btnSimpan);
        btnSimpan.setBounds(708, 73, 85, 30);

        btnBatalTambah.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBatalTambah.setText("Batal");
        btnBatalTambah.setPreferredSize(new java.awt.Dimension(85, 30));
        btnBatalTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalTambahActionPerformed(evt);
            }
        });
        addUser.add(btnBatalTambah);
        btnBatalTambah.setBounds(811, 73, 85, 30);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Gaji Pokok");

        txtGajiPokok.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGajiPokok.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(76, 122, 227)));
        txtGajiPokok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGajiPokokActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Nama Karyawan");

        namakaryawan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        namakaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namakaryawanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Rp");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtGajiPokok))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(735, 735, 735))
                        .addComponent(namakaryawan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGajiPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(namakaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(636, Short.MAX_VALUE))
        );

        addUser.add(jPanel1);
        jPanel1.setBounds(0, 121, 902, 830);

        mainPanel.add(addUser, "card2");

        add(mainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
private javax.swing.JPanel addUser;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBatalTambah;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JPanel dataUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> namakaryawan;
    private javax.swing.JTable tblBasicSalary;
    private javax.swing.JTextField txtGajiPokok;
}

