package com.agima.atizik.alphapedometer.mortar;

import android.support.annotation.Nullable;

import com.agima.atizik.alphapedometer.di.DaggerService;
import com.agima.atizik.alphapedometer.flow.AbstractScreen;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mortar.MortarScope;


/**
 * Created by DiNo on 08.03.2018.
 */

public class ScreenScoper {

    private static Map<String, MortarScope> scopeMap = new HashMap<>();

    public static MortarScope getScreenScope(AbstractScreen screen) {
        if (!scopeMap.containsKey(screen.getScopeName())) {
            return createScreenScope(screen);
        } else {
            return scopeMap.get(screen.getScopeName());
        }
    }

    public static void registerScope(MortarScope scope) {
        scopeMap.put(scope.getName(), scope);
    }

    private static void cleanScopeMap() {
        Iterator<Map.Entry<String, MortarScope>> iterator = scopeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MortarScope> entry = iterator.next();
            if (entry.getValue().isDestroyed()) {
                iterator.remove();
            }
        }
    }

    public static void destroyScreenScope(String scopeName) {
        MortarScope mortarScope = scopeMap.get(scopeName);
        if (mortarScope != null) {
            mortarScope.destroy();
        }
        cleanScopeMap();
    }

    @Nullable
    private static String getParentScopeName(AbstractScreen screen) {
        try {
            String genericName = ((Class) ((ParameterizedType) screen.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getName();
            String parentScopeName = genericName;
            if (parentScopeName.contains("$")) {
                parentScopeName = parentScopeName.substring(0, genericName.indexOf("$"));
            }
            return parentScopeName;

        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static MortarScope createScreenScope(AbstractScreen screen) {
        String parentScreen = getParentScopeName(screen);
        MortarScope parentScope = scopeMap.get(parentScreen);
        Object component = screen.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME));
        MortarScope newScope = parentScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, component)
                .build(screen.getScopeName());
        registerScope(newScope);
        return newScope;
    }
}

