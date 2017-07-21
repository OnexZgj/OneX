package com.example.linsa.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.linsa.retrofitdemo.view.CircleProgressView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.delete_username)
    ImageButton deleteUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.pb_login)
    CircleProgressView pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        pbLogin.setVisibility(View.VISIBLE);
//        pbLogin.spin();  和普通没有效果
//        pbLogin.stopSpinning(); 停止旋转

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
    }
}
