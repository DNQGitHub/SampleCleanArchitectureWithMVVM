package com.q.sample.cleanarchitecture.utilities;

public final class Log
{
    private static String APP_TAG = "Sample_CleanArchitecture";

    private Log() {
    }

    public static void i(String className, String message, Object... params) {
        String log = createLog(className, message, params);
        android.util.Log.i(String.format("%1$s-%2$s", APP_TAG, "I"), log);
    }

    public static void i(Class classT, String message, Object... params) {
        i(classT.getSimpleName(), message, params);
    }

    public static void i(Object object, String message, Object... params) {
        i(object.getClass(), message, params);
    }

    public static void d(String className, String message, Object... params) {
        String log = createLog(className, message, params);
        android.util.Log.d(String.format("%1$s-%2$s", APP_TAG, "D"), log);
    }

    public static void d(Class classT, String message, Object... params) {
        d(classT.getSimpleName(), message, params);
    }

    public static void e(String className, String message, Object... params) {
        String log = createLog(className, message, params);
        android.util.Log.e(String.format("%1$s-%2$s", APP_TAG, "E"), log);
    }

    public static void e(Class classT, String message, Object... params) {
        e(classT.getSimpleName(), message, params);
    }

    private static String createLog(String className, String message, Object... params) {
        return String.format("%1$s | %2$s", className, String.format(message, params));
    }
}
