/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author myama
 */
public class SalaryMasterData {
    private Integer id;
    private String employeename;
    private Integer salary;
    private Integer idemployee;
    
    public Integer getId() {
    return id;
}
    public void setId(Integer Id) {
        this.id = Id;
    } 
    
    public String getEmployeeName() {
        return employeename;
    }
    
    public void setEmployeeName(String employeename) {
        this.employeename = employeename;
    }
    
    public Integer getSalary() {
        return salary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    
    public Integer getIdEmployee()  {
        return idemployee;
    }
    public void setIdEmployee(Integer idemployee) {
        this.idemployee = idemployee;
    }
}
