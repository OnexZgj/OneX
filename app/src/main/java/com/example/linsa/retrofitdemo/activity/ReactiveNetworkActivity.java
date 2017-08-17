package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;


public class ReactiveNetworkActivity extends AppCompatActivity {


    private static final String TAG = "ReactiveNetworkActivity";
    private TextView tvConnectivityStatus;
    private TextView tvInternetStatus;

//
//    private Disposable networkDisposable;
//    private Disposable internetDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive_network);

        tvConnectivityStatus = (TextView) findViewById(R.id.connectivity_status);
        tvInternetStatus = (TextView) findViewById(R.id.internet_status);

    }
//
//    @Override protected void onResume() {
//        super.onResume();
//
//        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(connectivity -> {
//                    Log.d(TAG, connectivity.toString());
//                    final NetworkInfo.State state = connectivity.getState();
//                    final String name = connectivity.getTypeName();
//                    tvConnectivityStatus.setText(String.format("state: %s, typeName: %s", state, name));
//                });
//
//        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(isConnected -> tvInternetStatus.setText(isConnected.toString()));
//    }
//
//    @Override protected void onPause() {
//        super.onPause();
//        safelyDispose(networkDisposable, internetDisposable);
//    }
//
//    private void safelyDispose(Disposable... disposables) {
//        for (Disposable subscription : disposables) {
//            if (subscription != null && !subscription.isDisposed()) {
//                subscription.dispose();
//            }
//        }
//    }
}
