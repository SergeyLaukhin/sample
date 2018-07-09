package com.agima.atizik.alphapedometer.view.abstracts;

import android.support.annotation.Nullable;

/**
 * Created by DiNo on 08.03.2018.
 */

public abstract class AbstractPresenter<T extends IView> {

    private T mView;

    public void takeView(T view) {
        mView = view;
    }

    public void dropView() {
        mView = null;
    }

    public abstract void initView();

    @Nullable
    public T getView() {
        return mView;
    }

}
