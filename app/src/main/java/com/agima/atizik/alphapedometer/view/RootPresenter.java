package com.agima.atizik.alphapedometer.view;

import android.content.Intent;

import com.agima.atizik.alphapedometer.view.abstracts.IRootView;

import mortar.Presenter;
import mortar.bundler.BundleService;

/**
 * Created by DiNo on 08.03.2018.
 */

public class RootPresenter extends Presenter<IRootView> {

    public RootView view;
    public RootModel model;


    public RootPresenter(RootView view, RootModel model) {
        this.view = view;
        this.model = model;
    }


    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService(view.getContext());
    }


    @Override
    protected void onExitScope() {
        super.onExitScope();
    }


    public IRootView getRootView() {
        return getView();
    }

}

