package com.example.chandra.coldstone.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.example.chandra.coldstone.utility.ActivityUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by chandra on 4/3/2016.
 */
public class CallRest extends AsyncTask<RequestParams, Void, String> {

    TransferToActivity transferToActivity;
    Context context;
    String function;
    ProgressDialog dialog;

    public interface TransferToActivity{
        void doAction(String output,String function);
    }

    public CallRest(Context context,String function){
        this.context=context;
        this.function=function;
        this.transferToActivity = (TransferToActivity)context;
    }


    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Requesting..");
        dialog.show();
    }

    @Override
    protected String doInBackground(RequestParams... params) {
        BufferedReader reader = null;
        String line = "";
        String status = "";
        try {
            HttpURLConnection con = params[0].getConnection();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            if ((line = reader.readLine()) != null) {
                status = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    protected void onPostExecute(String output) {
        ActivityUtility.Helper.writeDebugLog(output);
        dialog.dismiss();
        transferToActivity.doAction(output, CallRest.this.function);
    }
}
