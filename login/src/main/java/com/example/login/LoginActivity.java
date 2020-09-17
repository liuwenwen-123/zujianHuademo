package com.example.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.annotation.BindPath;
import com.example.basic.BaseActivity;
import com.example.common.LiveDataBus;
import com.example.router.Arouter;


@BindPath("login/login")
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean isApplicayion = getIsApplicayion();

        LiveDataBus.getInstance().with("code",Integer.class).observe(
                LoginActivity.this,true, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e("LoginActivity   Integer", integer + "---");
            }
        });

    }

    public void toMember(View view) {
        LiveDataBus.getInstance().with("code",Integer.class).postValue(789);
      /*  Arouter.gerInstance().jumpActivity("member/membe", null);*/
    }

    public void gotwo(View view) {
        Arouter.gerInstance().jumpActivity(
                "login/login2", null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            String string = extras.getString("111");
            Log.e("aaaaa",string);
        }
    }
}
