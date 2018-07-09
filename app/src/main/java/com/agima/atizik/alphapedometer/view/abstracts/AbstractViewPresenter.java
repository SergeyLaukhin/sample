package com.agima.atizik.alphapedometer.view.abstracts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.agima.atizik.alphapedometer.utils.Lg;
import com.agima.atizik.alphapedometer.view.RootPresenter;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.ViewPresenter;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by DiNo on 08.03.2018.
 */

public abstract class AbstractViewPresenter<V extends AbstractView, M extends AbstractModel> extends ViewPresenter<V> {

    @Inject
    public M model;
    @Inject
    public RootPresenter rootPresenter;
    protected CompositeSubscription mCompViewSubs;

    public AbstractViewPresenter() {
        super();
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        initDagger(scope);
    }

    public IRootView getRootView() {
        return rootPresenter.getRootView();
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
      /*  initActionBar();*/
        Lg.e("state","onLoad: ");
        getView().init();
        mCompViewSubs = new CompositeSubscription();
    }

    @Override
    protected void onExitScope() {
        Lg.e("debug_position", "onExitScope");
        super.onExitScope();
    }

   /* public void showTabInPosition(@RootActivity.BottomBarPosition int position) {
        if (rootPresenter.getRootView() != null) {
            rootPresenter.getRootView().showTabInPosition(position);
        }
    }*/

    @Override
    public void dropView(V view) {
        super.dropView(view);
        if (mCompViewSubs.hasSubscriptions()) {
            mCompViewSubs.unsubscribe();
        }
    }

    protected Context getRootContext() {
        Context context = null;
        if (rootPresenter.getRootView() != null) {
            context = rootPresenter.getRootView().getContext();
        }
        return context;
    }

    protected abstract void initDagger(MortarScope scope);

   /* protected abstract void initActionBar();*/


}
