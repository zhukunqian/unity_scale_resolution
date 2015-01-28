package com.haifi.abc;

import android.app.NativeActivity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.unity3d.player.UnityPlayer3;

/**
 * 只使用反射抓数据
 * 
 * @author ZhuKunqian
 * 
 */
public class UnityPlayerNativeActivity5 extends NativeActivity {
	protected UnityPlayer3 mUnityPlayer; // don't change the name of this
											// variable; referenced from native
											// code
	// UnityPlayer.init() should be called before attaching the view to a layout
	// - it will load the native code.
	// UnityPlayer.quit() should be the last thing called - it will unload the
	// native code.
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("android method", "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		getWindow().takeSurface(null);
		setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
		getWindow().setFormat(PixelFormat.RGB_565);

		mUnityPlayer = new UnityPlayer3(this);
		if (mUnityPlayer.getSettings().getBoolean("hide_status_bar", true))
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);

		int glesMode = mUnityPlayer.getSettings().getInt("gles_mode", 1);
		boolean trueColor8888 = false;
		mUnityPlayer.init(glesMode, trueColor8888);

		int width = getResources().getDisplayMetrics().widthPixels;
		int height = getResources().getDisplayMetrics().heightPixels;

		setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();

		mUnityPlayer._width = width / 2;
		mUnityPlayer._height = height / 2;
		mUnityPlayer._displayWidth = width;
		mUnityPlayer._displayHeight = height;

		mUnityPlayer.setScreenSize(mUnityPlayer._width, mUnityPlayer._height,
				true);
	}

	protected void onDestroy() {
		Log.i("android method", "onDestroy");
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// onPause()/onResume() must be sent to UnityPlayer to enable pause and
	// resource recreation on resume.
	protected void onPause() {
		Log.i("android method", "onPause");
		super.onPause();
		mUnityPlayer.pause();
	}

	protected void onResume() {
		Log.i("android method", "onResume");
		super.onResume();
		mUnityPlayer.resume();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		Log.i("android method", "onConfigurationChanged start");
		super.onConfigurationChanged(newConfig);

		mUnityPlayer.configurationChanged(newConfig);
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		Log.i("android method", "onConfigurationChanged");
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.i("android method", "onConfigurationChanged");
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.onKeyMultiple(event.getKeyCode(),
					event.getRepeatCount(), event);
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("android method", "onTouchEvent");
		Log.i("android method",
				"onTouchEvent X:" + event.getX() + " Y:" + event.getY()
						+ " RawX:" + event.getRawX() + " RawY:"
						+ event.getRawY() + " XPrecision:"
						+ event.getXPrecision() + " YPrecision:"
						+ event.getYPrecision());
		return super.onTouchEvent(event);
	}
}
