package com.unity3d.player;

import android.os.*;

public final class k
{
    static final boolean a;
    static final boolean b;
    static final f c;
    static final e d;
    
    static {
        a = (Build.VERSION.SDK_INT >= 11);
        b = (Build.VERSION.SDK_INT >= 12);
        c = (k.a ? new d() : null);
        d = (k.b ? new c() : null);
    }
}
