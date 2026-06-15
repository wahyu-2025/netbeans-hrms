/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.AttendanceLimitModel;

/**
 *
 * @author USer
 */
public interface AttendanceLimitService {

    void addAttendanceLimit(AttendanceLimitModel attendanceLimitModel);

    void editAttendanceLimit(AttendanceLimitModel attendanceLimitModel);

    void deleteAttendanceLimit(AttendanceLimitModel attendanceLimitModel);

    AttendanceLimitModel getById(int id);

    List<AttendanceLimitModel> getDataById();

    List<AttendanceLimitModel> getData();

}
