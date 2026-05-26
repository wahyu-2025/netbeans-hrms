/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.DeptModel;

/**
 *
 * @author wayan
 */
public interface DeptService {

    void addDept(DeptModel deptmodel);

    void editDept(DeptModel deptmodel);

    void deleteDept(DeptModel deptmodel);

    DeptModel getById(int id);

    List<DeptModel> getDataById();

    List<DeptModel> getData();

    List<DeptModel> searching(String nama);
   
    void exportDeptToExcel();
}
