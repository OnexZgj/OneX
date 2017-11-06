package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.weidget.SaleProgressView;
import com.example.linsa.retrofitdemo.weidget.SaleView;

import java.text.DecimalFormat;

/**
 * 测试和使用PorterDuffXfermode的混合模式，自定义View的方法用
 */
public class PorterDuffXfermodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff_xfermode);


        final SaleView svApdxSale = (SaleView) findViewById(R.id.sv_apdx_sale);

        final SaleProgressView saleProgressView = (SaleProgressView ) findViewById(R.id.spv);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saleProgressView.setTotalAndCurrentCount(100,i);

                float scale = Float.parseFloat(new DecimalFormat("0.00").format((float) i / 100));
                svApdxSale.setScale(scale);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        crvApdxView.setXfermode();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        getMenuInflater().inflate(R.menu.my_menu,menu);
//
//        menu.findItem(R.id.srcIn).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////                crvApdxView.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//                crvApdxView.invalidate();
//                return true;
//            }
//        });
//        menu.findItem(R.id.srcOut).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////                crvApdxView.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//                crvApdxView.invalidate();
//                return true;
//            }
//        });
//        menu.findItem(R.id.lighten).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////                crvApdxView.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
//                crvApdxView.invalidate();
//                return false;
//            }
//        });
//        menu.findItem(R.id.clear).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////                crvApdxView.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//                crvApdxView.invalidate();
//                return false;
//            }
//        });
//        return true;
//    }
}
