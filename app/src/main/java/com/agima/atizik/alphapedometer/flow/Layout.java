package com.agima.atizik.alphapedometer.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by DiNo on 08.03.2018.
 */

@Retention(RUNTIME)
@Target(TYPE)
public @interface Layout {
    int value();
}
