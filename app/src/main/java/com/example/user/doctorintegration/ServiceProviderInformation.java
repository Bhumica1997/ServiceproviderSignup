package com.example.user.doctorintegration;

/**
 * Created by USER on 3/30/2018.
 */

public class ServiceProviderInformation {


    public String Username;
    public String Address;
    public String Email;
    public String Password;
    public String Phonenumber;
    public String Spinner1;
    public String Spinner2;
    public String Spinner3;
    public double latitude;
    public double longitude;
    public double ratings;
    public String time;
    public String rarestmedicine;
    public String discounts;
    public String area;

    public ServiceProviderInformation(String Username,String Address, String Email, String Password, String Phonenumber, String Spinner1, String Spinner2, String Spinner3,double latitude,double longitude,double ratings,String time,String rarestmedicine,String discounts,String area) {

        this.Username = Username;
        this.Address = Address;
        this.Email = Email;
        this.Password = Password;
        this.Phonenumber = Phonenumber;
        this.Spinner1 = Spinner1;
        this.Spinner2 = Spinner2;
        this.Spinner3 = Spinner3;
        this.latitude=latitude;
        this.longitude=longitude;
        this.ratings=ratings;
        this.time=time;
        this.rarestmedicine=rarestmedicine;
        this.discounts=discounts;
        this.area=area;
    }
}