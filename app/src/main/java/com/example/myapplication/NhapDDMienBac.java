package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NhapDDMienBac extends AppCompatActivity {



    private int buttons[] = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnC,
            R.id.btn4,R.id.btn5,R.id.btn6,
            R.id.btn7,R.id.btn8,R.id.btn9,
            R.id.btnZero,R.id.btnDot, R.id.btnEnter, R.id.btnOk
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_ddmien_bac);
    }
}