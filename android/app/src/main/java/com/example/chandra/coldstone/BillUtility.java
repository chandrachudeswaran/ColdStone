package com.example.chandra.coldstone;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raghuveer on 2/9/2016.
 */
public class BillUtility {

    public static Bill parseBill(String input){
        Bill bill=null;
        try {
            JSONObject obj = new JSONObject(input);
            if(obj.getString("userid").length()>0){
                bill = setJSONString(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bill;
    }

    public static ArrayList<Bill> getHistory(String input){
        ArrayList<Bill> list = new ArrayList<>();
        try {
            JSONArray obj = new JSONArray(input);
            for(int i=0;i<obj.length();i++){
                list.add(setJSONString(obj.getJSONObject(i)));
            }
        } catch (JSONException e) {
            list.add(parseBill(input));
        }
        return list;
    }

    public static Bill setJSONString(JSONObject obj){
        Bill bill = new Bill();
        try {
            bill.setDate(obj.getString("dateinserted"));
            bill.setPrice(obj.getDouble("price"));
            bill.setStatus(obj.getString("status"));
            bill.setWeight(obj.getString("weight"));
            bill.setTime(obj.getString("timeinserted"));
            bill.setId(obj.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bill;
    }
}
