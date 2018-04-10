package com.example.user.spsignup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class third extends Activity {
    Button btnReg;
    EditText edtFirst,edtLast, edtUser, edtPass, edtConfPass;
    @NotEmpty
    @Email
    private EditText edtEmail;
    private Matcher matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //initialization of all editText
        edtFirst=(EditText)findViewById(R.id.edtfirstname);
        edtLast=(EditText)findViewById(R.id.edtlastname);
        edtUser=(EditText)findViewById(R.id.edtUsername);
        edtPass=(EditText)findViewById(R.id.edtPass);
        edtConfPass=(EditText)findViewById(R.id.edtConfirmPass);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        //Initialization of Register Button
        btnReg=(Button)findViewById(R.id.button1);

        //Registration button functionality
        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(edtEmail.getText().toString().trim().length()==0) {
                    edtEmail.setError("First name not entered");
                    edtEmail.requestFocus();
                }

                if (!edtEmail.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$")) {
                        edtEmail.setError("Invalid pattern");
                        edtEmail.requestFocus();
                        }else{
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }

                if(edtFirst.getText().toString().length()==0){
                    edtFirst.setError("First name not entered");
                    edtFirst.requestFocus();
                }
                if(edtFirst.getText().toString().length()<2) {
                    edtFirst.setError("Name must be minimun 3 characters");
                    edtFirst.requestFocus();
                }
                if(edtLast.getText().toString().length()==0){
                    edtLast.setError("Last name not entered");
                    edtLast.requestFocus();
                }

                if(edtUser.getText().toString().length()==0){
                    edtUser.setError("Username is Required");
                    edtUser.requestFocus();
                }
                if(edtPass.getText().toString().length()==0){
                    edtPass.setError("Password not entered");
                    edtPass.requestFocus();
                }
                if(edtConfPass.getText().toString().length()==0){
                    edtConfPass.setError("Please confirm password");
                    edtLast.requestFocus();
                }
                if(!edtPass.getText().toString().equals(edtConfPass.getText().toString())){
                    edtConfPass.setError("Password Not matched");
                    edtConfPass.requestFocus();
                }
                if(edtPass.getText().toString().length()<8 ) {
                    edtPass.setError("Password should be atleast of 8 charactors");
                    edtPass.requestFocus();
                }


        }
    });
}
}