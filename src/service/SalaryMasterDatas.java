/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;
import java.util.List;
import model.SalaryMasterData;


/**
 *
 * @author myama
 */
public interface SalaryMasterDatas {
    
   void addSalary(SalaryMasterData salaryData);

    void editSalary(SalaryMasterData salaryData);

    void deleteSalary(SalaryMasterData salaryData);

    SalaryMasterData getById(int id);

    List<SalaryMasterData> getDataById();
    List<SalaryMasterData> getData();
}
