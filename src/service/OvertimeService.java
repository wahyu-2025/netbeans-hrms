/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.OvertimeModel;

/**
 *
 * @author USer
 */
public interface OvertimeService {

    void addOvertime(OvertimeModel overtimemodel);

    void editOvertime(OvertimeModel overtimemodel);

    void deleteOvertime(OvertimeModel overtimemodel);

    OvertimeModel getById(int id);

    List<OvertimeModel> getDataById();

    List<OvertimeModel> getData();
   
    void exportOvertimeToExcel();
}
