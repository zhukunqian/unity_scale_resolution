package com.unity3d.player;

import android.os.*;

final class o implements Thread.UncaughtExceptionHandler
{
    private volatile Thread.UncaughtExceptionHandler a;
    
    final synchronized boolean a() {
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
        if ((defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()) == this) {
            return false;
        }
        this.a = defaultUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
        return true;
    }
    
    @Override
    public final synchronized void uncaughtException(final Thread thread, final Throwable t) {
        try {
            final Error error;
            (error = new Error(String.format("FATAL EXCEPTION [%s]\n", thread.getName()) + String.format("Unity version     : %s\n", "4.3.1f1") + String.format("Device model      : %s %s\n", Build.MANUFACTURER, Build.MODEL) + String.format("Device fingerprint: %s\n", Build.FINGERPRINT))).setStackTrace(new StackTraceElement[0]);
            error.initCause(t);
            this.a.uncaughtException(thread, error);
        }
        catch (Throwable t2) {
            this.a.uncaughtException(thread, t);
        }
    }
}
