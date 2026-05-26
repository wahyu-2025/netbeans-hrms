/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author myama
 */
public class AttendanceModel {
       private Integer id;
    private Integer idemployee;
    private String nikemployee;
    private Integer islate;
    private Integer totallateperdays;
    private String employeename;
    private String attendancename;
    private String attendancetime;
    private String checkintime;
    private String checkouttime;
    private String attendancedate;
    private String levelname;
    private String deptname;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
     public Integer getIdEmployee() {
        return idemployee;
    }

    public void setIdEmployee(Integer idemployee) {
        this.idemployee = idemployee;
    }
    
    public String getNikEmployee() {
        return nikemployee;
    }

    public void setNikEmployee(String nikemployee) {
        this.nikemployee = nikemployee;
    }
    
    public Integer getIsLate() {
        return islate;
    }

    public void setIsLate(Integer islate) {
        this.islate = islate;
    }
    
    public Integer getLatePerDays() {
        return totallateperdays;
    }

    public void setLatePerDays(Integer totallateperdays) {
        this.totallateperdays = totallateperdays;
    }
    
    public String getEmployeeName() {
        return employeename;
    }

    public void setEmployeeName(String employeename) {
        this.employeename = employeename;
    }
    
    public String getAttendanceName() {
        return attendancename;
    }

    public void setAttendanceName(String attendancename) {
        this.attendancename = attendancename;
    }
    
    public String getAttendanceTime() {
        return attendancetime;
    }

    public void setAttendanceTime(String attendancetime) {
        this.attendancetime = attendancetime;
    }
    
    public String getCheckin() {
        return checkintime;
    }

    public void setCheckin(String checkintime) {
        this.checkintime = checkintime;
    }
    
    public String getCheckout() {
        return checkouttime;
    }

    public void setCheckout(String checkouttime) {
        this.checkouttime = checkouttime;
    }
    
    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }
    
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    
    public String getAttendancedate() {
        return attendancedate;
    }

    public void setAttendancedate(String attendancedate) {
        this.attendancedate = attendancedate;
    }
}
