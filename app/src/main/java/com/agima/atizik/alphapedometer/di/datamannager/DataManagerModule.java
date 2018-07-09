package com.agima.atizik.alphapedometer.di.datamannager;

import com.agima.atizik.alphapedometer.data.network.AlphaPedometerService;
import com.agima.atizik.alphapedometer.data.storage.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DiNo on 08.03.2018.
 */

@Module
public class DataManagerModule {

    public DataManagerModule() {
    }

    @Provides
    @Singleton
    AlphaPedometerService provideAlphaPedometerService() {
        return AlphaPedometerService.Factory.makeService();
    }

    @Provides
    @Singleton
    PreferenceManager providePreferenceManager() {
        return PreferenceManager.getInstance();
    }




}
