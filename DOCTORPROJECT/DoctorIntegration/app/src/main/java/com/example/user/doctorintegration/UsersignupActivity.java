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


//        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phn)) {
//
//            String id = databaseUsers.push().getKey();
//
//            UserInformation user = new UserInformation(name, email, password, phn);
//
//            databaseUsers.child(id).setValue(user);
//
//        }




//                if (usrname.getText().toString().length() < 2 && !usrname.getText().toString().matches("[a-zA-Z][a-zA-Z ]*")){
//                        usrname.setError("Username should have atleast 3 characters");
//                        usrname.requestFocus();
//                         }
//
//                if (!Email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$")) {
//                        Email.setError("pattern expected john@gmail.com");
//                        Email.requestFocus();
//                         }
//
//
//                if (Password.getText().toString().length()== 0) {
//                            Password.setError("Password should be atleast of 8 charactors");
//                            Password.requestFocus();
//                    }
//                    if (!phno.getText().toString().matches("\\d{10}")) {
//                          phno.setError("phone number should have 10 digits");
//                           phno.requestFocus();
//                    }
//                String validname = "[a-zA-Z][a-zA-Z ]*";
//
//                String name = usrname.getText().toString();
//
//                Matcher matcher= Pattern.compile(validname).matcher(name);
//
//                if (matcher.matches()){
//                    //Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_LONG).show();
//                    //return true;
//
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"Enter Valid Name",Toast.LENGTH_LONG).show();
//                    return ;
//                }
//                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//
//                        "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
//
//
//                String email = Email.getText().toString();
//
//                Matcher matcher1 = Pattern.compile(validemail).matcher(email);
//
//                if (matcher.matches()){
//                    //Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_LONG).show();
//                    //return true;
//                    registerUser();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"Enter Valid Email-Id",Toast.LENGTH_LONG).show();
//                    return ;
//                }
//
//                String phn = phno.getText().toString().trim();
//
//                //   Matcher matcher1= Pattern.compile(validnum).matcher(phn);
//                if(PhoneNumberUtils.isGlobalPhoneNumber(phn)){
//
//                    registerUser();
//
//                }else{
//                    Toast.makeText(UsersignupActivity.this,"InvalidUser",Toast.LENGTH_LONG).show();
//                }
////                addUser();
////                Intent toy2 = new Intent(UsersignupActivity.this, NavigateActivity.class);
////
////                startActivity(toy2);
////                registerUser();
////                addUser();
//
//            }
//        });
//
//            }
//        });
//    }


//
//    public boolean getValidMobile(String mobileNo,String validPattern){
//
//        Matcher matcher1= Pattern.compile(validPattern).matcher(mobileNo);
//        if(matcher1.matches()){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    public boolean getValidLandline(String mobileNo,String validPattern){
//
//        Matcher matcher1= Pattern.compile(validPattern).matcher(mobileNo);
//        if(matcher1.matches()){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//
//
//    public static boolean isValidMobile(String phone) {
//        boolean check = false;
//        if (!Pattern.matches("[a-zA-Z]+", phone)) {
//            if (phone.length() < 9 || phone.length() > 13) {
//                // if(phone.length() != 10) {
//                check = false;
//                // txtPhone.setError("Not Valid Number");
//            } else {
//                check = android.util.Patterns.PHONE.matcher(phone).matches();
//            }
//        } else {
//            check = false;
//        }
//        return check;
//    }
//
//
//
//}
//    public static boolean isValidMobile(String phone) {
//        boolean check = false;
//        if (!Pattern.matches("[a-zA-Z]+", phone)) {
//            if (phone.length() < 9 || phone.length() > 13) {
//                // if(phone.length() != 10) {
//                check = false;
//                // txtPhone.setError("Not Valid Number");
//            } else {
//                check = android.util.Patterns.PHONE.matcher(phone).matches();
//            }
//        } else {
//            check = false;
//        }
//        return check;
//    }
//
//
//
//}

//
//public static boolean checkForSpecialChar(String aName){
//
//    String str = aName;
//    int specials = 0, digits = 0, letters = 0, spaces = 0;
//    for (int i = 0; i < str.length(); i++) {
//        char ch = str.charAt(i);
//        if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isSpace(ch)) {
//            specials++;
//        } else if (Character.isDigit(ch)) {
//            digits++;
//        } else if (Character.isSpace(ch)) {
//            ++spaces;
//        } else {
//            letters++;
//        }
//    }
//    Log.d("--TestCount",""+specials+" didgi "+digits+" letter "+letters) ;
//    if(specials>0){
//        return true;   //special character exist
//    }else{
//        return false;  //not found
//    }
//
//
//}
//}

//
//}



















//        progressDialog = new ProgressDialog(this);
//
//
//        databaseUsers = FirebaseDatabase.getInstance().getReference("UserInformation");
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        //Registration button functionality
//        button1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//
//                // TODO Auto-generated method stub
////
//
//                     if (username.getText().toString().matches("^[A-Za-z_]\\w{3,25}$") ){
//                        username.setError("Username should have atleast 3 characters");
//                        username.requestFocus();
//                         }
//
//                    if (!Email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$")) {
//                        Email.setError("pattern expected john@gmail.com");
//                        Email.requestFocus();
//                         }
//
////
//                    if (Password.getText().toString().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")) {
//                            Password.setError("Password should be atleast of 8 charactors");
//                            Password.requestFocus();
//                    }
//                    if (!phone.getText().toString().matches("\\d{10}")) {
//                          phone.setError("phone number should have 10 digits");
//                           phone.requestFocus();
//                    }else {
//                    registerUser();
//                    }
//
//                   }
//
//        });
//
//    }
