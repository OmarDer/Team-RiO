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
    private String gradId;
    private String gradName;
    private String gradLatitude;
    private String gradLongitude;
    
    public Grad(String gradId, String gradName, String gradLatitude, String gradLongitude)
    {
        this.gradId = gradId;
        this.gradName = gradName;
        this.gradLatitude = gradLatitude;
        this.gradLongitude = gradLongitude;
    }

    public String getGradId() {
        return gradId;
    }

    public void setGradId(String gradId) {
        this.gradId = gradId;
    }

    public String getGradName() {
        return gradName;
    }

    public void setGradName(String gradName) {
        this.gradName = gradName;
    }

    public String getGradLatitude() {
        return gradLatitude;
    }

    public void setGradLatitude(String gradLatitude) {
        this.gradLatitude = gradLatitude;
    }

    public String getGradLongitude() {
        return gradLongitude;
    }

    public void setGradLongitude(String gradLongitude) {
        this.gradLongitude = gradLongitude;
    }
    
    
    
}
