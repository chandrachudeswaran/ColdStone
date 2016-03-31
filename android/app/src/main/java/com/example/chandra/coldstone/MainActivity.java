package com.example.chandra.coldstone;


import android.content.Intent;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.database.RequestParams;
import com.example.chandra.coldstone.database.RestCall;


public class MainActivity extends AppCompatActivity implements RestCall.MainFunctionCall {

    String function;
    String username;
    String android_id;
    Toolbar mToolbar;
    boolean session=false;
    EditText edit_username;
    EditText edit_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(EasyPayConstants.BASE_TITLE + "Login");
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        edit_username = (EditText) findViewById(R.id.username);
        edit_password = (EditText) findViewById(R.id.password);

        android_id = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        checkSessionExists();
    }

    public void checkSessionExists(){
        RequestParams params = new RequestParams(EasyPayConstants.baseurl, EasyPayConstants.METHOD_POST);
        this.function = EasyPayConstants.FUNC_SESSION;
        params.setUrl(this.function);
        params.addParams(EasyPayConstants.PARAMETER_DEVICE, android_id);
        new RestCall(MainActivity.this,EasyPayConstants.FUNC_SESSION).execute(params);
    }

    @Override
    public void doActionOnSession(String output) {
        if (!output.equals("1")) {
            session=true;
            showHome(output);
        }
    }

    @Override
    public void doActionOnLogin(String output) {
        int status = Integer.valueOf(output);
        if(status==0){
            session=true;
            showHome(edit_username.getText().toString());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public boolean validateInput() {
        if (edit_username.getText().length() == 0 || edit_password.getText().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void doLogin(View v){
        if(validateInput()) {
            RequestParams requestParams = new RequestParams(EasyPayConstants.baseurl, EasyPayConstants.METHOD_POST);
            requestParams.setUrl(EasyPayConstants.FUNC_LOGIN);
            requestParams.addParams(EasyPayConstants.PARAMETER_USERNAME, edit_username.getText().toString());
            requestParams.addParams(EasyPayConstants.PARAMETER_PASSWORD, edit_password.getText().toString());
            requestParams.addParams(EasyPayConstants.PARAMETER_DEVICE, android_id);
            new RestCall(MainActivity.this,EasyPayConstants.FUNC_LOGIN).execute(requestParams);
        }
    }

    public void showSignUp(View v){
        Intent intent = new Intent(MainActivity.this,SignupActivity.class);
        intent.putExtra(EasyPayConstants.ANDROID_DEVICE_ID_KEY, android_id);
        startActivity(intent);
    }

    public void showHome(String username){
        this.username=username;
        session=true;
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        intent.putExtra("username", this.username);
        intent.putExtra(EasyPayConstants.ANDROID_DEVICE_ID_KEY,android_id);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras() != null) {
                    if(data.getBooleanExtra("Session",false)){
                        finish();
                    }
                }

            }
        }
    }


}
