package com.example.chandra.coldstone.database;

import android.content.Context;

/**
 * Created by chandra on 4/3/2016.
 */
public class CallRest {

    TransferToActivity transferToActivity;
    Context context;
    String function;

    public interface TransferToActivity{
        public void doAction(String output,String function);
    }

    public CallRest(Context context,String function){
        this.context=context;
        this.function=function;

        this.transferToActivity = (TransferToActivity)context;
        transferToActivity.doAction(null,"Hello");
    }
}
