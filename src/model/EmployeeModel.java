/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author USer
 */
public class EmployeeModel {

    private Integer id;
    private String nik;
    private String password;
    private String nama;
    private Integer iddept;
    private Integer idlevel;
    private String address;
    private String phonenumber;
    private String deptname;
    private String levelname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeName() {
        return nama;
    }

    public void setEmployeeName(String nama) {
        this.nama = nama;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Integer getIdDept() {
        return iddept;
    }

    public void setIdDept(Integer iddept) {
        this.iddept = iddept;
    }

    public Integer getIdLevel() {
        return idlevel;
    }

    public void setIdLevel(Integer idlevel) {
        this.idlevel = idlevel;
    }
    
     public String getDeptName() {
        return deptname;
    }

    public void setDeptName(String deptname) {
        this.deptname = deptname;
    }

    public String getLevelName() {
        return levelname;
    }

    public void setLevelName(String levelname) {
        this.levelname = levelname;
    }
}
