/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.EmployeeModel;

/**
 *
 * @author USer
 */
public interface EmployeeService {

    void addEmployee(EmployeeModel userModel);

    void editEmployee(EmployeeModel userModel);

    void deleteEmployee(EmployeeModel userModel);

    EmployeeModel getById(int id);

    List<EmployeeModel> getDataById();

    List<EmployeeModel> getData();

    void exportEmployeeToExcel();
}
