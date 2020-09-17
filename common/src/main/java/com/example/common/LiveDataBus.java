package com.example.common;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class LiveDataBus {
    private static LiveDataBus liveDataBus = new LiveDataBus();
    //     容器  存储所有订阅者的容器
    private Map<String, BusMutableLiveData<Object>> map;

    public LiveDataBus() {
        map = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        return liveDataBus;

    }

    /**
     * 注册订阅这的方法   存和取得 一体额方法
     * with  创建通道
     * type 没使用 但是 决定了 MutableLiveData 以及返回值 是什么类型
     */

    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> type) {
//   判断容器 是否包含了这个key
        if (!map.containsKey(key)) {
            map.put(key, new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) map.get(key);
    }


    public class BusMutableLiveData<T> extends MutableLiveData<T> {
        private boolean isViscosity = false;

        public void observe(@NonNull LifecycleOwner owner, boolean isViscosity, @NonNull Observer<T> observer) {
            this.isViscosity = isViscosity;
            observe(owner, observer);
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);

            try {
                Log.e("aaaa  ", isViscosity + "--");
                if (isViscosity) {
                    Log.e("aaaa22  ", isViscosity + "--");
//                      通过 反射 获取 mversion  在获取 lastversion  将mversion 赋值给  lastversion
                    hook(observer);
                }
            } catch (Exception e) {

            }
        }

        /**
         * hook  技术 实现方法  拦截 onchange方法的 执行
         *
         * @param observer
         */
        private void hook(Observer<? super T> observer) throws Exception {

            Class classLiveData = LiveData.class;

            //找到LiveData中mObservers字段

            Field fieldObservers = classLiveData.getDeclaredField("mObservers");

            fieldObservers.setAccessible(true);

            Object objectObservers = fieldObservers.get(this);

            Class classObservers = objectObservers.getClass();

            //调用mObservers的get方法

            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);

            methodGet.setAccessible(true);

            //调用get方法拿到mObservers中单个的Entry值

            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);

            Object objectWrapper = null;

            if (objectWrapperEntry instanceof Map.Entry) {

//拿到Observer

                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();

            }

            if (objectWrapper == null) {

                throw new NullPointerException("Wrapper can not be bull!");

            }

//拿到Observer的父类（ObserverWrapper）

            Class classObserverWrapper = objectWrapper.getClass().getSuperclass();

            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");

            fieldLastVersion.setAccessible(true);

            //拿到LiveData中的mVersion

            Field fieldVersion = classLiveData.getDeclaredField("mVersion");

            fieldVersion.setAccessible(true);

            Object objectVersion = fieldVersion.get(this);

            //把observer.mLastVersion的值改为mVersion的值

            fieldLastVersion.set(objectWrapper, objectVersion);


        }
    }






    }
