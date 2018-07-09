package com.agima.atizik.alphapedometer.utils;

/**
 * Created by DiNo on 08.03.2018.
 */

public class NetworkAvailableError extends Throwable/*от Throwable bad practice*/ {
    private String message;

    public NetworkAvailableError() {
        this.message = "Интернет недоступен, повторите попытку позже";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
