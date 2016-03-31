package com.example.chandra.coldstone.utility;

import com.example.chandra.coldstone.dto.Bill;
import com.example.chandra.coldstone.dto.Toppings;
import com.example.chandra.coldstone.constants.DatabaseConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghuveer on 2/9/2016.
 */
public class ParseUtility {

    public static Bill parseBill(String input) {
        Bill bill = null;
        try {
            JSONObject obj = new JSONObject(input);
            if (obj.getString("userid").length() > 0) {
                bill = setJSONString(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bill;
    }

    public static ArrayList<Bill> getHistory(String input) {
        ArrayList<Bill> list = new ArrayList<>();
        try {
            JSONArray obj = new JSONArray(input);
            for (int i = 0; i < obj.length(); i++) {
                list.add(setJSONString(obj.getJSONObject(i)));
            }
        } catch (JSONException e) {
            list.add(parseBill(input));
        }
        return list;
    }

    public static Bill setJSONString(JSONObject obj) {
        Bill bill = new Bill();
        try {
            bill.setDate(obj.getString(DatabaseConstants.BILL_DATE));
            bill.setPrice(obj.getDouble(DatabaseConstants.BILL_PRICE));
            bill.setStatus(obj.getString(DatabaseConstants.BILL_STATUS));
            bill.setWeight(obj.getString(DatabaseConstants.BILL_WEIGHT));
            bill.setTime(obj.getString(DatabaseConstants.BILL_TIME));
            bill.setId(obj.getInt(DatabaseConstants.ID));
            if (!obj.isNull(DatabaseConstants.BILL_TOPPINGS)) {
                String toppings = obj.getString(DatabaseConstants.BILL_TOPPINGS);
                bill.setSelectedToppings(ToppingsUtility.convertStringToSet(toppings));
                bill.setToppingsPrice(obj.getDouble(DatabaseConstants.BILL_TOPPINGS_RATE));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bill;
    }


    public static List<Toppings> getToppingsListFromRest(JSONArray array) {
        List<Toppings> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                Toppings toppings = new Toppings();
                toppings.setId(array.getJSONObject(i).getInt(DatabaseConstants.ID));
                toppings.setName(array.getJSONObject(i).getString(DatabaseConstants.TOPPINGS_NAME));
                toppings.setPrice((float) array.getJSONObject(i).getDouble(DatabaseConstants.TOPPINGS_PRICE));
                toppings.setUrl(array.getJSONObject(i).getString(DatabaseConstants.TOPPINGS_URL));
                list.add(toppings);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ActivityUtility.Helper.writeDebugLog(list.toString());
        return list;
    }
}
