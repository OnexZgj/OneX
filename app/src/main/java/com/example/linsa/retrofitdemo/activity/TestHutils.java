package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.bean.People;
import com.example.linsa.retrofitdemo.util.MyUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestHutils extends AppCompatActivity {

    @InjectView(R.id.ll_ath_con)
    LinearLayout llAthCon;
    @InjectView(R.id.tv_ath_name)
    TextView tvAthName;
    @InjectView(R.id.tv_ath_sex)
    TextView tvAthSex;
    @InjectView(R.id.btn_ath_show)
    Button btnAthShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hutils);
        ButterKnife.inject(this);

        People people = new People();
        people = MyUtils.fromXmlToBean(llAthCon, people);

        Toast.makeText(this, people.toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_ath_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ath_show:

                People people=new People();
                people.setNAME("OneX");
                people.setSEX("man");

                MyUtils.formBeanToXml(llAthCon,people);

                break;
        }
    }
}
