package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.linsa.retrofitdemo.R;
import com.zph.glpanorama.GLPanorama;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OpeGlActivity extends AppCompatActivity {

    @InjectView(R.id.gl_aog_test)
    GLPanorama glAogTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ope_gl);
        ButterKnife.inject(this);



        glAogTest.setGLPanorama(R.drawable.taylor);
    }
}
