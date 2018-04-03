package com.example.user.dcotorproject;

/**
 * Created by USER on 3/24/2018.
 */

public class serviceproviders {
        String username;
        String userph;
        String address;
        String spinner;
        String email;


        public serviceproviders() {

        }

        public serviceproviders(String username, String userph, String address, String spinner,String email) {

            this.username = username;
            this.userph = userph;
            this.address = address;
            this.spinner = spinner;
            this.email = email;
        }



        public String getusername() {
            return username;
        }

        public String getuserph() {
            return userph;
        }

        public String getaddress() {return address; }

        public  String getSpinner() {return spinner;}
        public String getEmail() {return email;}

    }
