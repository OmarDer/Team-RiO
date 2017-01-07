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
public class Location {
    
    private String country;
    private String countryCode;
    private String city;
    private Double latitude;
    private Double longitude;
    private Boolean status;
    
    public Location(Boolean status)
    {
        this.status = status;
    }

    public Location(String country, String city, Double latitude, Double longitude, Boolean status, String countryCode) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    
    public Grad getGrad(){
        return new Grad(city, longitude, latitude);
    }
    
    
    
    
    
    
}
