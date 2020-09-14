package com.example.basic;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


     final boolean is_application = BuildConfig.is_applicaion;


    public boolean getIsApplicayion() {
        return is_application;
    }
}
