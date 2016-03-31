package com.example.chandra.coldstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.example.chandra.coldstone.adapters.GridAdapter;
import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.database.RequestParams;
import com.example.chandra.coldstone.database.RestCall;
import com.example.chandra.coldstone.dto.Toppings;
import com.example.chandra.coldstone.utility.ActivityUtility;
import com.example.chandra.coldstone.utility.ParseUtility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ToppingsActivity extends AppCompatActivity implements RestCall.ToppingsFunctionCall {

    Toolbar mToolbar;
    GridView gridView;
    List<Toppings> list;
    GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toppings);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        setTitle(EasyPayConstants.BASE_TITLE + "Select Toppings");
        gridView = (GridView) findViewById(R.id.gridviewid);
        getToppingsList();
    }


    public void doCheckout(View view) {
       onBackPressed();
    }


    public void getToppingsList(){
        RequestParams params = new RequestParams(EasyPayConstants.baseurl,EasyPayConstants.METHOD_GET);
        params.setUrl(EasyPayConstants.FUNC_GET_TOPPINGS);
        new RestCall(ToppingsActivity.this,EasyPayConstants.FUNC_GET_TOPPINGS).execute(params);
    }


    public void displayToppings(){
        adapter = new GridAdapter(getApplicationContext(), R.layout.gridrow, list);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void doActionOnToppings(String output) {
        ActivityUtility.Helper.writeDebugLog(output);
        try {
            list = ParseUtility.getToppingsListFromRest(new JSONArray(output));
            displayToppings();
        } catch (JSONException e) {
            ActivityUtility.Helper.writeErrorLog(e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        ActivityUtility.Helper.writeDebugLog(adapter.getSelectedToppingList().toString());
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EasyPayConstants.INTENT_SELECTED_TOPPINGS,
                new ArrayList<>(adapter.getSelectedToppingList()));
        intent.putExtra(EasyPayConstants.INTENT_PRICE, adapter.getToppingsRate());
        setResult(RESULT_OK, intent);
        finish();
    }
}
