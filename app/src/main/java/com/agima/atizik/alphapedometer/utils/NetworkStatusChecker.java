package com.agima.atizik.alphapedometer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.agima.atizik.alphapedometer.AlphaPedometerApp;

import rx.Observable;


/**
 * Created by DiNo on 08.03.2018.
 */

public class NetworkStatusChecker {

    public static Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AlphaPedometerApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static Observable<Boolean> isInternetAvailable() {

        return Observable.just(isNetworkAvailable());
    }

}
