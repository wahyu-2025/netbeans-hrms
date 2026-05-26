/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import DAO.AttendanceDAO;
import config.Koneksi;
import java.sql.*;
import java.util.List; 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.AttendanceModel;
import service.AttendanceService;
import tableModel.AttendanceTableModel;

/**
 *
 * @author myama
 */
public class FormAttendanceLimit extends javax.swing.JPanel {
    
    private AttendanceService attendanceservice = new AttendanceDAO();
    private Connection conn = new Koneksi().koneksi();
    private AttendanceTableModel tableModel = new AttendanceTableModel();
    
    class ComboItem {
    private int id;
    private String name;

    public ComboItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name; // akan muncul di combobox
    }
   }
            

    /**
     * Creates new form FormAttendanceLimit
     */
    public FormAttendanceLimit() {
        initComponents();
        tbleAtt.setModel(tableModel);
        loadData();
    }
    
      private void loadData() {
        btnCancel.setVisible(false);
        List<AttendanceModel> list = attendanceservice.getData();
        tableModel.setData(list);

        tbleAtt.getColumnModel().getColumn(0).setMinWidth(0);
        tbleAtt.getColumnModel().getColumn(0).setMaxWidth(0);
        tbleAtt.getColumnModel().getColumn(0).setWidth(0);
    }
      
       private void showAddDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Tambah Data Absensi", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        // Label & ComboBox Nama Karyawan
        JLabel lblNama = new JLabel("Nama Karyawan");
        lblNama.setBounds(20, 20, 150, 25);
        dialog.add(lblNama);

        JComboBox<String> cbNama = new JComboBox<>();
        cbNama.setBounds(20, 50, 340, 30);
        dialog.add(cbNama);

        // Isi combobox dari database
        try {
            String sql = "SELECT idemployee, employeename FROM employee WHERE isdeleted = 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cbNama.addItem(new ComboItem(rs.getInt("idemployee"), rs.getString("employeename")).toString());
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label & ComboBox IN/OUT
        JLabel lblInOut = new JLabel("Pilih Absensi");
        lblInOut.setBounds(20, 100, 150, 25);
        dialog.add(lblInOut);

        JComboBox<String> cbInOut = new JComboBox<>(new String[]{"IN", "OUT"});
        cbInOut.setBounds(20, 130, 120, 30);
        dialog.add(cbInOut);

        // Tombol Simpan & Batal
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(200, 175, 80, 30);
        dialog.add(btnSimpan);

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(290, 175, 70, 30);
        dialog.add(btnBatal);

        // Action Simpan
        btnSimpan.addActionListener(e -> {
            String nama  = (String) cbNama.getSelectedItem();
            String inout = (String) cbInOut.getSelectedItem();
            saveData(nama, inout, dialog);
        });

        // Action Batal
        btnBatal.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
       
       private void saveData(String nama, String inout, JDialog dialog) {
        try {
        // ========== 1. CEK DUPLIKASI ==========
        String cek = "SELECT * FROM attendance WHERE isdeleted = 0 AND DATE(created_at) = ? AND employeename = ? AND attendancename = ?";
        PreparedStatement psCek = conn.prepareStatement(cek);
        psCek.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
        psCek.setString(2, nama);
        psCek.setString(3, inout);
        ResultSet rsCek = psCek.executeQuery();

        if (rsCek.next()) {
            JOptionPane.showMessageDialog(dialog, "Data absensi untuk " + nama + " sudah ada hari ini.");
            rsCek.close(); psCek.close();
            return;
        }
        rsCek.close(); psCek.close();

        // ========== 2. AMBIL DATA KARYAWAN ==========
        String nik = "";
        int idemployee = 0;
        int iddept = 0;
        Integer islates = 0;
        Integer lateperdays = 0;

        String sqlEmp = "SELECT idemployee, nik, iddeptemployee FROM employee WHERE isdeleted = 0 AND employeename = ?";
        PreparedStatement psEmp = conn.prepareStatement(sqlEmp);
        psEmp.setString(1, nama);
        ResultSet rsEmp = psEmp.executeQuery();

        if (!rsEmp.next()) {
            JOptionPane.showMessageDialog(dialog, "Data karyawan tidak ditemukan.");
            return;
        }

        idemployee = rsEmp.getInt("idemployee");
        nik        = rsEmp.getString("nik");
        iddept     = rsEmp.getInt("iddeptemployee");
        rsEmp.close(); psEmp.close();

        // ========== 3. AMBIL THRESHOLD ==========
        String sqlThreshold = "SELECT * FROM threshold WHERE iddeptthreshold = ? AND attendancename = ? AND isdeleted = 0";
        PreparedStatement psThreshold = conn.prepareStatement(sqlThreshold);
        psThreshold.setInt(1, iddept);
        psThreshold.setString(2, inout);
        ResultSet rsThreshold = psThreshold.executeQuery();

        if (!rsThreshold.next()) {
            JOptionPane.showMessageDialog(dialog, "Threshold untuk karyawan ini tidak ditemukan.");
            return;
        }

        // ========== 4. VALIDASI WAKTU ==========
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("h:mm a");
        String attendancetime = java.time.LocalTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.getDefault()));
        java.time.LocalTime inputTime = java.time.LocalTime.parse(attendancetime, formatter);

        if (inout.equalsIgnoreCase("OUT")) {
           String endtimestr = rsThreshold.getString("endtimestr")
             .toUpperCase()
             .replaceAll("(?<=\\d)(AM|PM)$", " $1");
            java.time.LocalTime endTime = java.time.LocalTime.parse(endtimestr, formatter);


            if (inputTime.isBefore(endTime)) {
                JOptionPane.showMessageDialog(dialog,
                    "Absensi keluar tidak boleh lebih awal dari threshold (" + endtimestr + ")");
                return;
            }

        } else if (inout.equalsIgnoreCase("IN")) {
            // Validasi IN: cek telat atau melewati threshold
            String endtimestr     = rsThreshold.getString("endtimestr").toUpperCase().replaceAll("(?<=\\d)(AM|PM)$", " $1");
            String thresholdtimestr = rsThreshold.getString("thresholdtimestr").toUpperCase().replaceAll("(?<=\\d)(AM|PM)$", " $1");

            java.time.LocalTime endTime       = java.time.LocalTime.parse(endtimestr, formatter);
            java.time.LocalTime thresholdTime = java.time.LocalTime.parse(thresholdtimestr, formatter);

            if (inputTime.isAfter(thresholdTime)) {
                // Lewat threshold → dianggap tidak masuk
                JOptionPane.showMessageDialog(dialog, "Anda dianggap tidak masuk karena melebihi waktu threshold.");
                return;

            } else if (inputTime.isAfter(endTime) && inputTime.isBefore(thresholdTime)) {
                // Telat
                islates     = 1;
                lateperdays = (int) java.time.Duration.between(endTime, inputTime).toMinutes();

            } else {
                // Tepat waktu
                islates     = 0;
                lateperdays = 0;
            }
        }

        rsThreshold.close(); psThreshold.close();

        // ========== 5. SIMPAN KE DATABASE ==========
        AttendanceModel model = new AttendanceModel();
        model.setIdEmployee(idemployee);
        model.setNikEmployee(nik);
        model.setEmployeeName(nama);
        model.setAttendanceName(inout);
        model.setAttendanceTime(attendancetime);
        model.setIsLate(islates);
        model.setLatePerDays(lateperdays);

       boolean berhasil = attendanceservice.addAttendance(model);

if (berhasil) {
    JOptionPane.showMessageDialog(dialog, "Data berhasil disimpan!");
    dialog.dispose();
    loadData(); // refresh dari DB
} else {
    JOptionPane.showMessageDialog(dialog, "Gagal menyimpan data ke database!");
}

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(dialog, "Terjadi kesalahan: " + e.getMessage());
    }
}
    
    
    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataClasses = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbleAtt = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        dataClasses.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tbleAtt.setModel(new javax.swing.table.DefaultTableModel(
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
        tbleAtt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbleAttMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbleAtt);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("Attendance Limit");

        btnAdd.setText("Tambah");
        btnAdd.setPreferredSize(new java.awt.Dimension(85, 30));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(85, 30));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataClassesLayout = new javax.swing.GroupLayout(dataClasses);
        dataClasses.setLayout(dataClassesLayout);
        dataClassesLayout.setHorizontalGroup(
            dataClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataClassesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(dataClassesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataClassesLayout.setVerticalGroup(
            dataClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataClassesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(dataClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(dataClasses, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tbleAttMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbleAttMouseClicked
        btnCancel.setVisible(true);
        btnAdd.setVisible(false);
    }//GEN-LAST:event_tbleAttMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
    showAddDialog();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
          btnCancel.setVisible(false); // ← sembunyikan tombol cancel
    btnAdd.setVisible(true);     // ← tampilkan lagi tombol add
    loadData();        
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JPanel dataClasses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbleAtt;
    // End of variables declaration//GEN-END:variables
}
