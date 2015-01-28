package com.unity3d.player;

import java.util.ArrayList;
import java.util.List;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;

final class CopyOfa
{
    private final Object[] g;
    private final int h;
    private final int i;
    private final int j;
    private final int k;
    Camera a;
    Camera.Parameters b;
    Camera.Size c;
    int d;
    int[] e;
    b f;
    
    public CopyOfa(final int h, final int n, final int n2, final int n3) {
        this.g = new Object[0];
        this.h = h;
        this.i = a(n, 640);
        this.j = a(n2, 480);
        this.k = a(n3, 24);
    }
    
    private void b(final CopyOfa a) {
        synchronized (this.g) {
            this.a = Camera.open(this.h);
            this.b = this.a.getParameters();
            this.c = this.f();
            this.e = this.e();
            this.d = this.d();
            a(this.b);
            this.b.setPreviewSize(this.c.width, this.c.height);
            this.b.setPreviewFpsRange(this.e[0], this.e[1]);
            com.unity3d.player.h.Log(3, String.format("camera: size req = %dx%d \tactual = %dx%d", this.i, this.j, this.c.width, this.c.height));
            com.unity3d.player.h.Log(3, String.format("camera: kfps req = %d \tactual = %d-%d", this.k * 1000, this.e[0], this.e[1]));
            com.unity3d.player.h.Log(3, String.format("camera: parameters [%s]", this.b.flatten()));
            this.a.setParameters(this.b);
            final Camera.PreviewCallback previewCallbackWithBuffer = (Camera.PreviewCallback)new Camera.PreviewCallback() {
                long a = 0L;
                
                public final void onPreviewFrame(final byte[] array, final Camera camera) {
                    if (com.unity3d.player.CopyOfa.this.a != camera) {
                        return;
                    }
                    final long a = this.a + 1L;
                    this.a = a;
                    if ((a & 0xFFL) == 0x0L) {
                        com.unity3d.player.h.Log(3, "camera: frame " + this.a);
                    }
//                    a.onCameraFrame(com.unity3d.player.CopyOfa.this, array);
                }
            };
            final int n = this.c.width * this.c.height * this.d / 8 + 4096;
            this.a.addCallbackBuffer(new byte[n]);
            this.a.addCallbackBuffer(new byte[n]);
            this.a.setPreviewCallbackWithBuffer((Camera.PreviewCallback)previewCallbackWithBuffer);
        }
    }
    
    private static void a(final Camera.Parameters parameters) {
        if (parameters.getSupportedColorEffects() != null) {
            parameters.setColorEffect("none");
        }
        if (parameters.getSupportedFocusModes().contains("continuous-video")) {
            parameters.setFocusMode("continuous-video");
        }
    }
    
    public final int a() {
        return this.h;
    }
    
    public final Camera.Size b() {
        return this.c;
    }
    
    public final void a(final CopyOfa a) {
        synchronized (this.g) {
            if (this.a == null) {
                this.b(a);
            }
            if (com.unity3d.player.k.a && com.unity3d.player.k.c.a(this.a)) {
                this.a.startPreview();
                return;
            }
            if (this.f == null) {
                (this.f = new b() {
                    Camera a = com.unity3d.player.CopyOfa.this.a;
                    
                    public final void surfaceCreated(final SurfaceHolder previewDisplay) {
                        synchronized (com.unity3d.player.CopyOfa.this.g) {
                            if (com.unity3d.player.CopyOfa.this.a != this.a) {
                                return;
                            }
                            try {
                                com.unity3d.player.CopyOfa.this.a.setPreviewDisplay(previewDisplay);
                                com.unity3d.player.CopyOfa.this.a.startPreview();
                            }
                            catch (Exception ex) {
                                com.unity3d.player.h.Log(6, "Unable to initialize webcam data stream: " + ex.getMessage());
                            }
                        }
                    }
                    
                    @Override
                    public final void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
                        synchronized (com.unity3d.player.CopyOfa.this.g) {
                            if (com.unity3d.player.CopyOfa.this.a != this.a) {
                                return;
                            }
                            com.unity3d.player.CopyOfa.this.a.stopPreview();
                        }
                    }
                }).a();
            }
        }
    }
    
    public final void a(final byte[] array) {
        synchronized (this.g) {
            if (this.a != null) {
                this.a.addCallbackBuffer(array);
            }
        }
    }
    
    public final void c() {
        synchronized (this.g) {
            if (this.a != null) {
                this.a.setPreviewCallbackWithBuffer((Camera.PreviewCallback)null);
                this.a.stopPreview();
                this.a.release();
                this.a = null;
            }
            if (this.f != null) {
                this.f.b();
                this.f = null;
            }
        }
    }
    
    private final int d() {
        this.b.setPreviewFormat(17);
        return ImageFormat.getBitsPerPixel(17);
    }
    
    private final int[] e() {
        final double n = this.k * 1000;
        List<int[]> supportedPreviewFpsRange;
        if ((supportedPreviewFpsRange = (List<int[]>)this.b.getSupportedPreviewFpsRange()) == null) {
            supportedPreviewFpsRange = new ArrayList<int[]>();
        }
        for (final int[] array : supportedPreviewFpsRange) {
            com.unity3d.player.h.Log(3, String.format("camera: fpsrange[i] = %dx%d", array[0], array[1]));
        }
        int[] array2 = { this.k * 1000, this.k * 1000 };
        double n2 = Double.MAX_VALUE;
        for (final int[] array3 : supportedPreviewFpsRange) {
            final double n3;
            if ((n3 = Math.abs(Math.log(n / array3[0])) + Math.abs(Math.log(n / array3[1]))) < n2) {
                n2 = n3;
                array2 = array3;
            }
        }
        return array2;
    }
    
    private final Camera.Size f() {
        final double n = this.i;
        final double n2 = this.j;
        List<Size> supportedPreviewSizes;
        for (final Camera.Size size : supportedPreviewSizes = this.b.getSupportedPreviewSizes()) {
            com.unity3d.player.h.Log(3, String.format("camera: sz[i] = %dx%d", size.width, size.height));
        }
        Camera.Size size2 = null;
        double n3 = Double.MAX_VALUE;
        for (final Camera.Size size3 : supportedPreviewSizes) {
            final double n4;
            if ((n4 = Math.abs(Math.log(n / size3.width)) + Math.abs(Math.log(n2 / size3.height))) < n3) {
                n3 = n4;
                size2 = size3;
            }
        }
        return size2;
    }
    
    private static final int a(final int n, final int n2) {
        if (n != 0) {
            return n;
        }
        return n2;
    }
    
    interface aaa
    {
        void onCameraFrame(com.unity3d.player.CopyOfa p0, byte[] p1);
    }
}
