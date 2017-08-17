package com.example.linsa.retrofitdemo.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.bean.Student;
import com.example.linsa.retrofitdemo.weidget.CircleView;
import com.example.linsa.retrofitdemo.util.Common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class TestExcelActivity extends AppCompatActivity {


    @InjectView(R.id.btn_ate_add)
    Button btnAteAdd;
    @InjectView(R.id.cv_ate_cv)
    CircleView cvAteCv;
    private Field[] declaredFields;
    private ArrayList<Student> studentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_excel);
        ButterKnife.inject(this);
        cvAteCv.start();

        cvAteCv.setDuration(5000);
        cvAteCv.setStyle(Paint.Style.FILL);
        cvAteCv.setColor(Color.parseColor("#ff0000"));
        cvAteCv.setInterpolator(new LinearOutSlowInInterpolator());
        cvAteCv.start();



        Student student1 = new Student("onex", 15, "男");

        Student student2 = new Student("onex1", 16, "男");

        Student student3 = new Student("onex2", 17, "女");

        studentsList = new ArrayList<>();
        studentsList.add(student1);
        studentsList.add(student2);
        studentsList.add(student3);

        declaredFields = student1.getClass().getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i].getName());
            System.out.println(declaredFields[i].getType().toString());
        }

        btnAteAdd = (Button) findViewById(R.id.btn_ate_add);
        btnAteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creatExcel();

            }
        });
    }


    private void creatExcel() {
        try {
            // 打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File(Common.ROOT_PATH + "/test10.xls"));
            // 生成名为“第一张工作表”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一张工作表", 0);
            // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
            // 以及单元格内容为baby

            //创建表头
            for (int i = 0; i < declaredFields.length; i++) {
                Label lable = new Label(i, 0, declaredFields[i].getName());
                sheet.addCell(lable);
            }

            //创建数据

            for (int i = 0; i < studentsList.size(); i++) {
                int row = i + 1;
                for (int j = 0; j < declaredFields.length; j++) {
                    if (declaredFields[j].getType().toString().equals("class java.lang.String")) {
                        Label lable = new Label(j, row, ((String) declaredFields[j].get(studentsList.get(i))));
                        sheet.addCell(lable);
                    } else {
                        Number number = new Number(j, row, ((Integer) declaredFields[j].get(studentsList.get(i))));
                        sheet.addCell(number);
                    }
                }
            }


//            Label label = new Label(0, 0, "baby");
            // 将定义好的单元格添加到工作表中

//            sheet.addCell(label);
            // 生成一个保存数字的单元格，必须使用Number的完整包路径，否则有语法歧义。
            //单元格位置是第二列，第一行，值为123
//            jxl.write.Number number = new jxl.write.Number(1, 0, 123);
//            sheet.addCell(number);
            //写入数据并关闭
            book.write();
            book.close();

        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick({ R.id.cv_ate_cv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_ate_cv:
                break;
        }
    }
}
