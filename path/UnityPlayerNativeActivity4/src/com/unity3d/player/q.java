package com.unity3d.player;

import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;

public final class q extends FrameLayout implements SensorEventListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl
{
    private static boolean a;
    private final UnityPlayer b;
    private final Context c;
    private final SurfaceView d;
    private final SurfaceHolder e;
    private final String f;
    private final int g;
    private final int h;
    private final boolean i;
    private final long j;
    private final long k;
    private final FrameLayout l;
    private final SensorManager m;
    private final Display n;
    private int o;
    private int p;
    private int q;
    private int r;
    private MediaPlayer s;
    private MediaController t;
    private boolean u;
    private boolean v;
    private int w;
    private boolean x;
    private int y;
    private boolean z;
    private static final boolean A;
    
    private static void a(final String s) {
        Log.v("Video", s);
    }
    
    protected q(final UnityPlayer b, final Context c, final String f, final int backgroundColor, final int g, final int h, final boolean i, final long j, final long k) {
        super(c);
        this.u = false;
        this.v = false;
        this.w = 0;
        this.x = false;
        this.y = 0;
        this.b = b;
        this.c = c;
        this.l = this;
        this.d = new SurfaceView(c);
        (this.e = this.d.getHolder()).addCallback((SurfaceHolder.Callback)this);
        this.e.setType(3);
        this.l.setBackgroundColor(backgroundColor);
        this.l.addView((View)this.d);
        this.m = (SensorManager)this.c.getSystemService("sensor");
        this.n = ((WindowManager)this.c.getSystemService("window")).getDefaultDisplay();
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
        this.k = k;
        if (com.unity3d.player.q.a) {
            a("fileName: " + this.f);
        }
        if (com.unity3d.player.q.a) {
            a("backgroundColor: " + backgroundColor);
        }
        if (com.unity3d.player.q.a) {
            a("controlMode: " + this.g);
        }
        if (com.unity3d.player.q.a) {
            a("scalingMode: " + this.h);
        }
        if (com.unity3d.player.q.a) {
            a("isURL: " + this.i);
        }
        if (com.unity3d.player.q.a) {
            a("videoOffset: " + this.j);
        }
        if (com.unity3d.player.q.a) {
            a("videoLength: " + this.k);
        }
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.m.registerListener((SensorEventListener)this, this.m.getDefaultSensor(1), 1);
        this.z = true;
    }
    
    public final void onControllerHide() {
    }
    
    protected final void onPause() {
        if (com.unity3d.player.q.a) {
            a("onPause called");
        }
        this.m.unregisterListener((SensorEventListener)this);
        if (!this.x) {
            this.pause();
            this.x = false;
        }
        if (this.s != null) {
            this.y = this.s.getCurrentPosition();
        }
        this.z = false;
    }
    
    protected final void onResume() {
        if (com.unity3d.player.q.a) {
            a("onResume called");
        }
        if (!this.z) {
            this.m.registerListener((SensorEventListener)this, this.m.getDefaultSensor(1), 1);
            if (!this.x) {
                this.start();
            }
        }
        this.z = true;
    }
    
    protected final void onDestroy() {
        this.onPause();
        this.doCleanUp();
        final UnityPlayer b = this.b;
        UnityPlayer.a(new Runnable() {
            @Override
            public final void run() {
                com.unity3d.player.q.this.b.hideVideoPlayer();
            }
        });
    }
    
    private void a() {
        this.doCleanUp();
        try {
            this.s = new MediaPlayer();
            if (this.i) {
                this.s.setDataSource(this.c, Uri.parse(this.f));
            }
            else if (this.k != 0L) {
                final FileInputStream fileInputStream = new FileInputStream(this.f);
                this.s.setDataSource(fileInputStream.getFD(), this.j, this.k);
                fileInputStream.close();
            }
            else {
                final AssetManager assets = this.getResources().getAssets();
                try {
                    final AssetFileDescriptor openFd = assets.openFd(this.f);
                    this.s.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                    openFd.close();
                }
                catch (IOException ex2) {
                    final FileInputStream fileInputStream2 = new FileInputStream(this.f);
                    this.s.setDataSource(fileInputStream2.getFD());
                    fileInputStream2.close();
                }
            }
            this.s.setDisplay(this.e);
            this.s.setOnBufferingUpdateListener((MediaPlayer.OnBufferingUpdateListener)this);
            this.s.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
            this.s.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
            this.s.setOnVideoSizeChangedListener((MediaPlayer.OnVideoSizeChangedListener)this);
            this.s.setAudioStreamType(3);
            this.s.prepare();
            if (this.g == 0 || this.g == 1) {
                (this.t = new MediaController(this.c)).setMediaPlayer((MediaController.MediaPlayerControl)this);
                this.t.setAnchorView((View)this.d);
                this.t.setEnabled(true);
                this.t.show();
            }
        }
        catch (Exception ex) {
            if (com.unity3d.player.q.a) {
                a("error: " + ex.getMessage() + ex);
            }
            this.onDestroy();
        }
    }
    
    public final boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4 || (this.g == 2 && n != 0 && !keyEvent.isSystem())) {
            this.onDestroy();
            return true;
        }
        if (this.t != null) {
            return this.t.onKeyDown(n, keyEvent);
        }
        return super.onKeyDown(n, keyEvent);
    }
    
    public final boolean onTouchEvent(final MotionEvent motionEvent) {
    	Log.i("hack unity", "motion event 6:"+motionEvent.getX()+" "+motionEvent.getY());
        final int n = motionEvent.getAction() & 0xFF;
        if (this.g == 2 && n == 0) {
            this.onDestroy();
            return true;
        }
        if (this.t != null) {
            return this.t.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }
    
    public final void onBufferingUpdate(final MediaPlayer mediaPlayer, final int w) {
        if (com.unity3d.player.q.a) {
            a("onBufferingUpdate percent:" + w);
        }
        this.w = w;
    }
    
    public final void onCompletion(final MediaPlayer mediaPlayer) {
        if (com.unity3d.player.q.a) {
            a("onCompletion called");
        }
        this.onDestroy();
    }
    
    public final void onVideoSizeChanged(final MediaPlayer mediaPlayer, final int q, final int r) {
        if (com.unity3d.player.q.a) {
            a("onVideoSizeChanged called " + q + "x" + r);
        }
        if (q == 0 || r == 0) {
            if (com.unity3d.player.q.a) {
                a("invalid video width(" + q + ") or height(" + r + ")");
            }
            return;
        }
        this.u = true;
        this.q = q;
        this.r = r;
        if (this.v && this.u) {
            this.b();
        }
    }
    
    public final void onPrepared(final MediaPlayer mediaPlayer) {
        if (com.unity3d.player.q.a) {
            a("onPrepared called");
        }
        this.v = true;
        if (this.v && this.u) {
            this.b();
        }
    }
    
    public final void surfaceChanged(final SurfaceHolder surfaceHolder, final int n, final int o, final int p4) {
        if (com.unity3d.player.q.a) {
            a("surfaceChanged called " + n + " " + o + "x" + p4);
        }
        this.o = o;
        this.p = p4;
    }
    
    public final void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
        if (com.unity3d.player.q.a) {
            a("surfaceDestroyed called");
        }
        this.doCleanUp();
    }
    
    public final void surfaceCreated(final SurfaceHolder surfaceHolder) {
        if (com.unity3d.player.q.a) {
            a("surfaceCreated called");
        }
        this.a();
        this.seekTo(this.y);
    }
    
    protected final void doCleanUp() {
        if (this.s != null) {
            this.s.release();
            this.s = null;
        }
        this.q = 0;
        this.r = 0;
        this.v = false;
        this.u = false;
    }
    
    private void b() {
        if (this.isPlaying()) {
            return;
        }
        if (com.unity3d.player.q.a) {
            a("startVideoPlayback");
        }
        this.updateVideoLayout();
        if (!this.x) {
            this.start();
        }
    }
    
    protected final void updateVideoLayout() {
        if (com.unity3d.player.q.a) {
            a("updateVideoLayout");
        }
        final WindowManager windowManager = (WindowManager)this.c.getSystemService("window");
        this.o = windowManager.getDefaultDisplay().getWidth();
        this.p = windowManager.getDefaultDisplay().getHeight();
        int n = this.o;
        int n2 = this.p;
        if (this.h == 1 || this.h == 2) {
            final float n3 = this.q / this.r;
            if (this.o / this.p <= n3) {
                n2 = (int)(this.o / n3);
            }
            else {
                n = (int)(this.p * n3);
            }
        }
        else if (this.h == 0) {
            n = this.q;
            n2 = this.r;
        }
        if (com.unity3d.player.q.a) {
            a("frameWidth = " + n + "; frameHeight = " + n2);
        }
        this.l.updateViewLayout((View)this.d, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(n, n2, 17));
    }
    
    public final boolean canPause() {
        return true;
    }
    
    public final boolean canSeekBackward() {
        return true;
    }
    
    public final boolean canSeekForward() {
        return true;
    }
    
    public final int getBufferPercentage() {
        if (this.i) {
            return this.w;
        }
        return 100;
    }
    
    public final int getCurrentPosition() {
        if (this.s == null) {
            return 0;
        }
        return this.s.getCurrentPosition();
    }
    
    public final int getDuration() {
        if (this.s == null) {
            return 0;
        }
        return this.s.getDuration();
    }
    
    public final boolean isPlaying() {
        final boolean b = this.v && this.u;
        if (this.s == null) {
            return !b;
        }
        return this.s.isPlaying() || !b;
    }
    
    public final void pause() {
        if (this.s == null) {
            return;
        }
        this.s.pause();
        this.x = true;
    }
    
    public final void seekTo(final int n) {
        if (this.s == null) {
            return;
        }
        this.s.seekTo(n);
    }
    
    public final void start() {
        if (this.s == null) {
            return;
        }
        this.s.start();
        this.x = false;
    }
    
    public final void onSensorChanged(final SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1) {
            this.b.nativeDeviceOrientation(calculateDeviceOrientation(sensorEvent, this.n));
        }
    }
    
    public final void onAccuracyChanged(final Sensor sensor, final int n) {
    }
    
    public static int calculateDeviceOrientation(final SensorEvent sensorEvent, final Display display) {
        final float n = sensorEvent.values[0];
        final float n2 = sensorEvent.values[1];
        final float n3 = sensorEvent.values[2];
        final float n4 = 1.0f / (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
        float n5 = n * n4;
        float n6 = n2 * n4;
        final float n7 = n3 * n4;
        if (display.getHeight() > display.getWidth() ^ (display.getOrientation() & 0x1) == 0x0) {
            final float n8 = -n5;
            n5 = n6;
            n6 = n8;
        }
        if (com.unity3d.player.q.A) {
            n5 = -n5;
        }
        float n9 = -1.0f;
        int n10 = 0;
        if (-1.0f < n6) {
            n9 = n6;
            n10 = 1;
        }
        if (n9 < -n6) {
            n9 = -n6;
            n10 = 2;
        }
        if (n9 < n5) {
            n9 = n5;
            n10 = 3;
        }
        if (n9 < -n5) {
            n9 = -n5;
            n10 = 4;
        }
        if (n9 < n7) {
            n9 = n7;
            n10 = 5;
        }
        if (n9 < -n7) {
            n9 = -n7;
            n10 = 6;
        }
        if (n9 < 0.5 * Math.sqrt(3.0)) {
            n10 = 0;
        }
        return n10;
    }
    
    static {
    	com.unity3d.player.q.a = false;
        A = (Build.MANUFACTURER.equalsIgnoreCase("Amazon") && (Build.MODEL.equalsIgnoreCase("KFTT") || Build.MODEL.equalsIgnoreCase("KFJWI") || Build.MODEL.equalsIgnoreCase("KFJWA") || Build.MODEL.equalsIgnoreCase("KFSOWI") || Build.MODEL.equalsIgnoreCase("KFTHWA") || Build.MODEL.equalsIgnoreCase("KFTHWI") || Build.MODEL.equalsIgnoreCase("KFAPWA") || Build.MODEL.equalsIgnoreCase("KFAPWI")));
    }
}
