package com.agima.atizik.alphapedometer.data.managers;



import com.agima.atizik.alphapedometer.AlphaPedometerApp;
import com.agima.atizik.alphapedometer.data.network.AlphaPedometerService;
import com.agima.atizik.alphapedometer.data.network.ResponseToRes;
import com.agima.atizik.alphapedometer.data.storage.PreferenceManager;
import com.agima.atizik.alphapedometer.di.DaggerService;
import com.agima.atizik.alphapedometer.di.datamannager.DaggerDataManagerComponent;
import com.agima.atizik.alphapedometer.di.datamannager.DataManagerComponent;
import com.agima.atizik.alphapedometer.di.datamannager.DataManagerModule;


import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import rx.schedulers.Schedulers;

/**
 * Created by DiNo on 08.03.2018.
 */

public class DataManager {

    @Inject
    public PreferenceManager mPreferenceManager;
    @Inject
    AlphaPedometerService mService;

    public DataManager() {
        DataManagerComponent component = DaggerDataManagerComponent.builder()
                .appComponent(AlphaPedometerApp.getAppComponent())
                .dataManagerModule(new DataManagerModule())
                .build();
        DaggerService.registerComponent(DataManagerComponent.class, component);
        component.inject(this);
    }
    //region =================================== Preference ==========================================

    public void setToken(String token) {
        mPreferenceManager.setToken(token);
    }


    //endregion


    //region =================================== Network ==========================================

    public Observable<Void> postLogin(String login, String password) {
        return mService.postLogin(login,password).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).compose(new ResponseToRes<>());
    }


    //endregion

    //region =================================== DB ==========================================

   /* private void saveToDisk(IEntity object) {
        if (object!=null) {
            switch (object.getEntityEnum()) {
                case PROFILE:
                    UserCache userCache = new UserCache();
                    userCache.replace((UserEntity) object);
                    break;
            }
        }
    }*/


    //endregion
}
