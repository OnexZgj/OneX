package com.example.linsa.retrofitdemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestThemeActivity extends AppCompatActivity {

    @InjectView(R.id.btn_att_change)
    Button btnAttChange;

    private SharedPreferences onex;
    private SharedPreferences.Editor editer;
    private boolean day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onex = getSharedPreferences("onex", MODE_PRIVATE);
        editer = onex.edit();
        initTheme();

        setContentView(R.layout.activity_test_theme);
        ButterKnife.inject(this);


    }

    private void initTheme() {
        day = onex.getBoolean("day", false);
        if (day){
            setTheme(R.style.DayTheme);
        }else{
            setTheme(R.style.NightTheme);
        }




    }

    @OnClick(R.id.btn_att_change)
    public void onViewClicked() {


        setTheme(R.style.DayTheme);

        editer.putBoolean("day",!day);
        editer.commit();
        Toast.makeText(TestThemeActivity.this , "true", Toast.LENGTH_SHORT).show();
    }
}
