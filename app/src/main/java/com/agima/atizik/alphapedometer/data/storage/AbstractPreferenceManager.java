package com.agima.atizik.alphapedometer.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.agima.atizik.alphapedometer.AlphaPedometerApp;

import org.w3c.dom.Text;

/**
 * Created by DiNo on 08.03.2018.
 */

abstract class AbstractPreferenceManager {

    public static final String TAG = "PreferenceManager";
    static PreferenceManager INSTANCE;
    SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    AbstractPreferenceManager() {
        mPreferences = AlphaPedometerApp.getInstance().getSharedPreferences("alpha_pedometer_app", Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    public static PreferenceManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PreferenceManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PreferenceManager();
                }
            }
        }
        return INSTANCE;
    }

    public void putString(String TAG, String value) {
        editor.putString(TAG, value);
        editor.commit();
    }

    public void putText(String TAG, Text value) {
        editor.putString(TAG, value.getTextContent());
        editor.commit();
    }

    public void putBoolean(String TAG, boolean value) {
        editor.putBoolean(TAG, value);
        editor.commit();
    }

    public void putFloat(String TAG, float value) {
        editor.putFloat(TAG, value);
        editor.commit();
    }

    void putInt(String TAG, int value) {
        editor.putInt(TAG, value);
        editor.commit();
    }

    void putLong(String TAG, long value) {
        editor.putLong(TAG, value);
        editor.commit();
    }


    public String getString(String TAG, String defaultValue) {
        return mPreferences.getString(TAG, defaultValue);
    }

    public boolean getBoolean(String TAG, boolean defaultValue) {
        return mPreferences.getBoolean(TAG, defaultValue);
    }

    public String getString(String TAG) {
        return getString(TAG, "");
    }

}
