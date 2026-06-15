/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author USer
 */
public class AttendanceLimitModel {
    
    private Integer id;
    private Integer iddeptattendancelimit;
    private String attendancename;
    private String starttimestr;
    private String endtimestr;
    private String attendancelimittimestr;
    private String deptname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdDept() {
        return iddeptattendancelimit;
    }

    public void setIdDept(Integer iddeptattendancelimit) {
        this.iddeptattendancelimit = iddeptattendancelimit;
    }

    public String getAttendancename() {
        return attendancename;
    }

    public void setAttendancename(String attendancename) {
        this.attendancename = attendancename;
    }
    
    public String getStarttimestr() {
        return starttimestr;
    }

    public void setStarttimestr(String starttimestr) {
        this.starttimestr = starttimestr;  
    }
    
    public String getEndtime() {
        return endtimestr;
    }

    public void setEndtime(String endtimestr) {
        this.endtimestr = endtimestr;
    }
    
    public String getAttendanceLimittime() {
        return attendancelimittimestr;
    }

    public void setAttendanceLimittime(String attendancelimittimestr) {
        this.attendancelimittimestr = attendancelimittimestr;
    }
    
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}
