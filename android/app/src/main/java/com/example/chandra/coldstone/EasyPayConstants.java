package com.example.chandra.coldstone;

/**
 * Created by chandra on 2/9/2016.
 */
public class EasyPayConstants {

    public static final String baseurl = "http://192.168.1.127:8080/ColdStone/rest/user";
    public static final String ID="id";

    //BillInfo Table Columns
    public static final String BILL_DATE="dateinserted";
    public static final String BILL_PRICE="price";
    public static final String BILL_STATUS="status";
    public static final String BILL_WEIGHT="weight";
    public static final String BILL_TIME="timeinserted";
    public static final String BILL_TOPPINGS="toppings";
    public static final String BILL_TOPPINGS_RATE="toppingsrate";

    //Toppings Table Columns
    public static final String TOPPINGS_NAME="name";
    public static final String TOPPINGS_PRICE="price";
    public static final String TOPPINGS_URL="url";


    //Function Constants
    public static final String FUNC_GET_BILL="getbill";
    public static final String FUNC_STATUS_UPDATE="statusupdate";
    public static final String FUNC_HISTORY="history";
    public static final String FUNC_SAVE_TOPPINGS="savetoppings";
    public static final String FUNC_LOGOUT="logout";
    public static final String FUNC_GET_TOPPINGS="gettoppings";
    public static final String FUNC_LOGIN="login";
    public static final String FUNC_SIGNUP="signup";
    public static final String FUNC_SESSION="session";
}