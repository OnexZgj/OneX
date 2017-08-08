package com.example.linsa.retrofitdemo.util;

import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by Linsa on 2017/8/9:14:35.
 * des:自动将xml中的属性值封装到Bean中
 */

public class MyUtils {

    /**
     * 从xml中提取出相应的字段
     *
     * @param view
     * @param instanceBean
     */
    public static <T> T fromXmlToBean(View view, T instanceBean) {

        if (instanceBean==null){
            return null;
        }

        //获取所有实体bean中的方法
        Method[] methods = instanceBean.getClass().getMethods();
        for (Method method : methods) {
            //参数是当前java虚拟机中使用默认的语言环境值
            String methodName = method.getName().toUpperCase(Locale.getDefault());

            //假如是以SET方法开头，一般是我们自己自定义的一些方法
            if (methodName.startsWith("SET")) {
                //获取对应Tag的View,根据SET方法获取相应的Tag，找到对应的View
                View childView = view.findViewWithTag(methodName.substring(3));
                if (childView != null && childView instanceof TextView) {
                    String etText = ((TextView) childView).getText().toString();
                    Class<?> aClass = method.getParameterTypes()[0];
                    try {
                        if (aClass.getName().equals("int")) {
                            method.invoke(instanceBean,
                                    Integer.parseInt(etText));
                        } else if (aClass.getName().equals("long")) {
                            method.invoke(instanceBean, Long.parseLong(etText));
                        } else if (aClass == Integer.class) {
                            method.invoke(instanceBean,
                                    (Integer) Integer.parseInt(etText));
                        } else if (aClass == Double.class) {
                            method.invoke(instanceBean,
                                    (Double) Double.parseDouble(etText));
                        } else if (aClass == Float.class) {
                            method.invoke(instanceBean,
                                    (Float) Float.parseFloat(etText));
                        } else if (aClass == Long.class) {
                            method.invoke(instanceBean,
                                    (Long) Long.parseLong(etText));
                        } else if (aClass == String.class) {
                            method.invoke(instanceBean, etText);
                        }else{
                            try {
                                throw new Exception("MyUtils中没有对应的数据类型!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return instanceBean;
    }
}
