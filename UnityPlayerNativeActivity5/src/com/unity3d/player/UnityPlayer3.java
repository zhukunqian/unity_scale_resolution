package com.unity3d.player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.util.Log;
import android.view.SurfaceView;
import android.view.inputmethod.InputMethodManager;

public class UnityPlayer3 extends UnityPlayer {

	private Method method = null;

	public int _width;
	public int _height;
	public int _displayWidth;
	public int _displayHeight;

	public UnityPlayer3(ContextWrapper arg0) {
		super(arg0);
		Log.i("unity", "UnityPlayer2 init");
	}

	public void triggerResizeCall() {
		Log.i("hack unity", "UnityPlayer2 triggerResizeCall");
		Log.i("hack unity", "UnityPlayer2 triggerResizeCall:" + _width + " "
				+ _height + " " + _displayWidth + " " + _displayHeight);
		try {
			if (method == null) {
				// private final native void nativeResize(final int p0, final
				// int p1,
				// final int p2, final int p3);
				method = UnityPlayer.class.getDeclaredMethod("nativeResize",
						int.class, int.class, int.class, int.class);
				method.setAccessible(true);
			}
			method.invoke(this, _width, _height, _displayWidth, _displayHeight);
		} catch (Exception e) {
			Log.e("hack unity", "triggerResizeCall :" + e.toString(), e);
		}

		try {
			throw new RuntimeException("full stacktrace");
		} catch (Exception e) {
			Log.e("hack unity", "triggerResizeCall exception:" + e.toString(),
					e);
		}
	}

	public void configurationChanged(Configuration configuration) {
		Log.i("unity", "UnityPlayer2 onConfigurationChanged");
		Log.i("android method",
				"UnityPlayer2 configurationChanged start view layout width:"
						+ this.getLayoutParams().width + " height:"
						+ this.getLayoutParams().height);
		try {
			SurfaceView _n;
			boolean _e = false;
			q _E;
			ContextWrapper _m;

			Field field = UnityPlayer.class.getDeclaredField("n");
			field.setAccessible(true);
			_n = (SurfaceView) field.get(this);

			field = UnityPlayer.class.getDeclaredField("e");
			field.setAccessible(true);
			_e = field.getBoolean(this);

			field = UnityPlayer.class.getDeclaredField("E");
			field.setAccessible(true);
			_E = (q) field.get(this);

			field = UnityPlayer.class.getDeclaredField("m");
			field.setAccessible(true);
			_m = (ContextWrapper) field.get(this);

			if (_n instanceof SurfaceView) {
				_n.getHolder().setFixedSize(_width, _height);
				// triggerResizeCall();
			}
			if (_e && configuration.hardKeyboardHidden == 2) {
				((InputMethodManager) _m.getSystemService("input_method"))
						.toggleSoftInput(0, 1);
			}
			if (_E != null) {
				_E.updateVideoLayout();
			}
		} catch (Exception e) {
			Log.e("hack unity", "configurationChanged :" + e.toString(), e);
		}
	}
	
	public void setScreenSize(final int n, final int n2, final boolean bl) {
		Log.i("hack unity", "UnityPlayer2 setScreenSize: width:" + n + " height:"
				+ n2 + " fullScreen:" + bl);
		super.setScreenSize(n, n2, bl);
	}

}
