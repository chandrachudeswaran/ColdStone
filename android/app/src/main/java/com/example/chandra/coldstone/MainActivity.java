package com.example.chandra.coldstone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginInterface,Signup.SignupInterface,HomeFragment.CheckBillInterface{


    ProgressDialog dialog;
    String function;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),"login").commit();

    }


    @Override
    public void doLoginCheck(String username, String password) {

        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl,"POST");
        this.function="login";
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);
        requestParams.addParams("password",password);

        new UserDb().execute(requestParams);

    }

    @Override
    public void showSignup() {
        getFragmentManager().beginTransaction().addToBackStack("login")
                .replace(R.id.container, new Signup(), "signup").commit();
    }

    @Override
    public void doSingup(String username, String password) {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl,"POST");
        this.function="signup";
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);
        requestParams.addParams("password",password);

        new UserDb().execute(requestParams);
    }

    public void showHome(){
        getFragmentManager().beginTransaction().addToBackStack("login")
                .replace(R.id.container, new HomeFragment(), "home").commit();
    }

    @Override
    public void doCheckBill(String username) {
        RequestParams requestParams = new RequestParams(ColdStoneConstants.baseurl,"POST");
        this.function ="getBill";
        requestParams.setUrl(this.function);
        requestParams.addParams("username", username);
    }


    private class UserDb extends AsyncTask<RequestParams,Void,Integer>{

        @Override
        protected Integer doInBackground(RequestParams... params) {
            BufferedReader reader = null;
            int status=2;
            String line="";
            try {
                HttpURLConnection con = params[0].getConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                if ((line = reader.readLine()) != null) {
                    Log.d("Demo",line);
                    status = Integer.parseInt(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
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

            if(function.equals("signup")){
                if(integer==1){
                    showHome();
                }else{
                    Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
           else{
                if(integer==0){
                    showHome();
                }else{
                    Toast.makeText(getApplicationContext(), "Login parameters incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }
}
