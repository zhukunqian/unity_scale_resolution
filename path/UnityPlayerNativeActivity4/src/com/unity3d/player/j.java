package com.unity3d.player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.app.Activity;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public final class j implements g {
	private final Queue<MotionEvent> a;
	private final Activity b;
	private Runnable c = new Runnable() {
		private void a(final View view, final MotionEvent motionEvent) {
			if (k.b) {
				k.d.a(view, motionEvent);
			}
		}

		@Override
		public final void run() {
			MotionEvent motionEvent;
			while ((motionEvent = j.this.a.poll()) != null) {
				final View decorView = j.this.b.getWindow().getDecorView();
				final int source;
				if (((source = motionEvent.getSource()) & 0x2) != 0x0) {
					switch (motionEvent.getAction() & 0xFF) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6: {
						Log.i("hack unity", "motion event 1:"+motionEvent.getX()+" "+motionEvent.getY());
						decorView.dispatchTouchEvent(motionEvent);
						continue;
					}
					default: {
						Log.i("hack unity", "motion event 2:"+motionEvent.getX()+" "+motionEvent.getY());
						a(decorView, motionEvent);
						continue;
					}
					}
				} else if ((source & 0x4) != 0x0) {
					Log.i("hack unity", "motion event 3:"+motionEvent.getX()+" "+motionEvent.getY());
					decorView.dispatchTrackballEvent(motionEvent);
				} else {
					Log.i("hack unity", "motion event 4:"+motionEvent.getX()+" "+motionEvent.getY());
					a(decorView, motionEvent);
				}
			}
		}
	};;

	public j(final ContextWrapper contextWrapper) {
		this.a = new ConcurrentLinkedQueue();
		this.b = (Activity) contextWrapper;
	}

	private static MotionEvent.PointerCoords[] a(final int n,
			final float[] array) {
		final MotionEvent.PointerCoords[] array2;
		a(array2 = new MotionEvent.PointerCoords[n], array, 0);
		return array2;
	}

	private static int a(final MotionEvent.PointerCoords[] array,
			final float[] array2, int n) {
		
		for (int i = 0; i < array.length; ++i) {
			final int n2 = i;
			final MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
			array[n2] = pointerCoords;
			final MotionEvent.PointerCoords pointerCoords2 = pointerCoords;
			pointerCoords.orientation = array2[n++];
			pointerCoords2.pressure = array2[n++];
			pointerCoords2.size = array2[n++];
			pointerCoords2.toolMajor = array2[n++];
			pointerCoords2.toolMinor = array2[n++];
			pointerCoords2.touchMajor = array2[n++];
			pointerCoords2.touchMinor = array2[n++];
			pointerCoords2.x = array2[n++];
			pointerCoords2.y = array2[n++];
			
			Log.i("hack unity", "motion event 5:"+pointerCoords.x+" "+pointerCoords.y);
		}
		return n;
	}

	@Override
	public final void a(final long n, final long n2, final int n3,
			final int n4, final int[] array, final float[] array2,
			final int n5, final float n6, final float n7, final int n8,
			final int n9, final int n10, final int n11, final int n12,
			final long[] array3, final float[] array4) {
		if (this.b != null) {
			final MotionEvent obtain = MotionEvent.obtain(n, n2, n3, n4, array,
					a(n4, array2), n5, n6, n7, n8, n9, n10, n11);
			int a = 0;
			for (int i = 0; i < n12; ++i) {
				final MotionEvent.PointerCoords[] array5;
				a = a(array5 = new MotionEvent.PointerCoords[n4], array4, a);
				obtain.addBatch(array3[i], array5, n5);
			}
			this.a.add(obtain);
			this.b.runOnUiThread(this.c);
		}
	}
}
