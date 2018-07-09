package com.agima.atizik.alphapedometer.utils;

import android.os.Build;
import android.util.Log;

import com.agima.atizik.alphapedometer.BuildConfig;

/**
 * Created by DiNo on 28.03.2018.
 */

public class Lg {

    private static final String PREFIX = "AlphaPedometer ";
    public static final int LOGCAT_BUFFER_SIZE = 3000;

    private static void printLog(int priority, String tag, String text) {
        if (shouldLog()) {
            if (text.length() > LOGCAT_BUFFER_SIZE) {
                String textLog = text;
                while (textLog.length() > LOGCAT_BUFFER_SIZE) {
                    String textLogFinal = textLog.substring(0, LOGCAT_BUFFER_SIZE);
                    textLog = textLog.substring(LOGCAT_BUFFER_SIZE);
                    Log.println(priority, PREFIX + tag, textLogFinal);
                }
                Log.println(priority, PREFIX + tag, textLog);
            } else {
                Log.println(priority, PREFIX + tag, text);
            }
        }
    }

    private static boolean shouldLog() {
        return BuildConfig.DEBUG;
    }

    public static void v(String tag, String text) {
        printLog(Log.VERBOSE, tag, text);
    }

    public static void w(String tag, String text) {
        printLog(Log.WARN, tag, text);
    }

    public static void d(String tag, String text) {
        printLog(Log.DEBUG, tag, text);
    }

    public static void a(String tag, String text) {
        printLog(Log.ASSERT, tag, text);
    }

    public static void e(String tag, String text) {
        printLog(Log.ERROR, tag, text);
    }

    public static void i(String tag, String text) {
        printLog(Log.INFO, tag, text);
    }
}
