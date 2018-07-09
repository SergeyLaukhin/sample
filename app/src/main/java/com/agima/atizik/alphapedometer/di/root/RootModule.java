package com.agima.atizik.alphapedometer.di.root;

import com.agima.atizik.alphapedometer.view.RootModel;
import com.agima.atizik.alphapedometer.view.RootPresenter;
import com.agima.atizik.alphapedometer.view.RootView;

import dagger.Provides;

/**
 * Created by DiNo on 08.03.2018.
 */

@dagger.Module
public class RootModule {

    /*private ActionBarBuilder actionBarBuilder;*/
    private RootPresenter rootPresenter;

    public RootModule() {
     /*   this.actionBarBuilder = ActionBarBuilder.init();*/
        rootPresenter = new RootPresenter(new RootView(), new RootModel());
    }

    @Provides
    @RootScope
    RootPresenter provideRootPresenter() {
        return rootPresenter;
    }

  /*  @Provides
    @RootScope
    ActionBarBuilder provideActionBarOwner() {
        return actionBarBuilder;
    }*/
}
