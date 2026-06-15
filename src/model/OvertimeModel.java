/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author USer
 */
public class OvertimeModel {

    private Integer id;
    private String employeename;
    private String overtimereason;
    private String starttime;
    private String endtime;
    private Integer duration;
    private String overtimedate;
    private Integer idemployee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getEmployeeName() {
        return employeename;
    }

    public void setEmployeeName(String employeename) {
        this.employeename = employeename;
    }
    
    public String getOvertimeReason() {
        return overtimereason;
    }

    public void setOvertimeReason(String overtimereason) {
        this.overtimereason = overtimereason;
    }
    
    public String getStartTime() {
        return starttime;
    }

    public void setStartTime(String starttime) {
        this.starttime = starttime;
    }
    
    public String getEndTime() {
        return endtime;
    }

    public void setEndTime(String endtime) {
        this.endtime = endtime;
    }
    
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public String getOvertimeDate() {
        return overtimedate;
    }

    public void setOvertimeDate(String overtimedate) {
        this.overtimedate = overtimedate;
    }
    
     public Integer getIdEmployee() {
        return idemployee;
    }

    public void setIdEmployee(Integer idemployee) {
        this.idemployee = idemployee;
    }
}
