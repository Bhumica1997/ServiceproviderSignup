package com.example.user.activitylog1;

import android.icu.text.AlphabeticIndex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;


import java.util.List;

import dalvik.annotation.TestTarget;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Length(min = 3,max = 25)
    private EditText editTextUsername;

    @NotEmpty
    @Password(scheme = Password.Scheme.ANY)
    private EditText editTextPassword;

    @NotEmpty
    @Email
    private EditText editTextEmail;

    @NotEmpty
    @Length(min = 10, max = 10)
    private EditText editTextPhoneno;
    private Button signup;


    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validator = new Validator(this);
        validator.setValidationListener(this);

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPhoneno = (EditText) findViewById(R.id.PhoneNumber);


        signup = (Button) findViewById(R.id.buttonsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
                String usrname = editTextUsername.getText().toString().trim();
                String pswd = editTextPassword.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String phn = editTextPhoneno.getText().toString().trim();
                if(usrname.isEmpty() && pswd.isEmpty() && email.isEmpty() && phn.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

        }
    }
}
