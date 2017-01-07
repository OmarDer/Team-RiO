/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.model;

/**
 *
 * @author Ragib Smajic
 */
public class Grad {
    
    private int idGrada;
    private String imeGrada;
    private Double longitude;
    private Double latitude;
    private Boolean veciCentar;

    public Grad(String imeGrada, Double longitude, Double latitude, Boolean veciCentar) {
        this.imeGrada = imeGrada;
        this.longitude = longitude;
        this.latitude = latitude;
        this.veciCentar = veciCentar;
    }
    
     public Grad(String imeGrada, Double longitude, Double latitude) {
        this.imeGrada = imeGrada;
        this.longitude = longitude;
        this.latitude = latitude;
        this.veciCentar = null;
    }
     
    public int getIdGrada() {
        return idGrada;
    }

    public void setIdGrada(int idGrada) {
        this.idGrada = idGrada;
    }
     
    public String getImeGrada() {
        return imeGrada;
    }

    public void setImeGrada(String imeGrada) {
        this.imeGrada = imeGrada;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getVeciCentar() {
        return veciCentar;
    }

    public void setVeciCentar(Boolean veciCentar) {
        this.veciCentar = veciCentar;
    }
    
    
    
    
    
}
