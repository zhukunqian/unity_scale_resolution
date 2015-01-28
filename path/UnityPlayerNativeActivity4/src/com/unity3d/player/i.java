package com.unity3d.player;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public final class i {
	private final Bundle a;

	public i(Activity componentName) {
		Bundle bundle = Bundle.EMPTY;
		final PackageManager packageManager = componentName.getPackageManager();

		try {
			final ActivityInfo activityInfo;
			if ((activityInfo = packageManager.getActivityInfo(
					componentName.getComponentName(), 128)) != null
					&& activityInfo.metaData != null) {
				bundle = activityInfo.metaData;
			}
		} catch (PackageManager.NameNotFoundException ex) {
			h.Log(6, "Unable to retreive meta data for activity '"
					+ componentName + "'");
		}
		this.a = new Bundle(bundle);
	}

	public final boolean a() {
		return this.a.getBoolean(a("ForwardNativeEventsToDalvik"));
	}

	private static String a(final String s) {
		return String.format("%s.%s", "unityplayer", s);
	}
}
