package com.example.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 声明注解的作用域     ElementType.TYPE   在类上
@Retention(RetentionPolicy.CLASS)  //// 声明注解的生命周期   存在时间
// RetentionPolicy.SOURCE  源码时 （最短期）  RetentionPolicy.CLASS 编译时       RetentionPolicy.RUNTIME  运行行（生命时间最长）
public @interface BindPath {
    String value();
}