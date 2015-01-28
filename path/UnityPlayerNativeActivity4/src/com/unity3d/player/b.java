package com.unity3d.player;

import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

abstract class b implements SurfaceHolder.Callback {
	private final Activity a;
	private final int b;
	private SurfaceView c;

	public b() {
		this.a = (Activity) com.unity3d.player.n.a.a();
		this.b = 3;
	}

	public b(final int n) {
		this.a = (Activity) com.unity3d.player.n.a.a();
		this.b = 3;
	}

	final void a() {
		this.a.runOnUiThread((Runnable) new Runnable() {
			@Override
			public final void run() {
				if (com.unity3d.player.b.this.c == null) {
					com.unity3d.player.b.this.c = new SurfaceView(n.a.a());
					com.unity3d.player.b.this.c.getHolder().setType(
							com.unity3d.player.b.this.b);
					com.unity3d.player.b.this.c.getHolder().addCallback(
							(SurfaceHolder.Callback) com.unity3d.player.b.this);
					n.a.a((View) com.unity3d.player.b.this.c);
					com.unity3d.player.b.this.c.setVisibility(0);
				}
			}
		});
	}

	final void b() {
		this.a.runOnUiThread((Runnable) new Runnable() {
			@Override
			public final void run() {
				if (com.unity3d.player.b.this.c != null) {
					n.a.b((View) com.unity3d.player.b.this.c);
				}
				com.unity3d.player.b.this.c = null;
			}
		});
	}

	public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
	}

	public void surfaceChanged(final SurfaceHolder surfaceHolder, final int n,
			final int n2, final int n3) {
	}
}
