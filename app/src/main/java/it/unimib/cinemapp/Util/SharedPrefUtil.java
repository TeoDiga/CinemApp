package it.unimib.cinemapp.Util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPrefUtil {
    private final Application application;

    public  SharedPrefUtil(Application application){
        this.application=application;
    }
    public void scriviString(String filename, String chiave, String valore){
        SharedPreferences sharedPreferences= application.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(chiave, valore);
        editor.apply();
    }
    public void scriviSetString(String filename, String chiave, Set<String> valori){
        SharedPreferences sharedPreferences= application.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putStringSet(chiave, valori);
        editor.apply();
    }
    public String leggiString(String filename, String chiave){
        SharedPreferences sharedPreferences= application.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sharedPreferences.getString(chiave, null);
    }
    public Set<String> leggiSetString(String filename, String chiave){
        SharedPreferences sharedPreferences= application.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(chiave, null);
    }
}
