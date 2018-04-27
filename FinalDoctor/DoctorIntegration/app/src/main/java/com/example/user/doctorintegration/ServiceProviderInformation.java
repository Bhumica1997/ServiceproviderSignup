package com.example.user.doctorintegration;

/**
 * Created by USER on 3/30/2018.
 */

public class ServiceProviderInformation {


    public String username;
    public String address;
    public String email;
    public String password;
    public String phonenumber;
    public String spinner1;
    public String spinner2;
    public String spinner3;
    public double latitude;
    public double longitude;
    public double ratings;
    public String time;
    public String rarestmedicine;
    public String discounts;
    public String area;

    public ServiceProviderInformation(){

    }

    public ServiceProviderInformation(String Username,String Address, String Email, String Password, String Phonenumber, String Spinner1, String Spinner2, String Spinner3,double latitude,double longitude,double ratings,String time,String rarestmedicine,String discounts,String area) {

        this.username = Username;
        this.address = Address;
        this.email = Email;
        this.password = Password;
        this.phonenumber = Phonenumber;
        this.spinner1 = Spinner1;
        this.spinner2 = Spinner2;
        this.spinner3 = Spinner3;
        this.latitude=latitude;
        this.longitude=longitude;
        this.ratings=ratings;
        this.time=time;
        this.rarestmedicine=rarestmedicine;
        this.discounts=discounts;
        this.area=area;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public String getRarestmedicine() {
        return rarestmedicine;
    }

    public void setRarestmedicine(String rarestmedicine) {
        this.rarestmedicine = rarestmedicine;
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