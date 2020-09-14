package com.example.zujiandemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.annotation.BindPath;
import com.example.router.Arouter;


@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public  void login(View view){
        Log.e("aaaaa MainActivity","11111");
        Arouter.gerInstance().jumpActivity("login/login",null);
    }
}