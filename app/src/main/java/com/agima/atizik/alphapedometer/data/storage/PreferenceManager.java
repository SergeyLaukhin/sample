package com.agima.atizik.alphapedometer.data.storage;



/**
 * Created by DiNo on 08.03.2018.
 */

public class PreferenceManager extends AbstractPreferenceManager {

    PreferenceManager() {
        super();
    }

    public void setToken(String token) {
        putString(PreferenceTAG.TOKEN_TAG,token);
    }

    public String getToken() {
        return getString(PreferenceTAG.TOKEN_TAG,null);
    }

}
