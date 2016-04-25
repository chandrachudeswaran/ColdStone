package com.example.chandra.coldstone;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.database.RequestParams;
import com.example.chandra.coldstone.database.RestCall;
import com.example.chandra.coldstone.utility.ActivityUtility;
import android.support.v7.widget.Toolbar;

import java.util.logging.Logger;

public class SignupActivity extends AppCompatActivity implements RestCall.SignUpFunctionCall{

    EditText username;
    EditText password;
    EditText confirmPassword;
    private Toolbar mToolbar;
    String android_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("EasyPay - Signup");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ice_cream_icon);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmpassword);

        android_id=getIntent().getExtras().getString(EasyPayConstants.ANDROID_DEVICE_ID_KEY);

    }


    public void doSignup(View view) {
        if (validateInput()) {
            RequestParams requestParams = new RequestParams(EasyPayConstants.baseurl, "POST");
            requestParams.setUrl(EasyPayConstants.FUNC_SIGNUP);
            requestParams.addParams(EasyPayConstants.PARAMETER_USERNAME, username.getText().toString());
            requestParams.addParams(EasyPayConstants.PARAMETER_PASSWORD, password.getText().toString());
            requestParams.addParams(EasyPayConstants.PARAMETER_DEVICE, android_id);
            new RestCall(SignupActivity.this,EasyPayConstants.FUNC_SIGNUP).execute(requestParams);
        }
    }


    public boolean validateInput(){
        if(username.getText().length()==0|| password.getText().length()==0|| confirmPassword.getText().length()==0){
            ActivityUtility.Helper.makeToast(getApplicationContext(), EasyPayConstants.ERROR_ALL_FIELDS);
            return false;
        }else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            ActivityUtility.Helper.makeToast(getApplicationContext(),EasyPayConstants.ERROR_PASSWORDS_MATCHING);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void doActionOnSignUp(String output) {
        Log.d("output",output);
        int status = Integer.valueOf(output);
        if(status!=0){
            showHome(username.getText().toString());
        }else{
            ActivityUtility.Helper.makeToast(SignupActivity.this,"Username already exist");
        }
    }


    public void showHome(String username){
        Intent intent = new Intent(SignupActivity.this,HomeActivity.class);
        intent.putExtra(EasyPayConstants.PARAMETER_USERNAME,username);
        intent.putExtra(EasyPayConstants.ANDROID_DEVICE_ID_KEY,android_id);
        startActivity(intent);
        finish();
    }
}
