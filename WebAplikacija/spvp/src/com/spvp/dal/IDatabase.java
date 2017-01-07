/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.dal;

import com.spvp.model.Grad;
import com.spvp.model.Prognoza;
import com.spvp.services.WebService;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ragib Smajic
 */
public interface IDatabase {

    Grad dajGradPoImenu(String imeGrada) throws SQLException;

    ArrayList<Prognoza> dajHistorijskePodatkePrognozaZaGrad(String grad, int brojDana) throws SQLException;

    void osvjeziHistorijuPrognoza(WebService ws) throws SQLException, ParseException;

    void osvjeziZadnjuPrognozuZaGrad(String imeGrada, WebService ws) throws SQLException, ParseException;

    Boolean ucitajGradoveUBazu(ArrayList<Grad> gradovi) throws SQLException;

    Boolean ucitajPrognozeUBazu(ArrayList<Prognoza> prognoze) throws SQLException;
    
}
