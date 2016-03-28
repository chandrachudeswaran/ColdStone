package com.example.chandra.coldstone;

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
            bill.setDate(obj.getString(EasyPayConstants.BILL_DATE));
            bill.setPrice(obj.getDouble(EasyPayConstants.BILL_PRICE));
            bill.setStatus(obj.getString(EasyPayConstants.BILL_STATUS));
            bill.setWeight(obj.getString(EasyPayConstants.BILL_WEIGHT));
            bill.setTime(obj.getString(EasyPayConstants.BILL_TIME));
            bill.setId(obj.getInt(EasyPayConstants.ID));
            if (!obj.isNull(EasyPayConstants.BILL_TOPPINGS)) {
                String toppings = obj.getString(EasyPayConstants.BILL_TOPPINGS);
                bill.setSelectedToppings(ToppingsUtility.convertStringToSet(toppings));
                bill.setToppingsPrice(obj.getDouble(EasyPayConstants.BILL_TOPPINGS_RATE));
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
                toppings.setId(array.getJSONObject(i).getInt(EasyPayConstants.ID));
                toppings.setName(array.getJSONObject(i).getString(EasyPayConstants.TOPPINGS_NAME));
                toppings.setPrice((float) array.getJSONObject(i).getDouble(EasyPayConstants.TOPPINGS_PRICE));
                toppings.setUrl(array.getJSONObject(i).getString(EasyPayConstants.TOPPINGS_URL));
                list.add(toppings);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
