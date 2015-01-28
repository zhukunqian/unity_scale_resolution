package com.unity3d.player;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.view.View;

public final class d implements f
{
    private static final SurfaceTexture a;
    private volatile boolean b;
    
    @Override
    public final boolean a(final Camera camera) {
        try {
            camera.setPreviewTexture(d.a);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public final void a(final View view, final boolean b) {
        this.b = b;
        view.setSystemUiVisibility(this.b ? (view.getSystemUiVisibility() | 0x1) : (view.getSystemUiVisibility() & 0xFFFFFFFE));
    }
    
    @Override
    public final boolean a() {
        return this.b;
    }
    
    @Override
    public final void a(final View view) {
        view.setOnSystemUiVisibilityChangeListener((View.OnSystemUiVisibilityChangeListener)new View.OnSystemUiVisibilityChangeListener() {
            public final void onSystemUiVisibilityChange(final int n) {
                d.a(d.this, view);
            }
        });
    }
    
    @Override
    public final void b(final View view) {
        if (this.b) {
            this.a(view, false);
            this.b = true;
            this.a(view, 500);
        }
    }
    
    private void a(final View view, final int n) {
        final Handler handler;
        if ((handler = view.getHandler()) == null) {
            this.a(view, this.b);
            return;
        }
        handler.postDelayed((Runnable)new Runnable() {
            @Override
            public final void run() {
                d.this.a(view, d.this.b);
            }
        }, (long)n);
    }
    
    static /* synthetic */ void a(final d d, final View view) {
        d.a(view, 1000);
    }
    
    static {
        a = new SurfaceTexture(-1);
    }
}
