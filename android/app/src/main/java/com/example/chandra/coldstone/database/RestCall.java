package com.example.chandra.coldstone.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.utility.ActivityUtility;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


/**
 * Created by chandra on 3/30/2016.
 */
public class RestCall extends AsyncTask<RequestParams, Void, String> {

    private Context context;
    ProgressDialog dialog;
    String function;
    MainFunctionCall functionCall;
    SignUpFunctionCall signUpFunctionCall;
    ToppingsFunctionCall toppingsFunctionCall;
    HomeFunctionCall homeFunctionCall;

    public interface MainFunctionCall{
        void doActionOnSession(String output);
        void doActionOnLogin(String output);
    }

    public interface SignUpFunctionCall{
        void doActionOnSignUp(String output);
    }

    public interface ToppingsFunctionCall{
        void doActionOnToppings(String output);
    }

    public interface HomeFunctionCall{
        void submitBillForUser(String output);
        void statusUpdate(String output);
        void doLogout(String output);
        void doActionOnHistory(String output);
    }

    public RestCall(Context context, String function) {
        this.context = context;
        this.function = function;
    }

    public Context getContext(){
        return this.context;
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

        switch(function){
            case EasyPayConstants.FUNC_SESSION:
                functionCall =(MainFunctionCall)getContext();
                functionCall.doActionOnSession(output);
                break;

            case EasyPayConstants.FUNC_LOGIN:
                functionCall=(MainFunctionCall)getContext();
                functionCall.doActionOnLogin(output);
                break;

            case EasyPayConstants.FUNC_SIGNUP:
                signUpFunctionCall =(SignUpFunctionCall)getContext();
                signUpFunctionCall.doActionOnSignUp(output);
                break;

            case EasyPayConstants.FUNC_GET_TOPPINGS:
                toppingsFunctionCall=(ToppingsFunctionCall)getContext();
                toppingsFunctionCall.doActionOnToppings(output);
                break;

            case EasyPayConstants.FUNC_GET_BILL:
                homeFunctionCall=(HomeFunctionCall)getContext();
                homeFunctionCall.submitBillForUser(output);
                break;

            case EasyPayConstants.FUNC_STATUS_UPDATE:
                homeFunctionCall=(HomeFunctionCall)getContext();
                homeFunctionCall.statusUpdate(output);
                break;

            case EasyPayConstants.FUNC_LOGOUT:
                homeFunctionCall=(HomeFunctionCall)getContext();
                homeFunctionCall.doLogout(output);
                break;

            case EasyPayConstants.FUNC_HISTORY:
                homeFunctionCall=(HomeFunctionCall)getContext();
                homeFunctionCall.doActionOnHistory(output);
                break;

        }
    }
}
