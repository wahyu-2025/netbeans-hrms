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
import java.util.List;
import java.sql.*;
/**
 *
 * @author myama
 */
public class FormNewSalary extends javax.swing.JPanel {
    
    private SalaryMasterDatas salaryMasterDatas = new SalaryMasterDataDAO();
    private SalaryMasterDataModel salaryMasterDataModel = new SalaryMasterDataModel();
    private Connection conn = new Koneksi().koneksi();

    /**
     * Creates new form FormNewSalary
     */
    public FormNewSalary() {
        initComponents();
        tblSalary.setModel(salaryMasterDataModel);
        loadData();
    }
    
    private void loadData() {
        btnEdit.setVisible(false);
        btnDelete.setVisible(false);
        btnCancel.setVisible(false);
 
        List<SalaryMasterData> list = salaryMasterDatas.getData();
        salaryMasterDataModel.setData(list);
 
        // Sembunyikan kolom ID
        tblSalary.getColumnModel().getColumn(0).setMinWidth(0);
        tblSalary.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSalary.getColumnModel().getColumn(0).setWidth(0);
    }
    
        private void showDialog(SalaryMasterData existing) {
        JDialog dialog = new JDialog();
        dialog.setTitle(existing == null ? "Add Gaji" : "Edit Gaji");
        dialog.setModal(true);
        dialog.setSize(400, 280);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(null);
 
        JLabel lblNama = new JLabel("Nama Karyawan");
        lblNama.setBounds(20, 20, 150, 25);
        dialog.add(lblNama);
 
        JComboBox<String> cbNama = new JComboBox<>();
        cbNama.setBounds(20, 50, 340, 30);
        dialog.add(cbNama);
 
        JLabel lblGaji = new JLabel("Gaji Pokok");
        lblGaji.setBounds(20, 100, 150, 25);
        dialog.add(lblGaji);
 
        JTextField txtGaji = new JTextField();
        txtGaji.setBounds(20, 130, 340, 30);
        dialog.add(txtGaji);
 
        JButton btnSave = new JButton(existing == null ? "Add" : "Update");
        btnSave.setBounds(220, 190, 70, 30);
        dialog.add(btnSave);
 
        JButton btnCancelDialog = new JButton("Cancel");
        btnCancelDialog.setBounds(300, 190, 80, 30);
        dialog.add(btnCancelDialog);
 
        // Isi ComboBox karyawan
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT employeename FROM employee WHERE isdeleted=0");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cbNama.addItem(rs.getString("employeename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        // Isi form jika edit
        if (existing != null) {
            txtGaji.setText(String.valueOf(existing.getSalary()));
            cbNama.setSelectedItem(existing.getEmployeeName());
        }
 
        btnSave.addActionListener(e -> {
            if (txtGaji.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Gaji pokok tidak boleh kosong.");
                return;
            }
            try {
                int gajiPokok = Integer.parseInt(txtGaji.getText().trim());
                String employeeName = cbNama.getSelectedItem().toString();
                String getIdSql = "SELECT idemployee FROM employee WHERE employeename=? AND isdeleted=0";
 
                PreparedStatement psId = conn.prepareStatement(getIdSql);
                psId.setString(1, employeeName);
                ResultSet rsId = psId.executeQuery();
 
                if (!rsId.next()) {
                    JOptionPane.showMessageDialog(null, "Karyawan tidak ditemukan di database!");
                    return;
                }
 
                int idEmployee = rsId.getInt("idemployee");
 
                if (existing == null) {
                    // CREATE
                    PreparedStatement psCheck = conn.prepareStatement(
                        "SELECT idemployeebasicsalary FROM basicsalary WHERE idemployeebasicsalary=? AND isdeleted=0");
                    psCheck.setInt(1, idEmployee);
                    if (psCheck.executeQuery().next()) {
                        JOptionPane.showMessageDialog(null, "Gaji Pokok untuk karyawan " + employeeName + " sudah terdaftar.");
                        return;
                    }
                    SalaryMasterData data = new SalaryMasterData();
                    data.setIdEmployee(idEmployee);
                    data.setSalary(gajiPokok);
                    salaryMasterDatas.addSalary(data);
                } else {
                    // UPDATE
                    PreparedStatement psCheck = conn.prepareStatement(
                        "SELECT idemployeebasicsalary, idsalary FROM basicsalary WHERE idemployeebasicsalary=? AND isdeleted=0");
                    psCheck.setInt(1, idEmployee);
                    ResultSet rsCheck = psCheck.executeQuery();
                    if (rsCheck.next() && existing.getId() != rsCheck.getInt("idsalary")) {
                        JOptionPane.showMessageDialog(null, "Gaji Pokok untuk karyawan " + employeeName + " sudah terdaftar.");
                        return;
                    }
                    SalaryMasterData data = new SalaryMasterData();
                    data.setId(existing.getId());
                    data.setIdEmployee(idEmployee);
                    data.setSalary(gajiPokok);
                    salaryMasterDatas.editSalary(data);
                }
 
                loadData();
                dialog.dispose();
 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Gaji pokok harus berupa angka.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan:\n" + ex.getMessage());
            }
        });
 
        btnCancelDialog.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header_panel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSalary = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        header_panel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel5.setText("Salary");

        tblSalary.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSalary.setPreferredSize(null);
        tblSalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSalaryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSalary);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/plus.png"))); // NOI18N
        btnAdd.setText("Add Salary");
        btnAdd.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnAdd.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                btnAddComponentHidden(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/plus.png"))); // NOI18N
        btnEdit.setText("Edit Salary");
        btnEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout header_panel1Layout = new javax.swing.GroupLayout(header_panel1);
        header_panel1.setLayout(header_panel1Layout);
        header_panel1Layout.setHorizontalGroup(
            header_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_panel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(header_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(header_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(header_panel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(62, 62, 62))
                    .addGroup(header_panel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        header_panel1Layout.setVerticalGroup(
            header_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_panel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(header_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(header_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(header_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(header_panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSalaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSalaryMouseClicked
              int row = tblSalary.getSelectedRow();
        if (row != -1) {
            btnAdd.setVisible(false);
            btnEdit.setVisible(true);
            btnDelete.setVisible(true);
            btnCancel.setVisible(true);
        }
    }//GEN-LAST:event_tblSalaryMouseClicked

    private void btnAddComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_btnAddComponentHidden

    }//GEN-LAST:event_btnAddComponentHidden

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
         showDialog(null);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
           int row = tblSalary.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data dari tabel terlebih dahulu.");
            return;
        }
        int id = (int) tblSalary.getModel().getValueAt(row, 0);
        SalaryMasterData data = salaryMasterDatas.getById(id);
        showDialog(data);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       int index = tblSalary.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Pilih dahulu record yang akan dihapus.");
            return;
        }
 
        int id = (int) tblSalary.getModel().getValueAt(index, 0);
        SalaryMasterData data = salaryMasterDatas.getById(id);
 
        int confirm = JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            salaryMasterDatas.deleteSalary(data);
            salaryMasterDataModel.deleteSalary(index);
            loadData();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
       tblSalary.clearSelection();
        loadData();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel header_panel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSalary;
    // End of variables declaration//GEN-END:variables
}
