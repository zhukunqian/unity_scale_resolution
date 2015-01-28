package com.unity3d.player;

import android.util.*;

final class h
{
    protected static boolean a;
    
    protected static void Log(final int n, final String s) {
        if (h.a) {
            return;
        }
        if (n == 6) {
            Log.e("Unity", s);
        }
        if (n == 5) {
            Log.w("Unity", s);
        }
        if (n == 4) {
            Log.i("Unity", s);
        }
        if (n == 3) {
            Log.d("Unity", s);
        }
    }
    
    static {
        h.a = false;
    }
}
