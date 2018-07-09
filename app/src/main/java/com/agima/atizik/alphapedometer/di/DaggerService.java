package com.agima.atizik.alphapedometer.di;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by DiNo on 08.03.2018.
 */

public class DaggerService {

    public static String SERVICE_NAME = "DAGGER_SERVICE";
    private static Map<Class, Object> sComponentMap = new HashMap<>();

    public static void registerComponent(Class componentClass, Object daggerComponent) {
        sComponentMap.put(componentClass, daggerComponent);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDaggerComponent(Context context) {

        //noinspection ResourceType
        return (T) context.getSystemService(SERVICE_NAME);
    }

    // FIXME: 16.11.2016 refactor to scope
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Class<T> componentClass) {
        Object component = sComponentMap.get(componentClass);
        return (T) component;
    }
}
