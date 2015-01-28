package com.unity3d.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.fmod.FMODAudioDevice;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class UnityPlayer extends FrameLayout implements
		com.unity3d.player.a.aaa {
	public static Activity currentActivity;
	private boolean c;
	private boolean d;
	private boolean e;
	private final g f;
	private final n g;
	private boolean h;
	private p i;
	private final ConcurrentLinkedQueue<Runnable> j;
	private BroadcastReceiver k;
	private boolean l;
	a a;
	private ContextWrapper m;
	private SurfaceView n;
	private PowerManager.WakeLock o;
	private SensorManager p;
	private WindowManager q;
	private FMODAudioDevice r;
	private static boolean s;
	private boolean t;
	private boolean u;
	private int v;
	private int w;
	private int x;
	private int y;
	private final l z;
	private String A;
	private NetworkInfo B;
	private Bundle C;
	private List<com.unity3d.player.a> D;
	private q E;
	m b;
	private ProgressBar F;
	private Runnable G;
	private Runnable H;

	public UnityPlayer(final ContextWrapper m) {
		super((Context) m);
		this.c = false;
		this.d = false;
		this.e = false;
		this.h = false;
		this.i = new p();
		this.j = new ConcurrentLinkedQueue<Runnable>();
		this.k = null;
		this.l = false;
		this.a = new a();
		this.u = true;
		this.x = 0;
		this.y = 0;
		this.A = null;
		this.B = null;
		this.C = new Bundle();
		this.D = new ArrayList();
		this.b = null;
		this.F = null;
		this.G = new Runnable() {
			@Override
			public final void run() {
				final int m;
				if ((m = UnityPlayer.this.nativeActivityIndicatorStyle()) >= 0) {
					if (UnityPlayer.this.F == null) {
						UnityPlayer.this.F = new ProgressBar(
								(Context) UnityPlayer.this.m,
								(AttributeSet) null, (new int[] { 16842874,
										16843401, 16842873, 16843400 })[m]);
						UnityPlayer.this.F.setIndeterminate(true);
						UnityPlayer.this.F
								.setLayoutParams((ViewGroup.LayoutParams) new FrameLayout.LayoutParams(
										-2, -2, 51));
						UnityPlayer.this.addView((View) UnityPlayer.this.F);
					}
					UnityPlayer.this.F.setVisibility(0);
					UnityPlayer.this
							.bringChildToFront((View) UnityPlayer.this.F);
				}
			}
		};
		this.H = new Runnable() {
			@Override
			public final void run() {
				if (UnityPlayer.this.F != null) {
					UnityPlayer.this.F.setVisibility(8);
					UnityPlayer.this.F = null;
				}
			}
		};
		if (m instanceof Activity) {
			UnityPlayer.currentActivity = (Activity) m;
		}
		this.g = new n(this);
		this.m = m;
		this.f = ((m instanceof Activity) ? new j(m) : null);
		this.z = new l((Context) m, this);
		this.a();
		a(this.m.getApplicationInfo());
		if (!com.unity3d.player.p.c()) {
			final AlertDialog create;
			(create = new AlertDialog.Builder((Context) this.m)
					.setTitle((CharSequence) "Failure to initialize!")
					.setPositiveButton(
							(CharSequence) "OK",
							(DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
								public final void onClick(
										final DialogInterface dialogInterface,
										final int n) {
									UnityPlayer.this.b();
								}
							})
					.setMessage(
							(CharSequence) "Your hardware does not support this application, sorry!")
					.create()).setCancelable(false);
			create.show();
			return;
		}
		this.nativeFile(this.m.getPackageCodePath());
		this.k();
		this.n = new SurfaceView((Context) m);
		this.n.getHolder().addCallback(
				(SurfaceHolder.Callback) new SurfaceHolder.Callback() {
					public final void surfaceCreated(
							final SurfaceHolder surfaceHolder) {
						final Surface surface = surfaceHolder.getSurface();
						if (com.unity3d.player.p.c()) {
							UnityPlayer.this.nativeRecreateGfxState(surface);
						}

						Log.i("hack unity", "in surfaceCreated");

					}
//				       public void surfaceChanged(SurfaceHolder holder, int format, int width,
//				                int height);
					public final void surfaceChanged(
							final SurfaceHolder surfaceHolder, final int n,
							final int n2, final int n3) {
						UnityPlayer.this.a(new b() {
							@Override
							public final void a() {
								int n = n2;
								int n2 = n3;
								if ((UnityPlayer.this.x != 0 || UnityPlayer.this.y != 0)
										&& (UnityPlayer.this.x != n || UnityPlayer.this.y != n2)) {
									UnityPlayer.this.setScreenSize(
											UnityPlayer.this.x,
											UnityPlayer.this.y,
											com.unity3d.player.k.a
													&& com.unity3d.player.k.c
															.a());
									n = UnityPlayer.this.x;
									n2 = UnityPlayer.this.y;
								}
								UnityPlayer.this.v = n;
								UnityPlayer.this.w = n2;
								UnityPlayer.this.nativeResize(
										UnityPlayer.this.v, UnityPlayer.this.w,
										UnityPlayer.this.n.getWidth(),
										UnityPlayer.this.n.getHeight());
								UnityPlayer.this.h();

								Log.i("hack unity",
										"in surfaceChanged nativeResize v:"
												+ UnityPlayer.this.v
												+ " w:"
												+ UnityPlayer.this.w
												+ " width:"
												+ UnityPlayer.this.n.getWidth()
												+ " "
												+ UnityPlayer.this.n
														.getHeight());
							}
						});
					}

					public final void surfaceDestroyed(
							final SurfaceHolder surfaceHolder) {
					}
				});
		this.n.setFocusable(true);
		this.n.setFocusableInTouchMode(true);
		this.g.c((View) this.n);
		this.t = false;
		if (this.m instanceof Activity) {
			final Window window;
			(window = ((Activity) this.m).getWindow()).addFlags(2097152);
			window.addFlags(524288);
			window.addFlags(4194304);
		}
		this.k = new BroadcastReceiver() {
			public void onReceive(final Context context, final Intent intent) {
				UnityPlayer.this.b();
			}
		};
		this.m.registerReceiver(this.k, new IntentFilter(
				"com.unity3d.intent.action.SHUTDOWN"));
		this.o = ((PowerManager) this.m.getSystemService("power")).newWakeLock(
				26, "Unity-VideoPlayerWakeLock");
		this.c();
		this.initJni((Context) m);
		this.nativeInitWWW(WWW.class);
		if (com.unity3d.player.k.a) {
			com.unity3d.player.k.c.a((View) this);
		}
		this.p = (SensorManager) this.m.getSystemService("sensor");
		this.q = (WindowManager) this.m.getSystemService("window");
		this.nativeSetDefaultDisplay(this.q.getDefaultDisplay().getDisplayId());
		this.a.start();
	}

	private void a() {
		try {
			final File file;
			InputStream open;
			if ((file = new File(this.m.getPackageCodePath(),
					"assets/bin/Data/settings.xml")).exists()) {
				open = new FileInputStream(file);
			} else {
				open = this.m.getAssets().open("bin/Data/settings.xml");
			}
			final XmlPullParserFactory instance;
			(instance = XmlPullParserFactory.newInstance())
					.setNamespaceAware(true);
			final XmlPullParser pullParser;
			(pullParser = instance.newPullParser()).setInput(open,
					(String) null);
			String name = null;
			String attributeValue = null;
			for (int i = pullParser.getEventType(); i != 1; i = pullParser
					.next()) {
				if (i == 2) {
					name = pullParser.getName();
					for (int j = 0; j < pullParser.getAttributeCount(); ++j) {
						if (pullParser.getAttributeName(j).equalsIgnoreCase(
								"name")) {
							attributeValue = pullParser.getAttributeValue(j);
						}
					}
				} else if (i == 3) {
					name = null;
				} else if (i == 4 && attributeValue != null) {
					com.unity3d.player.h.Log(4, attributeValue + " = "
							+ pullParser.getText() + " (" + name + ")");
					if (name.equalsIgnoreCase("integer")) {
						this.C.putInt(attributeValue,
								Integer.parseInt(pullParser.getText()));
					} else if (name.equalsIgnoreCase("string")) {
						this.C.putString(attributeValue, pullParser.getText());
					} else if (name.equalsIgnoreCase("bool")) {
						this.C.putBoolean(attributeValue,
								Boolean.parseBoolean(pullParser.getText()));
					} else if (name.equalsIgnoreCase("float")) {
						this.C.putFloat(attributeValue,
								Float.parseFloat(pullParser.getText()));
					}
					attributeValue = null;
				}
			}
		} catch (Exception ex) {
			com.unity3d.player.h.Log(6, "Unable to locate player settings. "
					+ ex.getLocalizedMessage());
			this.b();
		}
	}

	public View getView() {
		return (View) this;
	}

	public Bundle getSettings() {
		return this.C;
	}

	public static native void UnitySendMessage(final String p0,
			final String p1, final String p2);

	private void b() {
		if (this.m instanceof Activity && !((Activity) this.m).isFinishing()) {
			((Activity) this.m).finish();
		}
	}

	static void a(final Runnable runnable) {
		new Thread(runnable).start();
	}

	private void b(final Runnable runnable) {
		if (this.m instanceof Activity) {
			((Activity) this.m).runOnUiThread(runnable);
			return;
		}
		com.unity3d.player.h.Log(5,
				"Not running Unity from an Activity; ignored...");
	}

	public void init(final int n, final boolean b) {
	}

	private void c() {
		this.nativeForwardEventsToDalvik(this.l = new i((Activity) this.m).a());
	}

	protected void kill() {
		Process.killProcess(Process.myPid());
	}

	public void quit() {
		this.t = true;
		if (!this.i.d()) {
			this.pause();
		}
		this.a.a1();
		try {
			this.a.join(10000L);
		} catch (InterruptedException ex) {
		}
		com.unity3d.player.h.Log(4, "onDestroy");
		if (this.k != null) {
			this.m.unregisterReceiver(this.k);
		}
		this.k = null;
		if (com.unity3d.player.p.c()) {
			this.removeAllViews();
		}
		i();
		this.kill();
	}

	private void d() {
		final Iterator<com.unity3d.player.a> iterator = this.D.iterator();
		while (iterator.hasNext()) {
			iterator.next().c();
		}
	}

	private void e() {
		for (final com.unity3d.player.a a1 : this.D) {
			try {
				a1.a(a1);
			} catch (Exception ex) {
				com.unity3d.player.h.Log(6, "Unable to initialize camera: "
						+ ex.getMessage());
				a1.c();
			}
		}
	}

	public void pause() {
		com.unity3d.player.h.Log(4, "onPause");
		if (this.E != null) {
			this.E.onPause();
			this.a(false);
			return;
		}
		this.reportSoftInputStr(null, 1, true);
		final p i = this.i;
		if (com.unity3d.player.p.c()) {
			final Semaphore semaphore = new Semaphore(0);
			if (this.isFinishing()) {
				this.c(new Runnable() {
					@Override
					public final void run() {
						UnityPlayer.this.f();
						semaphore.release();
					}
				});
			} else {
				this.c(new Runnable() {
					@Override
					public final void run() {
						if (UnityPlayer.this.nativePause()) {
							UnityPlayer.this.f();
							semaphore.release(2);
							return;
						}
						semaphore.release();
					}
				});
			}
			try {
				semaphore.tryAcquire(10L, TimeUnit.SECONDS);
			} catch (InterruptedException ex) {
			}
			if (semaphore.drainPermits() > 0) {
				this.quit();
			}
		}
		if (!this.i.f()) {
			return;
		}
		this.i.c(false);
		this.i.b(true);
		this.d();
		if (this.r != null) {
			this.r.stop();
		}
		this.a.c();
		this.z.d();
	}

	public void resume() {
		com.unity3d.player.h.Log(4, "onResume");
		if (com.unity3d.player.k.a) {
			com.unity3d.player.k.c.b((View) this);
		}
		this.i.b(false);
		this.g();
	}

	private void f() {
		if (this.r != null) {
			this.r.close();
		}
		this.nativeDone();
	}

	private void g() {
		if (!this.i.e()) {
			return;
		}
		if (this.E != null) {
			this.a(true);
			this.E.onResume();
			return;
		}
		this.i.c(true);
		this.e();
		this.a.b();
		this.z.e();
		this.A = null;
		this.B = null;
		final p i = this.i;
		if (com.unity3d.player.p.c()) {
			this.k();
		}
		this.c(new Runnable() {
			@Override
			public final void run() {
				UnityPlayer.this.nativeResume();
			}
		});
		if (UnityPlayer.s && this.r == null) {
			this.r = new FMODAudioDevice();
		}
		if (this.r != null && !this.r.isRunning()) {
			this.r.start();
		}
	}

	public void configurationChanged(final Configuration configuration) {
		com.unity3d.player.h.Log(4, "onConfigurationChanged");
		if (this.n instanceof SurfaceView) {
			this.n.getHolder().setSizeFromLayout();
		}
		if (this.e && configuration.hardKeyboardHidden == 2) {
			((InputMethodManager) this.m.getSystemService("input_method"))
					.toggleSoftInput(0, 1);
		}
		if (this.E != null) {
			this.E.updateVideoLayout();
		}
	}

	public void windowFocusChanged(final boolean b) {
		com.unity3d.player.h.Log(4, "windowFocusChanged: " + b);
		this.i.a(b);
		if (b && this.b != null) {
			this.reportSoftInputStr(null, 1, false);
		}
		this.c(new Runnable() {
			@Override
			public final void run() {
				UnityPlayer.this.nativeFocusChanged(b);
			}
		});
		this.g();
	}

	private void h() {
		if (this.m instanceof Activity) {
			float n = 0.0f;
			if (!this.C.getBoolean("hide_status_bar", true)) {
				final Activity activity = (Activity) this.m;
				final Rect rect = new Rect();
				activity.getWindow().getDecorView()
						.getWindowVisibleDisplayFrame(rect);
				n = rect.top;
			}
			this.nativeSetTouchDeltaY(n);
		}
	}

	protected void startActivityIndicator() {
		this.b(this.G);
	}

	protected void stopActivityIndicator() {
		this.b(this.H);
	}

	private final native void nativeFile(final String p0);

	private final native void initJni(final Context p0);

	private final native void nativeSetDefaultDisplay(final int p0);

	private final native void nativeSetExtras(final Bundle p0);

	private final native void nativeResize(final int p0, final int p1,
			final int p2, final int p3);

	private final native void nativeSetTouchDeltaY(final float p0);

	private final native boolean nativeRender();

	final native void nativeDeviceOrientation(final int p0);

	private final native void nativeSetInputString(final String p0);

	private final native void nativeSetInputCanceled(final boolean p0);

	private final native boolean nativePause();

	private final native void nativeResume();

	private final native void nativeFocusChanged(final boolean p0);

	private final native void nativeRecreateGfxState(final Surface p0);

	private final native void nativeDone();

	private final native void nativeSoftInputClosed();

	private final native void nativeInitWWW(final Class p0);

	private final native void nativeVideoFrameCallback(final int p0,
			final byte[] p1, final int p2, final int p3);

	private final native int nativeRequestedAA();

	private final native boolean nativeRequested32bitDisplayBuffer();

	private final native int nativeActivityIndicatorStyle();

	private final native boolean nativeInjectEvent(final InputEvent p0);

	private final native void nativeKeysPressed(final String p0);

	final native void nativeForwardEventsToDalvik(final boolean p0);

	private static void a(final ApplicationInfo applicationInfo) {
		if (NativeLoader.load(applicationInfo.nativeLibraryDir)) {
			com.unity3d.player.p.a();
			return;
		}
		throw new UnsatisfiedLinkError(
				"Unable to load libraries from libmain.so");
	}

	private static void i() {
		if (!com.unity3d.player.p.c()) {
			return;
		}
		if (!NativeLoader.unload()) {
			throw new UnsatisfiedLinkError(
					"Unable to unload libraries from libmain.so");
		}
		com.unity3d.player.p.b();
	}

	protected void forwardMotionEventToDalvik(final long n, final long n2,
			final int n3, final int n4, final int[] array,
			final float[] array2, final int n5, final float n6, final float n7,
			final int n8, final int n9, final int n10, final int n11,
			final int n12, final long[] array3, final float[] array4) {
		this.f.a(n, n2, n3, n4, array, array2, n5, n6, n7, n8, n9, n10, n11,
				n12, array3, array4);
	}

	protected void setScreenSize(final int x, final int y, final boolean b) {
		Log.i("hack unity", "hack unity setScreenSize");
		final SurfaceView n;
		final Rect surfaceFrame = (n = this.n).getHolder().getSurfaceFrame();
		com.unity3d.player.h.Log(4, String.format(
				"setScreenSize(hack): %dx%d (%dx%d / %dx%d)", x, y,
				surfaceFrame.width(), surfaceFrame.height(), n.getWidth(),
				n.getHeight()));
		final boolean b2 = (n.getWidth() == x && n.getHeight() == y)
				|| (x == 0 && y == 0);
		final boolean b3;
		if (!(b3 = (x == -1 && y == -1))) {
			if (b2) {
				com.unity3d.player.h.Log(4,
						"setScreenSize: setSizeFromLayout()");
				this.x = 0;
				this.y = 0;
			} else {
				com.unity3d.player.h.Log(4, "setScreenSize: setFixedSize(" + x
						+ ", " + y + ")");
				this.x = x;
				this.y = y;
			}
		} else if (this.x != 0 || this.y != 0) {
			com.unity3d.player.h.Log(4, "setScreenSize: keeping fixed size "
					+ this.x + "x" + this.y);
		} else {
			com.unity3d.player.h.Log(4, "setScreenSize: keeping layout size "
					+ n.getWidth() + "x" + n.getHeight());
		}
		this.b(new Runnable() {
			@Override
			public final void run() {
				if (!b3) {
					if (b2) {
						n.getHolder().setSizeFromLayout();
					} else {
						n.getHolder().setFixedSize(x, y);
					}
					n.invalidate();
				}
				if (com.unity3d.player.k.a) {
					com.unity3d.player.k.c.a((View) UnityPlayer.this, b);
				}
			}
		});

	}

	protected void showSoftInput(final String s, final int n, final boolean b,
			final boolean b2, final boolean b3, final boolean b4,
			final String s2) {
		this.b(new Runnable() {
			@Override
			public final void run() {
				if (UnityPlayer.this.d) {
					((InputMethodManager) UnityPlayer.this.m
							.getSystemService("input_method")).toggleSoftInput(
							0, 1);
					UnityPlayer.this.e = true;
					return;
				}
				final UnityPlayer i = UnityPlayer.this;
				final ContextWrapper o = UnityPlayer.this.m;
				final UnityPlayer a = UnityPlayer.this;
				final int c = n;
				final boolean d = b;
				final boolean e = b2;
				final boolean f = b3;
				final boolean g = b4;
				i.b = new m((Context) o, a, s, c, d, e, f, s2);
				UnityPlayer.this.b.show();
			}
		});
	}

	protected void hideSoftInput() {
		this.b(new Runnable() {
			@Override
			public final void run() {
				if (UnityPlayer.this.e) {
					((InputMethodManager) UnityPlayer.this.m
							.getSystemService("input_method")).toggleSoftInput(
							1, 0);
					UnityPlayer.this.e = false;
					return;
				}
				if (UnityPlayer.this.b != null) {
					UnityPlayer.this.b.dismiss();
					UnityPlayer.this.b = null;
				}
			}
		});
	}

	protected void setSoftInputStr(final String s) {
		this.b(new Runnable() {
			@Override
			public final void run() {
				if (UnityPlayer.this.b != null && s != null) {
					UnityPlayer.this.b.a(s);
				}
			}
		});
	}

	protected void reportSoftInputStr(final String s, final int n,
			final boolean b) {
		if (n == 1) {
			this.hideSoftInput();
		}
		this.a(new b() {
			@Override
			public final void a() {
				if (b) {
					UnityPlayer.r(UnityPlayer.this);
				} else if (s != null) {
					UnityPlayer.this.nativeSetInputString(s);
				}
				if (n == 1) {
					UnityPlayer.this.nativeSoftInputClosed();
				}
			}
		});
	}

	protected void setHideInputField(final boolean d) {
		this.d = d;
	}

	protected int[] initCamera(int n2, final int n3, final int n4, final int n5) {
		com.unity3d.player.h.Log(4, "getNumCameras: " + this.getNumCameras());
		com.unity3d.player.h.Log(4, "initCamera: " + n2);
		com.unity3d.player.a n22 = new com.unity3d.player.a(n2, n3, n4, n5);
		n22.unityPlayer = this;

		try {
			((com.unity3d.player.a) n22).a(n22);
			this.D.add(n22);
			final Camera.Size b = ((com.unity3d.player.a) n22).b();
			return new int[] { b.width, b.height };
		} catch (Exception ex) {
			com.unity3d.player.h.Log(6,
					"Unable to initialize camera: " + ex.getMessage());
			((com.unity3d.player.a) n22).c();
			return null;
		}
	}

	protected void closeCamera(final int n) {
		final Iterator<com.unity3d.player.a> iterator = this.D.iterator();
		while (iterator.hasNext()) {
			final com.unity3d.player.a a;
			if ((a = iterator.next()).a() == n) {
				a.c();
				this.D.remove(a);
			}
		}
	}

	protected int getNumCameras() {
		if (!this.j()) {
			return 0;
		}
		return Camera.getNumberOfCameras();
	}

	public void onCameraFrame(final com.unity3d.player.a a1, final byte[] array) {
		this.a(new b() {
			final/* synthetic */int a = a1.a();
			final/* synthetic */Camera.Size c = a1.b();

			@Override
			public final void a() {
				UnityPlayer.this.nativeVideoFrameCallback(this.a, array,
						this.c.width, this.c.height);
				a1.a(array);
			}
		});
	}

	protected boolean isCameraFrontFacing(final int n) {
		final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		Camera.getCameraInfo(n, cameraInfo);
		return cameraInfo.facing == 1;
	}

	protected int getCameraOrientation(final int n) {
		final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		Camera.getCameraInfo(n, cameraInfo);
		return cameraInfo.orientation;
	}

	protected void showVideoPlayer(final String s, final int n, final int n2,
			final int n3, final boolean b, final int n4, final int n5) {
		this.b(new Runnable() {
			@Override
			public final void run() {
				if (UnityPlayer.this.E != null) {
					return;
				}
				UnityPlayer.this.a(true);
				UnityPlayer.this.pause();
				UnityPlayer.this.E = new q(UnityPlayer.this,
						(Context) UnityPlayer.this.m, s, n, n2, n3, b, n4, n5);
				UnityPlayer.this.addView((View) UnityPlayer.this.E);
				UnityPlayer.this.E.requestFocus();
				UnityPlayer.this.g.d((View) UnityPlayer.this.n);
			}
		});
	}

	protected void hideVideoPlayer() {
		this.b(new Runnable() {
			@Override
			public final void run() {
				if (UnityPlayer.this.E == null) {
					return;
				}
				UnityPlayer.this.g.c((View) UnityPlayer.this.n);
				UnityPlayer.this.removeView((View) UnityPlayer.this.E);
				UnityPlayer.this.E = null;
				UnityPlayer.this.resume();
				UnityPlayer.this.a(false);
			}
		});
	}

	private void a(final boolean b) {
		a("video", this.o, b);
	}

	private static void a(final String s, final PowerManager.WakeLock wakeLock,
			final boolean b) {
		try {
			if (b == wakeLock.isHeld()) {
				return;
			}
			if (b) {
				com.unity3d.player.h.Log(3,
						String.format("Acquiring %s wake-lock", s));
				wakeLock.acquire();
				if (!wakeLock.isHeld()) {
					com.unity3d.player.h
							.Log(5,
									String.format(
											"Unable to acquire %s wake-lock. Make sure 'android.permission.WAKE_LOCK' has been set in the manifest.",
											s));
				}
			} else if (!b) {
				com.unity3d.player.h.Log(3,
						String.format("Releasing %s wake-lock", s));
				wakeLock.release();
			}
		} catch (Exception ex) {
			com.unity3d.player.h
					.Log(5,
							String.format(
									"Unable to acquire/release %s wake-lock. Make sure 'android.permission.WAKE_LOCK' has been set in the manifest.",
									s));
		}
	}

	protected int getScreenTimeout() {
		return Settings.System.getInt(this.m.getContentResolver(),
				"screen_off_timeout", 15000) / 1000;
	}

	protected int getInternetReachability() {
		try {
			if (this.B == null) {
				this.B = ((ConnectivityManager) this.m
						.getSystemService("connectivity"))
						.getActiveNetworkInfo();
			}
			final NetworkInfo b;
			if ((b = this.B) == null) {
				return 0;
			}
			if (!b.isConnected()) {
				return 0;
			}
			return b.getType() + 1;
		} catch (Exception ex) {
			com.unity3d.player.h.Log(5,
					"android.permission.ACCESS_NETWORK_STATE not available?");
			return 0;
		}
	}

	protected void Location_SetDesiredAccuracy(final float n) {
		this.z.b(n);
	}

	protected void Location_SetDistanceFilter(final float n) {
		this.z.a(n);
	}

	protected native void nativeSetLocationStatus(final int p0);

	protected native void nativeSetLocation(final float p0, final float p1,
			final float p2, final float p3, final double p4, final float p5);

	protected void Location_StartUpdatingLocation() {
		this.z.b();
	}

	protected void Location_StopUpdatingLocation() {
		this.z.c();
	}

	protected boolean Location_IsServiceEnabledByUser() {
		return this.z.a();
	}

	private boolean j() {
		return this.m.getPackageManager().hasSystemFeature(
				"android.hardware.camera")
				|| this.m.getPackageManager().hasSystemFeature(
						"android.hardware.camera.front");
	}

	protected void triggerResizeCall() {
		Log.i("hack unity", "triggerResizeCall v:" + v + " w:" + w + " v:" + v
				+ " w:" + w);
		this.nativeResize(this.v, this.w, this.v, this.w);
	}

	protected int getSplashMode() {
		return this.C.getInt("splash_mode");
	}

	protected void executeGLThreadJobs() {
		Runnable runnable;
		while ((runnable = this.j.poll()) != null) {
			runnable.run();
		}
	}

	protected void disableLogger() {
		com.unity3d.player.h.a = true;
	}

	private void c(final Runnable runnable) {
		final p i = this.i;
		if (!com.unity3d.player.p.c()) {
			return;
		}
		if (Thread.currentThread() == this.a) {
			runnable.run();
			return;
		}
		this.j.add(runnable);
	}

	private void a(final b b) {
		if (this.isFinishing()) {
			return;
		}
		this.c(b);
	}

	protected boolean isFinishing() {
		if (!this.t) {
			final boolean t = this.m instanceof Activity
					&& ((Activity) this.m).isFinishing();
			this.t = t;
			if (!t) {
				return false;
			}
		}
		return true;
	}

	private void k() {
		if (!this.C.getBoolean("useObb")) {
			return;
		}
		String[] a;
		for (int length = (a = a((Context) this.m)).length, i = 0; i < length; ++i) {
			final String s;
			final String a2 = a(s = a[i]);
			if (this.C.getBoolean(a2)) {
				this.nativeFile(s);
			}
			this.C.remove(a2);
		}
	}

	private static String[] a(final Context context) {
		final String packageName = context.getPackageName();
		final Vector<String> vector = new Vector<String>();
		int versionCode;
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					packageName, 0).versionCode;
		} catch (PackageManager.NameNotFoundException ex) {
			return new String[0];
		}
		final File file;
		if (Environment.getExternalStorageState().equals("mounted")
				&& (file = new File(Environment.getExternalStorageDirectory()
						.toString() + "/Android/obb/" + packageName)).exists()) {
			if (versionCode > 0) {
				final String string = file + File.separator + "main."
						+ versionCode + "." + packageName + ".obb";
				if (new File(string).isFile()) {
					vector.add(string);
				}
			}
			if (versionCode > 0) {
				final String string2 = file + File.separator + "patch."
						+ versionCode + "." + packageName + ".obb";
				if (new File(string2).isFile()) {
					vector.add(string2);
				}
			}
		}
		final String[] array = new String[vector.size()];
		vector.toArray(array);
		return array;
	}

	private static String a(final String s) {
		byte[] digest = null;
		try {
			final MessageDigest instance = MessageDigest.getInstance("MD5");
			final FileInputStream fileInputStream = new FileInputStream(s);
			final long length = new File(s).length();
			fileInputStream.skip(length - Math.min(length, 65558L));
			final byte[] array = new byte[1024];
			for (int i = 0; i != -1; i = fileInputStream.read(array)) {
				instance.update(array, 0, i);
			}
			digest = instance.digest();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex2) {
		} catch (NoSuchAlgorithmException ex3) {
		}
		if (digest == null) {
			return null;
		}
		final StringBuffer sb = new StringBuffer();
		for (int j = 0; j < digest.length; ++j) {
			sb.append(Integer.toString((digest[j] & 0xFF) + 256, 16).substring(
					1));
		}
		return sb.toString();
	}

	private boolean a(final KeyEvent keyEvent) {
		final int keyCode;
		if ((keyCode = keyEvent.getKeyCode()) == 25 || keyCode == 24) {
			return false;
		}
		this.a(new b() {
			@Override
			public final void a() {
				UnityPlayer.this.nativeInjectEvent(new KeyEvent(keyEvent));
			}
		});
		return true;
	}

	private boolean a(final MotionEvent obtain) {
		if (this.l) {
			return false;
		}
		this.a(new b() {
			@Override
			public final void a() {
				UnityPlayer.this.nativeInjectEvent(MotionEvent.obtain(obtain));
				obtain.recycle();
			}
		});
		return true;
	}

	public boolean onKeyPreIme(final int n, final KeyEvent keyEvent) {
		return this.e && n == 4 && this.a(keyEvent);
	}

	public boolean onKeyMultiple(int i, final int n, final KeyEvent keyEvent) {
		final p j = this.i;
		if (!com.unity3d.player.p.c()) {
			return true;
		}
		if (i != 0) {
			for (i = 0; i < n; ++i) {
				this.a(keyEvent);
			}
		} else {
			this.a(new b() {
				@Override
				public final void a() {
					UnityPlayer.this.nativeKeysPressed(keyEvent.getCharacters());
				}
			});
		}
		return true;
	}

	public boolean onKeyUp(final int n, final KeyEvent keyEvent) {
		return this.a(new KeyEvent(keyEvent));
	}

	public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
		return this.a(new KeyEvent(keyEvent));
	}

	public boolean onTouchEvent(final MotionEvent motionEvent) {
		return this.a(MotionEvent.obtain(motionEvent));
	}

	public boolean onGenericMotionEvent(final MotionEvent motionEvent) {
		return this.a(MotionEvent.obtain(motionEvent));
	}

	static/* synthetic */void r(final UnityPlayer unityPlayer) {
		unityPlayer.nativeSetInputCanceled(true);
	}

	static {
		UnityPlayer.currentActivity = null;
		new o().a();
		UnityPlayer.s = true;
		System.loadLibrary("main");
	}

	private abstract class b implements Runnable {
		@Override
		public final void run() {
			if (!UnityPlayer.this.isFinishing()) {
				this.a();
			}
		}

		public abstract void a();
	}

	private final class a extends Thread {
		volatile boolean a;
		volatile boolean b;

		@Override
		public final void run() {
			this.setName("UnityMain");
			try {
				while (!this.b) {
					synchronized (this) {
						this.wait();
					}
					while (!this.b && !this.a) {
						UnityPlayer.this.executeGLThreadJobs();
						if (!UnityPlayer.this.isFinishing()
								&& !UnityPlayer.this.nativeRender()) {
							UnityPlayer.this.b();
						}
					}
				}
			} catch (InterruptedException ex) {
			}
		}

		public final void a1() {
			this.b = true;
			synchronized (this) {
				this.notify();
			}
		}

		public final void b() {
			this.a = false;
			synchronized (this) {
				this.notify();
			}
		}

		public final void c() {
			this.a = true;
			synchronized (this) {
				this.notify();
			}
		}

	}

}
