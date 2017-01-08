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
public class Database implements IDatabase{
    
    private IDatabase db;

    public Database(IDatabase db) {
        this.db = db;
    }
    
    

    @Override
    public Grad dajGradPoImenu(String imeGrada) throws SQLException {
        return db.dajGradPoImenu(imeGrada);
    }

    @Override
    public ArrayList<Prognoza> dajHistorijskePodatkePrognozaZaGrad(String grad, int brojDana) throws SQLException {
        return db.dajHistorijskePodatkePrognozaZaGrad(grad, brojDana);
    }

    @Override
    public void osvjeziHistorijuPrognoza(WebService ws) throws SQLException, ParseException {
        db.osvjeziHistorijuPrognoza(ws);
    }

    @Override
    public void osvjeziZadnjuPrognozuZaGrad(Grad grad, WebService ws) throws SQLException, ParseException {
        db.osvjeziZadnjuPrognozuZaGrad(grad, ws);
    }

    @Override
    public Boolean ucitajGradoveUBazu(ArrayList<Grad> gradovi) throws SQLException {
        return db.ucitajGradoveUBazu(gradovi);
    }

    @Override
    public Boolean ucitajPrognozeUBazu(ArrayList<Prognoza> prognoze) throws SQLException {
        return db.ucitajPrognozeUBazu(prognoze);
    }
    
    
    
}
