/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.LevelModel;

/**
 *
 * @author USer
 */
public interface LevelService {

    void addLevel(LevelModel levelmodel);

    void editLevel(LevelModel levelmodel);

    void deleteLevel(LevelModel levelmodel);

    LevelModel getById(int id);

    List<LevelModel> getDataById();

    List<LevelModel> getData();

    List<LevelModel> searching(String nama);

    void exportLevelToExcel();
}
