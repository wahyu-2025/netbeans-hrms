/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.AttendanceModel;

/**
 *
 * @author myama
 */
public interface AttendanceService {
//    void addAttendance(AttendanceModel attendancemodel);
    boolean addAttendance(AttendanceModel attendancemodel);
    List<AttendanceModel> getData();
    void exportAttendance();
    void exportLate();
}
