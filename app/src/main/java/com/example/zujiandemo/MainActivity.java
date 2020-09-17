package com.example.zujiandemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.annotation.BindPath;
import com.example.common.LiveDataBus;
import com.example.router.Arouter;


@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    private MutableLiveData<Integer> objectMutableLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public  void login(View view){
//       粘性事件 是不合理 的  只能接收到 观察者 注册之后 发送的消息
        LiveDataBus.getInstance().with("code",Integer.class).postValue(123456);
        Arouter.gerInstance().jumpActivity("login/login",null);

       /* objectMutableLiveData.observe(MainActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e("aaaaa MainActivity",integer+"");
            }
        });*/
    }
}