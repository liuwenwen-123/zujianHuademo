package com.example.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.annotation.BindPath;
import com.example.router.Arouter;

@BindPath("member/membe")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }

    public  void  toLogin(View view){
        Arouter.gerInstance().jumpActivity("main/main",null);
    }
}
