package com.example.linsa.retrofitdemo.bean;

/**
 * Created by Linsa on 2017/8/9:11:24.
 * des:测试Hutils的使用
 */

public class People {

    String NAME;
    String SEX;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }


    @Override
    public String toString() {
        return "People{" +
                "NAME='" + NAME + '\'' +
                ", SEX='" + SEX + '\'' +
                '}';
    }
}
