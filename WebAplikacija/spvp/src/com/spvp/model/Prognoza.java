/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.model;

import java.util.Date;

/**
 *
 * @author Ragib Smajic
 */
public class Prognoza {
    
    private Date zaDatum;
    private Grad zaGrad;
    private double temperatura;
    private double minTemperatura;
    private double maxTemperatura;
    private double vlaznostZraka;
    private double pritisakZraka;
    private double brzinaVjetra;
    private double vidljivost;
    private String vrijeme;
    private String kodDrzave;

    public Prognoza(Date zaDatum, Grad zaGrad, double temperatura, double minTemperatura, double maxTemperatura, double vlaznostZraka, double pritisakZraka, double brzinaVjetra, double vidljivost, String vrijeme, String kodDrzave) {
        this.zaDatum = zaDatum;
        this.zaGrad = zaGrad;
        this.temperatura = temperatura;
        this.minTemperatura = minTemperatura;
        this.maxTemperatura = maxTemperatura;
        this.vlaznostZraka = vlaznostZraka;
        this.pritisakZraka = pritisakZraka;
        this.brzinaVjetra = brzinaVjetra;
        this.vidljivost = vidljivost;
        this.vrijeme = vrijeme;
        this.kodDrzave = kodDrzave;
    }
    
    public String getKodDrzave() {
        return kodDrzave;
    }
   
    public void setKodDrzave(String kodDrzave) {
        this.kodDrzave = kodDrzave;
    }

    public Date getZaDatum() {
        return zaDatum;
    }

    /**
     * @param zaDatum the zaDatum to set
     */
    public void setZaDatum(Date zaDatum) {
        this.zaDatum = zaDatum;
    }

    /**
     * @return the zaGrad
     */
    public Grad getZaGrad() {
        return zaGrad;
    }

    /**
     * @param zaGrad the zaGrad to set
     */
    public void setZaGrad(Grad zaGrad) {
        this.zaGrad = zaGrad;
    }

    /**
     * @return the temperatura
     */
    public double getTemperatura() {
        return temperatura;
    }

    /**
     * @param temperatura the temperatura to set
     */
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * @return the minTemperatura
     */
    public double getMinTemperatura() {
        return minTemperatura;
    }

    /**
     * @param minTemperatura the minTemperatura to set
     */
    public void setMinTemperatura(double minTemperatura) {
        this.minTemperatura = minTemperatura;
    }

    /**
     * @return the maxTemperatura
     */
    public double getMaxTemperatura() {
        return maxTemperatura;
    }

    /**
     * @param maxTemperatura the maxTemperatura to set
     */
    public void setMaxTemperatura(double maxTemperatura) {
        this.maxTemperatura = maxTemperatura;
    }

    /**
     * @return the vlaznostZraka
     */
    public double getVlaznostZraka() {
        return vlaznostZraka;
    }

    /**
     * @param vlaznostZraka the vlaznostZraka to set
     */
    public void setVlaznostZraka(double vlaznostZraka) {
        this.vlaznostZraka = vlaznostZraka;
    }

    /**
     * @return the pritisakZraka
     */
    public double getPritisakZraka() {
        return pritisakZraka;
    }

    /**
     * @param pritisakZraka the pritisakZraka to set
     */
    public void setPritisakZraka(double pritisakZraka) {
        this.pritisakZraka = pritisakZraka;
    }

    /**
     * @return the brzinaVjetra
     */
    public double getBrzinaVjetra() {
        return brzinaVjetra;
    }

    /**
     * @param brzinaVjetra the brzinaVjetra to set
     */
    public void setBrzinaVjetra(double brzinaVjetra) {
        this.brzinaVjetra = brzinaVjetra;
    }

    /**
     * @return the vidljivost
     */
    public double getVidljivost() {
        return vidljivost;
    }

    /**
     * @param vidljivost the vidljivost to set
     */
    public void setVidljivost(double vidljivost) {
        this.vidljivost = vidljivost;
    }

    /**
     * @return the vrijeme
     */
    public String getVrijeme() {
        return vrijeme;
    }

    /**
     * @param vrijeme the vrijeme to set
     */
    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

}