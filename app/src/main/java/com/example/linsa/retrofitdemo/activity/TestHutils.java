package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.bean.People;
import com.example.linsa.retrofitdemo.util.MyUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestHutils extends AppCompatActivity {

    @InjectView(R.id.ll_ath_con)
    LinearLayout llAthCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hutils);
        ButterKnife.inject(this);

        People people = new People();
        people = MyUtils.fromXmlToBean(llAthCon, people);

        Toast.makeText(this, people.toString(), Toast.LENGTH_SHORT).show();
    }
}
