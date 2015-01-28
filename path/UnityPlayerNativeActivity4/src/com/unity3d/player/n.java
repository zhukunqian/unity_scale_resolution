package com.unity3d.player;

import android.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;

final class n
{
    public static n a;
    private final FrameLayout b;
    private Set c;
    private View d;
    private View e;
    
    n(final FrameLayout b) {
        this.c = new HashSet();
        this.b = b;
        n.a = this;
    }
    
    public final Context a() {
        return this.b.getContext();
    }
    
    public final void a(final View view) {
        h.Log(3, "surfaces: add hidden");
        this.c.add(view);
        if (this.d != null) {
            this.e(view);
        }
    }
    
    public final void b(final View view) {
        h.Log(3, "surfaces: remove hidden");
        this.c.remove(view);
        if (this.d != null) {
            this.f(view);
        }
    }
    
    public final void c(final View d) {
        h.Log(3, "surfaces: attach glview");
        if (this.d != d) {
            this.d = d;
            this.b.addView(d);
            final Iterator<View> iterator = this.c.iterator();
            while (iterator.hasNext()) {
                this.e(iterator.next());
            }
            if (this.e != null) {
                this.e.setVisibility(4);
            }
        }
        else {
            h.Log(3, "surfaces: ignored attach glview (already attached)");
        }
    }
    
    public final void d(final View view) {
        if (this.d == view) {
            h.Log(3, "surfaces: detach glview");
            final Iterator<View> iterator = this.c.iterator();
            while (iterator.hasNext()) {
                this.f(iterator.next());
            }
            this.b.removeView(view);
            this.d = null;
            if (this.e != null) {
                this.e.setVisibility(0);
            }
        }
        else {
            h.Log(3, "surfaces: ignored detach glview (not attached)");
        }
    }
    
    private void e(final View view) {
        h.Log(3, "surfaces: attach hidden");
        this.b.addView(view, this.b.getChildCount());
    }
    
    private void f(final View view) {
        h.Log(3, "surfaces: detach hidden");
        this.b.removeView(view);
        this.b.requestLayout();
    }
}
