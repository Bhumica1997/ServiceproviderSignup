package com.example.user.doctorintegration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UsersignupActivity extends AppCompatActivity {
    // public Button but1;
    @NotEmpty
    private EditText Email;
    @NotEmpty
    private EditText Password;
    @NotEmpty
    private EditText usrname;
    @NotEmpty
    private EditText phno;
    private Button but1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    AwesomeValidation awesomeValidation;
    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersignup);

        databaseUsers = FirebaseDatabase.getInstance().getReference("UserInformation");
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    registerUser();

                } else {
                    Toast.makeText(UsersignupActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });


        //init();
    }


    //attaching listener to button


    private void updateUI() {
        Email = (EditText) findViewById(R.id.editTextEmail);
        Password = (EditText) findViewById(R.id.editTextPassword);
        usrname = (EditText) findViewById(R.id.usrname);
        phno = (EditText) findViewById(R.id.phno);
        but1 = (Button) findViewById(R.id.but1);


        String regexname = "^[A-Za-z_ ]\\w{2,25}$";
        String regexPassword = "^([a-zA-Z0-9@*#]{8,15})$";
        String regexemail = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
                               "@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$;";
        awesomeValidation.addValidation(UsersignupActivity.this, R.id.usrname,regexname, R.string.namerror);
        awesomeValidation.addValidation(UsersignupActivity.this, R.id.editTextEmail,android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(UsersignupActivity.this, R.id.editTextPassword, regexPassword, R.string.passworderror);
        awesomeValidation.addValidation(UsersignupActivity.this, R.id.phno, "^\\d{10}$", R.string.phonenumbererror);


    }

    private void registerUser() {

        //getting email and password from edit texts
       /* String username = usrname.getText().toString().trim();
        String phoneno = phno.getText().toString().trim();*/
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String name = usrname.getText().toString().trim();
        String phn = phno.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            //if the value is not given displaying a toast

            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(phn)) {
            Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
            return;

        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            addUser();
                            Intent toy2 = new Intent(UsersignupActivity.this, NavigateActivity.class);
                            startActivity(toy2);
                            Toast.makeText(UsersignupActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();

                            usrname.setText("");
                            usrname.getText().clear();

                            Email.setText("");
                            Email.getText().clear();

                            Password.setText("");
                            Password.getText().clear();

                            phno.setText("");
                            phno.getText().clear();

                        } else {
                            //display some message here
                            Toast.makeText(UsersignupActivity.this, "Enter Proper Email", Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();


                    }
                });

    }

    private void addUser() {

        String name = usrname.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String phn = phno.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phn)) {

            String id = databaseUsers.push().getKey();

            UserInformation user = new UserInformation(name, email, password, phn);

            databaseUsers.child(id).setValue(user);


            //  Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        }
    }
}


