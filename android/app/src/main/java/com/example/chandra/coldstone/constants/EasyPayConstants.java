package com.example.chandra.coldstone.constants;

/**
 * Created by chandra on 2/9/2016.
 */
public class EasyPayConstants {

    public static final String BASE_TITLE="  EasyPay -";
    public static final String baseurl = "http://192.168.1.107:8080/ColdStone/rest/user";

    //Intent Constants
    public static final String ANDROID_DEVICE_ID_KEY="android_id";
    public static final String INTENT_BILL_KEY="Bill";
    public static final String INTENT_SELECTED_TOPPINGS="SelectedToppings";
    public static final String INTENT_PRICE="Price";

    //RestCall Constants
    public static final String METHOD_GET="GET";
    public static final String METHOD_POST="POST";
    public static final String PARAMETER_DEVICE="device";
    public static final String PARAMETER_USERNAME="username";
    public static final String PARAMETER_PASSWORD="password";

    //Function Constants
    public static final String FUNC_GET_BILL="getBill";
    public static final String FUNC_STATUS_UPDATE="statusupdate";
    public static final String FUNC_HISTORY="history";
    public static final String FUNC_SAVE_TOPPINGS="savetoppings";
    public static final String FUNC_LOGOUT="logout";
    public static final String FUNC_GET_TOPPINGS="gettoppings";
    public static final String FUNC_LOGIN="login";
    public static final String FUNC_SIGNUP="signup";
    public static final String FUNC_SESSION="session";


    //Error Messages
    public static final String ERROR_ALL_FIELDS="Enter all the fields";
    public static final String ERROR_PASSWORDS_MATCHING="Entered passwords dont match";
}