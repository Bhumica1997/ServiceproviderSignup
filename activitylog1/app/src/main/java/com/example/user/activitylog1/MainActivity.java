package com.example.user.activitylog1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
     private Button bhttonInJava;
     private TextView myedittext;
     private RadioButton radioButton;
     private RadioGroup radioGroup;
     private ToggleButton private1;
    private ToggleButton private2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        private1= (ToggleButton) findViewById(R.id.toggleButton)
         private2= (ToggleButton)  findViewById(R.id.toggleButton)
         RadioGroup = (RadioGroup) findViewById(R.id.radiogroup)


    }

}
