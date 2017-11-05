package com.assignment.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * summary: Some common methods that can be used
 */
public class CommonMethods {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
