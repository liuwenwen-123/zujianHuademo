package com.example.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class Arouter {

    private static Arouter Router = new Arouter();
    private Context context;
    //     存放 所有activity 的类对象  --- 路由表
    Map<String, Class<? extends Activity>> map;

    public Arouter() {
        map = new HashMap<>();
    }

    public static Arouter gerInstance() {
        return Router;
    }

    /**
     * 将 类对象 添加到  路由中
     */

    public void addactivity(String key, Class<? extends Activity> activity) {
        if (!TextUtils.isEmpty(key) &&
                activity != null &&
                !map.containsKey(key)) {
            map.put(key, activity);
        }
    }
    public void initRouter(Context context){
        this.context=context;
//        执行所有生成文件里面的putactivity  方法
//        找到这些类
        Log.e("aaaaa","'init");
       List<String> classNames = getClassName("com.lww.utils");
        for(String className:classNames){
//            通过反射获取类对象
            Log.e("aaaaa",className);
            try {

                Class<?> utilsClass = Class.forName(className);
//  判断这个类 是不是 IRouter的 子类
                if(IRouter.class.isAssignableFrom(utilsClass)){
                    IRouter iRouter = (IRouter) utilsClass.newInstance();
                    iRouter.putActity();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *  跳钻atiity
     */
    public  void jumpActivity(String key, Bundle bundle){
        Class<? extends Activity> aClass = map.get(key);
        if(aClass!=null){
            Intent intent = new Intent(context, aClass);
            if(bundle!=null){
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }



    private List<String> getClassName(String packageName){
//         创建 一个clasa 类集合

        DexFile dexFile = null;
        ArrayList<String> classlist=new ArrayList<>();
        try {

//             把当前apk 所有的 存储路径 给 DexFile
            dexFile = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = dexFile.entries();

          while (entries.hasMoreElements()){
              String className = entries.nextElement();
              if(className.contains(packageName)){
                  classlist.add(className);
              }
          }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  classlist;
    }
}

