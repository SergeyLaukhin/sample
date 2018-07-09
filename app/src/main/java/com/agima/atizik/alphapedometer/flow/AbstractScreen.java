package com.agima.atizik.alphapedometer.flow;

import android.os.Parcelable;

import com.agima.atizik.alphapedometer.mortar.ScreenScoper;

import flow.ClassKey;


/**
 * Created by DiNo on 08.03.2018.
 */

public abstract class AbstractScreen<T> extends ClassKey {

    public String getScopeName() {
        return getClass().getName();
    }

    public abstract Object createScreenComponent(T parentComponent);

    public void unregisterScope() {
        ScreenScoper.destroyScreenScope(getScopeName());
    }

}
