package com.agima.atizik.alphapedometer.di.app;

import android.content.Context;

import dagger.Component;

/**
 * Created by DiNo on 08.03.2018.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
