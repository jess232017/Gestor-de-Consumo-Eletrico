package com.alksoft.controldeconsumoelectrico.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager {

    ///Llaves
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String PROFILE_EXIST = "profile_exist";
    public static final String DEPARTMENT = "department";
    public static final String MUNICIPIO = "municipio";


    ///
    private static PrefManager sInstance;
    private final SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private PrefManager(final Context context){
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PrefManager getInstance(final Context context) {
        if (sInstance == null)
            sInstance = new PrefManager(context.getApplicationContext());
        return sInstance;
    }

    public final boolean getProfileExist() {
        return mPref.getBoolean(PROFILE_EXIST,false);
    }

    public void setProfileExist(Boolean profileExist){
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(PROFILE_EXIST, profileExist);
        editor.apply();
    }

    public final String getDepartment() {
        return mPref.getString(DEPARTMENT, "");
    }

    public final String getMunicipio() {
        return mPref.getString(MUNICIPIO, "");
    }

    public void setDepartment(String department){
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putString(DEPARTMENT, department);
        editor.apply();
    }

    public void setMunicipio(String municipio){
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putString(MUNICIPIO, municipio);
        editor.apply();
    }
}