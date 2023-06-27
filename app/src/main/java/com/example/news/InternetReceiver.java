package com.example.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Handles after receiving network status
 */
public class InternetReceiver extends BroadcastReceiver {
    /**
     * Informs the user if not connected to the internet.
     * @param context The application environment.
     * @param intent Action request.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = CheckInternet.getNetworkInfo(context);
        if (status.equals("disconnected")){
            Toast.makeText(context, "Not connected to the internet",Toast.LENGTH_LONG).show();
        }
    }
}