package com.agima.atizik.alphapedometer.view.abstracts;

import android.content.Context;

import com.agima.atizik.alphapedometer.flow.AbstractScreen;

/**
 * Created by DiNo on 08.03.2018.
 */

public interface IRootView extends IView {

    void showScreen(AbstractScreen screen);

    Context getContext();

}
