/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.ResignModel;

/**
 *
 * @author USer
 */
public interface ResignService {

    void addResign(ResignModel resignmodel);

    void editResign(ResignModel resignmodel);

    void deleteResign(ResignModel resignmodel);

    ResignModel getById(int id);

    List<ResignModel> getDataById();

    List<ResignModel> getData();
}
