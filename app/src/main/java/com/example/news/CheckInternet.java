package com.example.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Handles checking internet connection.
 */
public class CheckInternet {

    /**
     * Checks to see if the device is connected to the internet.
     * @param context The application environment.
     * @return The network status.
     */
    public static String getNetworkInfo(Context context){
        String status;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            status = "connected";
        } else {
            status = "disconnected";
        }
        return status;
    }
}