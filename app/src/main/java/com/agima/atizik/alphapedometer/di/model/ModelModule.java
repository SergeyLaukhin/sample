package com.agima.atizik.alphapedometer.di.model;

import com.agima.atizik.alphapedometer.data.managers.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DiNo on 08.03.2018.
 */

@Module
public class ModelModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }

}
