package com.agima.atizik.alphapedometer.di.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DiNo on 08.03.2018.
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
