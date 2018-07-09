package com.agima.atizik.alphapedometer.di.datamannager;

import com.agima.atizik.alphapedometer.data.managers.DataManager;
import com.agima.atizik.alphapedometer.di.app.AppComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by DiNo on 08.03.2018.
 */

@Component(dependencies = AppComponent.class, modules = DataManagerModule.class)
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
