package com.example.chandra.coldstone;

import java.util.LinkedHashSet;

/**
 * Created by Raghuveer on 3/28/2016.
 */
public class ToppingsUtility {

    public static String convertSetToString(LinkedHashSet<String> set){
        int length = set.size();
        StringBuilder sb = new StringBuilder();
        for(String item:set){
            length--;
            sb.append(item);
            if(length!=0){
                sb.append(",");
            }
        }
        return sb.toString();
    }


    public static LinkedHashSet<String> convertStringToSet(String input){
        String[] array = input.split(",");
        LinkedHashSet<String> list = new LinkedHashSet<>();
        for(int i=0;i<array.length;i++){
            list.add(array[i]);
        }
        return list;
    }
}
