/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;  

import java.time.LocalDate;

/**
 *
 * @author USer
 */
public class EmployeeSalaryModel {

    private Integer id;
    private String employeename;
    private Integer basicsalary;
    private Integer overtimebonus;
    private Integer cutlate;
    private Integer totalsalarythismonth;
    private String nik;
    private Integer idemployee;
    private String deptname;
    private String levelname;  
//    private String getmonth;
//    private String getyear;
    private LocalDate salaryPeriod;

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
    
    public Integer getBasicSalary() {
        return basicsalary;
    }

    public void setBasicSalary(Integer basicsalary) {
        this.basicsalary = basicsalary;
    }
    
    public Integer getOvertimeBonus() {
        return overtimebonus;
    }

    public void setOvertimeBonus(Integer overtimebonus) {
        this.overtimebonus = overtimebonus;
    }
    
    public Integer getCutLate() {
        return cutlate;
    }

    public void setCutLate(Integer cutlate) {
        this.cutlate = cutlate;
    }
    
    public Integer getSalaryThisMonth() {
        return totalsalarythismonth;
    }

    public void setSalaryThisMonth(Integer totalsalarythismonth) {
        this.totalsalarythismonth = totalsalarythismonth;
    }
    
     public String getEmployeeNik() {
        return nik;
    }

    public void setEmployeeNik(String nik) {
        this.nik = nik;
    }
    
    public Integer getIdEmployee() {
        return idemployee;
    }

    public void setIdEmployee(Integer idemployee) {
        this.idemployee = idemployee;
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
    
//    public String getMonth() {
//        return getmonth;
//    }
//
//    public void setMonth(String getmonth) {
//        this.getmonth = getmonth;
//    }
//    
//     public String getYear() {
//        return getyear;
//    }
//
//    public void setYear(String getyear) {
//        this.getyear = getyear;
//    }
    
    public LocalDate getSalaryPeriod() {
        return salaryPeriod;
    }
    
    public void setSalaryPeriod(LocalDate salaryPeriod) {
        this.salaryPeriod = salaryPeriod;
    }
}
