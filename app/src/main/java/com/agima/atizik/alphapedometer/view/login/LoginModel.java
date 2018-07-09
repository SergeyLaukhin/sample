package com.agima.atizik.alphapedometer.view.login;

import com.agima.atizik.alphapedometer.view.abstracts.AbstractModel;

import rx.Observable;

/**
 * Created by DiNo on 08.03.2018.
 */

public class LoginModel extends AbstractModel {
    public Observable<Void> login(String login, String password) {
        return dataManager.postLogin(login, password);
    }

    void setToken(String token) {
        dataManager.setToken(token);
    }

}
