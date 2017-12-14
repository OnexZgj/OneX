package com.example.linsa.retrofitdemo.activity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.util.Common;
import com.example.linsa.retrofitdemo.weidget.MyCanvasView;
import com.example.linsa.retrofitdemo.weidget.ZoomView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Canvas绘制图形的Activity
 * create by OneX on 2017/12/6
 */
public class CanvasContentActivity extends AppCompatActivity {

    private int dy = 0;
    private int dx = 0;

    @InjectView(R.id.mcv_acc_canvas)
    MyCanvasView mcvAccCanvas;
    @InjectView(R.id.btn_acc_add_x)
    Button btnAccAddX;
    @InjectView(R.id.btn_acc_add_y)
    Button btnAccAddY;
    @InjectView(R.id.btn_acc_lower_x)
    Button btnAccLowerX;
    @InjectView(R.id.btn_acc_lower_y)
    Button btnAccLowerY;
    @InjectView(R.id.fab_acc_anim)
    FloatingActionButton fabAccAnim;
    @InjectView(R.id.zv_acc_scal)
    ZoomView zoomView;
    private Paint paint;

    public boolean isClose;
    /**
     * 要绘制XY的类型
     */
    private int mType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_content);
        ButterKnife.inject(this);
        mType = getIntent().getIntExtra("mType", Common.TYPE_XY);


//        mcvAccCanvas.setDxAndDy(mType,dx, dy);
//        zoomView.setDxAndDy(false,fabAccAnim.getX(),fabAccAnim.getY());
//        zoomView.start();
//        zoomView.setInterpolator(new LinearInterpolator());


        fabAccAnim.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                isClose=!isClose;

                Log.i("TAG", "onClick: "+isClose);
                zoomView.setDxAndDy(isClose,fabAccAnim.getX()+fabAccAnim.getWidth()/2,fabAccAnim.getY()+fabAccAnim.getHeight()/2);
                zoomView.start();
            }
        });
    }

    private void drawCircle() {

        Canvas canvas=new Canvas();


    }


    @OnClick({R.id.btn_acc_add_x, R.id.btn_acc_add_y, R.id.btn_acc_lower_x, R.id.btn_acc_lower_y})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_acc_add_x:
                dx += 10;
                break;
            case R.id.btn_acc_add_y:
                dy += 10;
                break;
            case R.id.btn_acc_lower_x:
                dx -= 10;
                break;
            case R.id.btn_acc_lower_y:
                dy -= 10;
                break;
        }
        mcvAccCanvas.setDxAndDy(mType,dx, dy);

    }
}
