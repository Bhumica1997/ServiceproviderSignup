package com.example.user.doctorintegration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpsignupActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    @NotEmpty
    private EditText editTextEmail;
    @NotEmpty
    private EditText editTextPassword;
    @NotEmpty
    private EditText usrname;
    @NotEmpty
    private EditText phno;
    private Button but1;
    private Button but2;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @NotEmpty
    private EditText addr;
    DatabaseReference databaseUsers;

    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spsignup);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
//
        databaseUsers = FirebaseDatabase.getInstance().getReference("ServiceProviderInformation");

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail1);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword1);
        usrname=(EditText) findViewById(R.id.spname);
        phno=(EditText) findViewById(R.id.spphno);
        addr = (EditText) findViewById(R.id.spaddress);
       // but2 = (Button) findViewById(R.id.button);
        but1 = (Button) findViewById(R.id.buttonsp);
        progressDialog = new ProgressDialog(this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    registerUser();

                } else {
                    Toast.makeText(SpsignupActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        //attaching listener to button
        //init();
    }

    private void updateUI() {

//        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
//        Password = (EditText) findViewById(R.id.editTextPassword);
//        usrname = (EditText) findViewById(R.id.usrname);
//        phno = (EditText) findViewById(R.id.phno);
//        but1 = (Button) findViewById(R.id.but1);
//
//        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
//        awesomeValidation.addValidation(UsersignupActivity.this, R.id.usrname, "^[A-Za-z_]\\w{3,25}$", R.string.namerror);
//        awesomeValidation.addValidation(UsersignupActivity.this, R.id.editTextEmail, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
//        awesomeValidation.addValidation(UsersignupActivity.this, R.id.editTextPassword, regexPassword, R.string.passworderror);
//        awesomeValidation.addValidation(UsersignupActivity.this, R.id.phno, "^\\d{10}$", R.string.phonenumbererror);
//        spinner = (Spinner) findViewById(R.id.spinner1);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail1);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword1);
        usrname = (EditText) findViewById(R.id.spname);
        phno = (EditText) findViewById(R.id.spphno);
        addr = (EditText) findViewById(R.id.spaddress);

        but1 = (Button) findViewById(R.id.buttonsp);

        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        awesomeValidation.addValidation(SpsignupActivity.this, R.id.spname, "^[A-Za-z_]\\w{3,25}$", R.string.namerror);
        awesomeValidation.addValidation(SpsignupActivity.this, R.id.editTextEmail1, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(SpsignupActivity.this, R.id.editTextPassword1, regexPassword, R.string.passworderror);
        awesomeValidation.addValidation(SpsignupActivity.this, R.id.spaddress, "^[#.0-9a-zA-Z\\s,-]+$", R.string.addresserror);
        awesomeValidation.addValidation(SpsignupActivity.this, R.id.spphno, "^\\d{10}$", R.string.phonenumbererror);


    }

    private void registerUser() {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = usrname.getText().toString().trim();
        String phn = phno.getText().toString().trim();
        String address = addr.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name)) {
            //if the value is not given displaying a toast

            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phn)){
            Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
            return;

        }

        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please enter address", Toast.LENGTH_LONG).show();
            return;

        }



        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new serviceproviders
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(SpsignupActivity.this, "Add your Certifications", Toast.LENGTH_LONG).show();

                            addUser();
                            Intent toy2 = new Intent(SpsignupActivity.this, Firebase.class);

                            startActivity(toy2);

                        } else {
                            //display some message here
                            Toast.makeText(SpsignupActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();

                    }
                });

    }

//
//    private void init() {
//        but2 = (Button) findViewById(R.id.button);
//        but2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toy = new Intent(SpsignupActivity.this, Firebase.class);
//                startActivity(toy);
//            }
//        });
//    }

    private void addUser() {

        String name = usrname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
         String password = editTextPassword.getText().toString().trim();
        String phn = phno.getText().toString().trim();
        String address = usrname.getText().toString().trim();
        String spinner =spinner1.getSelectedItem().toString().trim();
        String sspinner = spinner2.getSelectedItem().toString().trim();
        String ssspinner = spinner3.getSelectedItem().toString().trim();



        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phn) && !TextUtils.isEmpty(address)) {

            String id = databaseUsers.push().getKey();

            ServiceProviderInformation serviceproviders = new ServiceProviderInformation(name, address,email, password, phn, spinner, sspinner, ssspinner);

            databaseUsers.child(id).setValue(serviceproviders);


            //  Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        }

    }

}

//        else if(TextUtils.isEmpty(name)){
//            //if the value is not given displaying a toast
//
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        else {
//            Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
//            return;
//
//
//        }



//        progressDialog = new ProgressDialog(this);
//
//        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//        updateUI();
//
//        but1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (awesomeValidation.validate()) {
//                    registerUser();
//
//                } else {
//                    Toast.makeText(SpsignupActivity.this, "Error", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//      //  init();



//    private void updateUI() {
//
//        spinner = (Spinner) findViewById(R.id.spinner1);
//        editTextEmail = (EditText) findViewById(R.id.editTextEmail1);
//        editTextPassword = (EditText) findViewById(R.id.editTextPassword1);
//        usrname = (EditText) findViewById(R.id.spname);
//        phno = (EditText) findViewById(R.id.spphno);
//        addr = (EditText) findViewById(R.id.spaddress);
//
//        but1 = (Button) findViewById(R.id.buttonsp);
//
//        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
//        awesomeValidation.addValidation(SpsignupActivity.this, R.id.spname, "^[A-Za-z_]\\w{3,25}$", R.string.namerror);
//        awesomeValidation.addValidation(SpsignupActivity.this, R.id.editTextEmail1, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
//        awesomeValidation.addValidation(SpsignupActivity.this, R.id.editTextPassword1, regexPassword, R.string.passworderror);
//        awesomeValidation.addValidation(SpsignupActivity.this, R.id.spaddress, "^[#.0-9a-zA-Z\\s,-]+$", R.string.addresserror);
//        awesomeValidation.addValidation(SpsignupActivity.this, R.id.spphno, "^\\d{10}$", R.string.phonenumbererror);
//
//    }
//    private void registerUser() {
//
//
//        String email = editTextEmail.getText().toString().trim();
//        String editTextPAssword = editTextPassword.getText().toString().trim();
//        String name = usrname.getText().toString().trim();
//        String phn = phno.getText().toString().trim();
//        String address = addr.getText().toString().trim();
//
//        //checking if email and passwords are empty
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(editTextPAssword)) {
//            Toast.makeText(this, "Please enter editTextPAssword", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(name)) {
//            //if the value is not given displaying a toast
//
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(phn)) {
//            Toast.makeText(this, "Please enter phno no", Toast.LENGTH_LONG).show();
//            return;
//
//        }
//
//        if (TextUtils.isEmpty(address)) {
//            Toast.makeText(this, "Please enter address", Toast.LENGTH_LONG).show();
//            return;
//
//        }
//
//        //if the email and editTextPAssword are not empty
//        //displaying a progress dialog
//
//        progressDialog.setMessage("Registering Please Wait...");
//        progressDialog.show();
//
//        //creating a new serviceproviders
//        firebaseAuth.createUserWithEmailAndPassword(email, editTextPAssword)
//                .addOnCompleteListener(SpsignupActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        //checking if success
//                        if (task.isSuccessful()) {
//                            //display some message here
//                            Toast.makeText(SpsignupActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            //display some message here
//                            Toast.makeText(SpsignupActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
//                        }
//                        progressDialog.dismiss();
//                        addUser();
//                        Intent toy2 = new Intent(SpsignupActivity.this, MainActivity.class);
//                        startActivity(toy2);
//
//                    }
//                });
//    }

//    private void init() {
//        but2 = (Button) findViewById(R.id.button);
//        but2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toy = new Intent(SpsignupActivity.this, Firebase.class);
//                startActivity(toy);
//            }
//        });
//    }

//    private void addUser() {
//
//        String Username = usrname.getText().toString().trim();
//        String Phonenumber = phno.getText().toString().trim();
//        String Address = usrname.getText().toString().trim();
//        String Spinner = spinner.getSelectedItem().toString().trim();
//        String Email = editTextEmail.getText().toString().trim();
//        String editTextPAssword = editTextPassword.getText().toString().trim();
//
//        if (!TextUtils.isEmpty(Username) && !TextUtils.isEmpty(Phonenumber) && !TextUtils.isEmpty(Address)) {
//
//            String id = databaseUsers.push().getKey();
//
//            ServiceProviderInformation serviceproviders = new ServiceProviderInformation(Username, Address, Email, editTextPAssword, Phonenumber, Spinner);
//
//            databaseUsers.child(id).setValue(serviceproviders);
//        }
//    }

//    private void init() {
//        but2 = (Button) findViewById(R.id.button);
//        but2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toy = new Intent(SpsignupActivity.this, Firebase.class);
//                startActivity(toy);
//            }
//        });
//    }



//        databaseUsers = FirebaseDatabase.getInstance().getReference("ServiceProviderInformation");
//
//        firebaseAuth = FirebaseAuth.getInstance();
//




//                if (usrname.getText().toString().length() < 2) {
//                    usrname.setError("Enter more than 3 characters");
//                    usrname.requestFocus();
//                } else {
//                    registerUser();
//                }
////                if (usrname.getText().toString().length() < 3 || !addr.getText().toString().matches("/^[a-zA-Z0-9]+([a-zA-Z0-9](_|-| )[a-zA-Z0-9])*[a-zA-Z0-9]+$/") ) {
////                    usrname.setError("Username should have atleast 3 characters");
////                    usrname.requestFocus();
////                }
//                if (!addr.getText().toString().matches("^[#.0-9a-zA-Z\\s,-]+$")) {
//                    addr.setError("Enter your address");
//                    addr.requestFocus();
//                } else {
//                    registerUser();
//                }
////                if (editTextEmail.getText().toString().trim().length() == 0) {
////                    editTextEmail.setError(" email not entered");
////                    editTextEmail.requestFocus();
////                }
//                if (!editTextEmail.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$")) {
//                    editTextEmail.setError("pattern expected john@gmail.com");
//                    editTextEmail.requestFocus();
//                } else {
//                    registerUser();
//                }
//                if (editTextPassword.getText().toString().length() < 8) {
//                    editTextPassword.setError("Password should be atleast of 8 charactors");
//                    editTextPassword.requestFocus();
//                } else {
//                    registerUser();
//                }
//                if (!phno.getText().toString().matches("\\d{10}")) {
//                    phno.setError("phone number should have 10 digits");
//                    phno.requestFocus();
//                } else {
//
//                    registerUser();
//                }
//
//            }
//        });
//        init();
//    }






//                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//
//                        "\\@" +
//
//                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//
//                        "(" +
//
//                        "\\." +
//
//                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
//
//                        ")+";
//
//
//                String email = editTextEmail.getText().toString();
//
//                Matcher matcher= Pattern.compile(validemail).matcher(email);
//
//
//                if (matcher.matches()){
//                    //Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_LONG).show();
//                    //return true;
//                }
//                else {
//                    //Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
//                    //return false;
//                }
//                String phn = phno.getText().toString();
//
//                if(isValidMobile(phn)){
//
//                    registerUser();
//
//                }else{
//                    Toast.makeText(SpsignupActivity.this,"InvalidUser",Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        });
//
//        //attaching listener to button



//    public static boolean isValidMobile(String phno) {
//        boolean check = false;
//        if (!Pattern.matches("[a-zA-Z]+", phno)) {
//            if (phno.length() < 11 || phno.length() > 13) {
//                // if(phno.length() != 10) {
//                check = false;
//                // txtPhone.setError("Not Valid Number");
//            } else {
//                check = android.util.Patterns.phno.matcher(phno).matches();
//            }
//        } else {
//            check = false;
//        }
//        return check;
//    }
//}