/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.EmployeeSalaryModel;

/**
 *
 * @author wayan
 */
public interface EmployeeSalaryService {

    void addEmployeeSalary(EmployeeSalaryModel employeesalary);

    void deleteEmployeeSalary(EmployeeSalaryModel employeesalary);

    EmployeeSalaryModel getById(int id);

    List<EmployeeSalaryModel> getDataById();

    List<EmployeeSalaryModel> getData();

    void exportEmployeeSalaryToExcel();
}
