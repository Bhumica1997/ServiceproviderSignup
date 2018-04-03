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
    public String Spinner;

    public ServiceProviderInformation(String Username,String Address, String Email, String Password, String Phonenumber, String Spinner) {

        this.Username = Username;
        this.Address = Address;
        this.Email = Email;
        this.Password = Password;
        this.Phonenumber = Phonenumber;
        this.Spinner = Spinner;
    }
}