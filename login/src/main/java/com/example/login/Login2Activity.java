package com.example.login;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.annotation.BindPath;
import com.example.basic.BaseActivity;
import com.example.router.Arouter;



@BindPath("login/login2")
public class Login2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_b);
    }

    public  void  toone(View view){
        Arouter.gerInstance().jumpActivity("login/login",null);
    }


}