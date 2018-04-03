package com.example.user.dcotorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.user.dcotorproject.NavigateActivity;
import com.example.user.dcotorproject.R;
import com.example.user.dcotorproject.ServiceprovidersignupActivity;
import com.example.user.dcotorproject.UsersignupActivity;

public class MainActivity extends AppCompatActivity {
    public Button but1;
    public Button button2;
    public Button button3;

    public void init() {
        but1 = (Button) findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(MainActivity.this, NavigateActivity.class);
                startActivity(toy);

            }
        });
    }
    public void init1() {
        button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy1 = new Intent(MainActivity.this, UsersignupActivity.class);
                startActivity(toy1);

            }
        });
    }
    public void init2() {
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy1 = new Intent(MainActivity.this, ServiceprovidersignupActivity.class);

                startActivity(toy1);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        init1();
        init2();

    }
}