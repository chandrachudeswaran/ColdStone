package com.example.chandra.coldstone;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginInterface,
        Signup.SignupInterface, HomeFragment.CheckBillInterface, HistoryFragment.HistoryInterface {

    ProgressDialog dialog;
    String function;
    String username;
    Bill billinfo;
    HomeFragment homeFragment;
    String android_id;
    HistoryFragment historyFragment;
    Toolbar mToolbar;
    boolean session=false;
    boolean signup=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        android_id = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        checkSessionExists();
    }

    public void checkSessionExists(){
        RequestParams params = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "session";
        params.setUrl(this.function);
        params.addParams("device", android_id);
        new CallRest().execute(params);
    }

    public void showLoginFragment() {
        getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), "login").commit();
    }
    @Override
    public void doLoginCheck(String username, String password) {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "login";
        this.username = username;
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);
        requestParams.addParams("password", password);
        requestParams.addParams("device",android_id);
        new CallRest().execute(requestParams);
    }

    @Override
    public void showSignup() {
        getFragmentManager().beginTransaction().addToBackStack("login")
                .replace(R.id.container, new Signup(), "signup").commit();
    }

    @Override
    public void doSignup(String username, String password) {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "signup";
        requestParams.setUrl(this.function);
        this.username = username;
        requestParams.addParams("username", username);
        requestParams.addParams("password", password);
        requestParams.addParams("device", android_id);
        new CallRest().execute(requestParams);
    }


    public void showHome(String username) {
        this.username = username;
        session=true;
        homeFragment = new HomeFragment();
        if(signup){
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().addToBackStack("login")
                    .replace(R.id.container, homeFragment, "home").commit();
        }else {
            Log.d("count", getFragmentManager().getBackStackEntryCount() + "");
            getFragmentManager().beginTransaction().addToBackStack("login")
                    .replace(R.id.container, homeFragment, "home").commit();


            Log.d("count", getFragmentManager().getBackStackEntryCount() + "");
        }
    }

    @Override
    public void getBillForUser() {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "getBill";
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);
        new CallRest().execute(requestParams);
    }

    @Override
    public void doUserActionOnBill(boolean check) {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "statusupdate";
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);
        requestParams.addParams("id",String.valueOf(billinfo.getId()));
        if (check) {
            requestParams.addParams("status", "A");
        } else {
            requestParams.addParams("status", "C");
        }
        new CallRest().execute(requestParams);
    }

    @Override
    public void showHistoryFragment() {
        historyFragment = new HistoryFragment();
        getFragmentManager().beginTransaction().addToBackStack("home")
                .replace(R.id.container, historyFragment, "history").commit();
    }

    @Override
    public void getHistoryBillList() {
        RequestParams params = new RequestParams(ColdStoneConstants.baseurl,"POST");
        this.function="history";
        params.setUrl(this.function);
        params.addParams("username", this.username);
        new CallRest().execute(params);
    }


    public void displayInList(ArrayList<Bill> list){
        historyFragment.displayHistory(list);
    }

    @Override
    public void doLogout() {
        RequestParams params = new RequestParams(ColdStoneConstants.baseurl,"POST");
        this.function="logout";
        params.setUrl(this.function);
        params.addParams("device", android_id);
        params.addParams("username",username);
        new CallRest().execute(params);
    }

    @Override
    public void onBackPressed() {


         if(getFragmentManager().getBackStackEntryCount()>1){
            getFragmentManager().popBackStack();
        }
        else if(getFragmentManager().getBackStackEntryCount()>0 && !session){
            getFragmentManager().popBackStack();
        }else{
            finish();
        }
    }


    private class CallRest extends AsyncTask<RequestParams,Void,String>{
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
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
            Log.d("Async",output);
            dialog.dismiss();
            if(MainActivity.this.function.equals("session")){
                if (!output.equals("1")) {
                    showHome(output);
                } else {
                    showLoginFragment();
                }
            }else if(MainActivity.this.function.equals("login")|| MainActivity.this.function.equals("signup")){
                int integer = Integer.valueOf(output);
                if (function.equals("signup")) {
                    if (integer == 1) {
                        session=true;
                        signup=true;
                        showHome(MainActivity.this.username);
                    } else {
                        Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    if (integer == 0) {
                        session=true;
                        showHome(MainActivity.this.username);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login parameters incorrect", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }else if(MainActivity.this.function.equals("history")){
                ArrayList<Bill> list =BillUtility.getHistory(output);
                displayInList(list);
            }else if(MainActivity.this.function.equals("getBill")){
                billinfo = BillUtility.parseBill(output);
                homeFragment.displayBill(billinfo);
            }else if(MainActivity.this.function.equals("statusupdate")|| MainActivity.this.function.equals("logout")){
                int bill = Integer.valueOf(output);
                if(MainActivity.this.function.equals("logout")){
                    if(bill==1){
                        session=false;
                        onBackPressed();
                    }
                }else {
                    if (bill == 1) {
                        Toast.makeText(getApplicationContext(), "Thank you for purchase", Toast.LENGTH_LONG);
                    } else {
                        Toast.makeText(getApplicationContext(), "Thank you", Toast.LENGTH_LONG);
                    }
                    Bill b = null;
                    homeFragment.displayBill(b);
                }
            }
        }
    }
}
