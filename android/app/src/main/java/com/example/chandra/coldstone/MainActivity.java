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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(mToolbar);

        android_id = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        RequestParams params = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "session";
        params.setUrl(this.function);
        params.addParams("device", android_id);
        new Session().execute(params);


    }

    private class Session extends AsyncTask<RequestParams, Void, String> {
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
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String bill) {
            dialog.dismiss();
            if (!bill.equals("1")) {
                showHome(bill);
            } else {
                showLogin();
            }
        }
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

        new UserDb().execute(requestParams);

    }

    @Override
    public void showSignup() {
        getFragmentManager().beginTransaction().addToBackStack("login")
                .replace(R.id.container, new Signup(), "signup").commit();
    }

    @Override
    public void doSingup(String username, String password) {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "signup";
        requestParams.setUrl(this.function);
        this.username = username;
        requestParams.addParams("username", username);
        requestParams.addParams("password", password);
        requestParams.addParams("device",android_id);

        new UserDb().execute(requestParams);
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

    public void showHome(String username) {
        this.username = username;
        session=true;
        homeFragment = new HomeFragment();
        getFragmentManager().beginTransaction().addToBackStack("login")
                .replace(R.id.container, homeFragment, "home").commit();
    }

    public void showLogin() {
        getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), "login").commit();
    }

    @Override
    public void doCheckBill() {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl, "POST");
        this.function = "getBill";
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);

        new BillDb().execute(requestParams);
    }

    @Override
    public void checkResponse(boolean check) {

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
        new StatusUpdate().execute(requestParams);

    }


    private class StatusUpdate extends AsyncTask<RequestParams, Void, Integer> {


        @Override
        protected Integer doInBackground(RequestParams... params) {
            BufferedReader reader = null;
            String line = "";
            int status = 0;

            try {
                HttpURLConnection con = params[0].getConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                if ((line = reader.readLine()) != null) {

                    status = Integer.parseInt(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return status;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Processing Order");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Integer bill) {
            dialog.dismiss();
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

    private class BillDb extends AsyncTask<RequestParams, Void, String> {

        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            String line = "";
            try {
                HttpURLConnection con = params[0].getConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Fetching Bill");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String bill) {
            dialog.dismiss();
            billinfo = BillUtility.parseBill(bill);
            homeFragment.displayBill(billinfo);
        }
    }

    private class UserDb extends AsyncTask<RequestParams, Void, Integer> {

        @Override
        protected Integer doInBackground(RequestParams... params) {
            BufferedReader reader = null;
            int status = 2;
            String line = "";
            try {
                HttpURLConnection con = params[0].getConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                if ((line = reader.readLine()) != null) {

                    status = Integer.parseInt(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return status;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Contacting");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            dialog.dismiss();

            if (function.equals("signup")) {
                if (integer == 1) {
                    session=true;
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
        }
    }

    @Override
    public void showHistoryFragment() {
        historyFragment = new HistoryFragment();
        getFragmentManager().beginTransaction().addToBackStack("home")
                .replace(R.id.container, historyFragment, "history").commit();
    }

    @Override
    public void getHistoryList() {
        RequestParams params = new RequestParams(ColdStoneConstants.baseurl,"POST");
        this.function="history";
        params.setUrl(this.function);
        params.addParams("username", this.username);
        new History().execute(params);

    }

    private class History extends AsyncTask<RequestParams, Void, String> {

        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            String line = "";
            try {
                HttpURLConnection con = params[0].getConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Getting History Data");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String input) {
            dialog.dismiss();
            ArrayList<Bill> list =BillUtility.getHistory(input);
            displayInList(list);
        }
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
        new StatusUpdate().execute(params);
    }
}
