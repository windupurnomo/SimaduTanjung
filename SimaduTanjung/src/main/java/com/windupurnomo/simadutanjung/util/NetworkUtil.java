package com.windupurnomo.simadutanjung.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Windu Purnomo on 7/6/14.
 */
public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context)
    {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
