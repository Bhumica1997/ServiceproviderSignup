package com.example.user.doctorintegration;

import java.io.Serializable;

public class ServiceProvideDetailModel implements Serializable{

    private String Username;
    private String Address;
    private String Email;
    private String passWord;
    private String phoneNumber;
    private String spinner1;
    private String spinner2;
    private String spinner3;
    private double latitude;
    private double longitude;
    private double ratings;
    private String time;
    private String rarestMedicine;
    private String discounts;
    private String area;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpinner1() {
        return spinner1;
    }

    public void setSpinner1(String spinner1) {
        this.spinner1 = spinner1;
    }

    public String getSpinner2() {
        return spinner2;
    }

    public void setSpinner2(String spinner2) {
        this.spinner2 = spinner2;
    }

    public String getSpinner3() {
        return spinner3;
    }

    public void setSpinner3(String spinner3) {
        this.spinner3 = spinner3;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRarestMedicine() {
        return rarestMedicine;
    }

    public void setRarestMedicine(String rarestMedicine) {
        this.rarestMedicine = rarestMedicine;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
