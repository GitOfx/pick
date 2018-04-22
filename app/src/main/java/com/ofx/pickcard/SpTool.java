package com.ofx.pickcard;

import android.content.Context;
import android.content.SharedPreferences;

public class SpTool {

    public static String dig = "dig";
    public static String science = "science";
    public static String food = "food";
    public static String water = "water";
    public static String current_card = "current_card";
    public static String card_num = "_card_num";
    public static String pick_card_num = "pick_card_num";
    public static String time = "time";
    public static String time_day = "time_day";

    private SpTool() {

    }

    private static SpTool spTool;
    private SharedPreferences sharedPreferences;

    public static SpTool getSp(Context context) {
        if (spTool == null) {
            spTool = new SpTool();
            spTool.sharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
//            spTool.setTime();
        }

        return spTool;
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        int value = sharedPreferences.getInt(key, 0);
        return value;
    }

    public long getLong(String key) {
        long value = sharedPreferences.getLong(key, 0L);
        return value;
    }

    public int getTime() {
        long value = sharedPreferences.getLong(time, 0);
        long timegap = System.currentTimeMillis()-value;
        return (int) (timegap/(1000*60*60*24));
    }

    public void setTime(long addtime) {
        if (getTime() == 0){
            putLong(time,System.currentTimeMillis());
        }else {
            putLong(time,getLong(time)+addtime);
        }

    }

    public void addTime() {
        setTime(1000*60*60*24);
    }

    public void addInt(String key,int valuen) {
        int value = sharedPreferences.getInt(key, 0);
        putInt(key,value+valuen);
    }
    public void minusInt(String key,int valuen) {
        int value = sharedPreferences.getInt(key, 0);
        putInt(key,value-valuen);
    }

    public String getString(String key) {
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public int getCardNum(String name) {
        int value = sharedPreferences.getInt(name+card_num, 0);
        GameActivity.log("getCardNum name "+name +" value "+value);
        return value;
    }

    public void addCardNum(String name) {
        GameActivity.log("add name "+name );
        putInt(name+card_num,getCardNum(name)+1);
    }

    public void minusCardNum(String name) {
        GameActivity.log("minus name "+name );
        putInt(name+card_num,getCardNum(name)-1);
    }

    public void clear() {
        sharedPreferences.edit().clear().commit();
    }
}
