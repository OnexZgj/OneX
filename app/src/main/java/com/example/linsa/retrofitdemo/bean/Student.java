package com.example.linsa.retrofitdemo.bean;

/**
 * Created by Linsa on 2017/8/3:15:24.
 * des:
 */

public class Student {

    public String name;
    public int age;
    public String sex;

    public Student(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
