package com.agima.atizik.alphapedometer.view.login;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


import com.agima.atizik.alphapedometer.R;
import com.agima.atizik.alphapedometer.di.DaggerService;
import com.agima.atizik.alphapedometer.flow.AbstractScreen;
import com.agima.atizik.alphapedometer.flow.Layout;
;
import com.agima.atizik.alphapedometer.view.RootActivity;
import com.agima.atizik.alphapedometer.view.abstracts.AbstractViewPresenter;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Provides;

import mortar.MortarScope;
import rx.Subscriber;

/**
 * Created by DiNo on 08.03.2018.
 */

@Layout(R.layout.screen_login)
public class LoginScreen extends AbstractScreen<RootActivity.RootComponent> {



    public LoginScreen() {

    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerLoginScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface LoginScope {
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @LoginScope
    public interface Component {
        void inject(LoginPresenter presenter);

        void inject(LoginView presenter);
    }

    @dagger.Module
    public class Module {

        @Provides
        @LoginScope
        LoginPresenter providePresenter() {
            return new LoginPresenter();
        }

        @Provides
        @LoginScope
        LoginModel provideModel() {
            return new LoginModel();
        }

    }

    class LoginPresenter extends AbstractViewPresenter<LoginView, LoginModel> {



        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
        }


        public void login() {
            if (getView().isValidate()) {
                getView().showProgressDialog();
                model.login(getView().getEmail(), getView().getPassword()).subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void authRes) {
                        model.setToken("test");
                        getView().closeProgressDialog();
                    }
                });
            }
        }

        /*private void goToMainScreen() {
            Flow.get(getView()).replaceTop(new MainScreen(false), Direction.REPLACE);
        }*/


    }

}
