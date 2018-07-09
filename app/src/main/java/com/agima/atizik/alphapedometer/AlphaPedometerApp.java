package com.agima.atizik.alphapedometer;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.agima.atizik.alphapedometer.di.DaggerService;
import com.agima.atizik.alphapedometer.di.app.AppComponent;
import com.agima.atizik.alphapedometer.di.app.AppModule;
import com.agima.atizik.alphapedometer.di.app.DaggerAppComponent;
import com.agima.atizik.alphapedometer.di.root.RootModule;
import com.agima.atizik.alphapedometer.mortar.ScreenScoper;
import com.agima.atizik.alphapedometer.view.DaggerRootActivity_RootComponent;
import com.agima.atizik.alphapedometer.view.RootActivity;

import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;


/**
 * Created by DiNo on 08.03.2018.
 */

public class AlphaPedometerApp extends Application {

    private static AlphaPedometerApp instance;
    private static AppComponent sAppComponent;
    private static RootActivity.RootComponent mRootActivityRootComponent;
    private MortarScope mRootScope;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static AlphaPedometerApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public Object getSystemService(String name) {
        if (TextUtils.equals(name, "appops") || mRootScope == null) {
            return super.getSystemService(name);
        }
        return mRootScope.hasService(name) ? mRootScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        instance = this;
        createAppComponent();
        createRootActivityComponent();
        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");
        MortarScope rootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());
        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(rootActivityScope);
        super.onCreate();
    }

    private void createAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    private RootActivity.RootComponent createRootComponent() {
        return DaggerRootActivity_RootComponent.builder()
                .appComponent(sAppComponent)
                .rootModule(new RootModule())
                .build();
    }

    private void createRootActivityComponent() {
        mRootActivityRootComponent = DaggerRootActivity_RootComponent.builder()
                .appComponent(sAppComponent)
                .rootModule(new RootModule())
                .build();
    }

}
