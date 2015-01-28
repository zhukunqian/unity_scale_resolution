package com.unity3d.player;

import android.view.*;

public final class c implements e
{
    @Override
    public final boolean a(final View view, final MotionEvent motionEvent) {
        return view.dispatchGenericMotionEvent(motionEvent);
    }
}
