package com.example.chandra.coldstone.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by chandra on 3/30/2016.
 */
public class ActivityUtility {

    static public class Helper {

        static public void writeDebugLog(String message) {
            Log.d("EasyPaySystem", message);
        }

        static public void writeErrorLog(String message) {
            Log.e("EasyPaySystem", message);
        }

        static public void makeToast(Context context, String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
