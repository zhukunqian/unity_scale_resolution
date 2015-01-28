package com.unity3d.player;

import android.content.*;
import android.location.*;
import java.util.*;
import android.os.*;
import android.hardware.*;

final class l implements LocationListener
{
    private final Context a;
    private final UnityPlayer b;
    private Location c;
    private float d;
    private boolean e;
    private int f;
    private boolean g;
    private int h;
    
    protected l(final Context a, final UnityPlayer b) {
        this.d = 0.0f;
        this.e = false;
        this.f = 0;
        this.g = false;
        this.h = 0;
        this.a = a;
        this.b = b;
    }
    
    public final boolean a() {
        return !((LocationManager)this.a.getSystemService("location")).getProviders(new Criteria(), true).isEmpty();
    }
    
    public final void a(final float d) {
        this.d = d;
    }
    
    public final void b(final float n) {
        if (n < 100.0f) {
            this.f = 1;
            return;
        }
        if (n < 500.0f) {
            this.f = 1;
            return;
        }
        this.f = 2;
    }
    
    public final void b() {
        this.g = false;
        if (this.e) {
            com.unity3d.player.h.Log(5, "Location_StartUpdatingLocation already started!");
            return;
        }
        if (!this.a()) {
            this.a(3);
            return;
        }
        final LocationManager locationManager = (LocationManager)this.a.getSystemService("location");
        this.a(1);
        final List<String> providers;
        if ((providers = locationManager.getProviders(true)).isEmpty()) {
            this.a(3);
            return;
        }
        LocationProvider locationProvider = null;
        if (this.f == 2) {
            final Iterator<String> iterator = providers.iterator();
            while (iterator.hasNext()) {
                final LocationProvider provider;
                if ((provider = locationManager.getProvider((String)iterator.next())).getAccuracy() == 2) {
                    locationProvider = provider;
                    break;
                }
            }
        }
        for (final String s : providers) {
            if (locationProvider == null || locationManager.getProvider(s).getAccuracy() != 1) {
                this.a(locationManager.getLastKnownLocation(s));
                locationManager.requestLocationUpdates(s, 0L, this.d, (LocationListener)this, this.a.getMainLooper());
                this.e = true;
            }
        }
    }
    
    public final void c() {
        ((LocationManager)this.a.getSystemService("location")).removeUpdates((LocationListener)this);
        this.e = false;
        this.c = null;
        this.a(0);
    }
    
    public final void d() {
        if (this.h == 1 || this.h == 2) {
            this.g = true;
            this.c();
        }
    }
    
    public final void e() {
        if (this.g) {
            this.b();
        }
    }
    
    public final void onLocationChanged(final Location location) {
        this.a(2);
        this.a(location);
    }
    
    public final void onStatusChanged(final String s, final int n, final Bundle bundle) {
    }
    
    public final void onProviderEnabled(final String s) {
    }
    
    public final void onProviderDisabled(final String s) {
        this.c = null;
    }
    
    private void a(final Location c) {
        if (c == null) {
            return;
        }
        if (a(c, this.c)) {
            this.c = c;
            this.b.nativeSetLocation((float)c.getLatitude(), (float)c.getLongitude(), (float)c.getAltitude(), c.getAccuracy(), c.getTime() / 1000.0, new GeomagneticField((float)this.c.getLatitude(), (float)this.c.getLongitude(), (float)this.c.getAltitude(), this.c.getTime()).getDeclination());
        }
    }
    
    private static boolean a(final Location location, final Location location2) {
        if (location2 == null) {
            return true;
        }
        final long n;
        final boolean b = (n = location.getTime() - location2.getTime()) > 120000L;
        final boolean b2 = n < -120000L;
        final boolean b3 = n > 0L;
        if (b) {
            return true;
        }
        if (b2) {
            return false;
        }
        final int n2;
        final boolean b4 = (n2 = (int)(location.getAccuracy() - location2.getAccuracy())) > 0;
        final boolean b5 = n2 < 0;
        final boolean b6 = n2 > 200 | location.getAccuracy() == 0.0f;
        final boolean a = a(location.getProvider(), location2.getProvider());
        return b5 || (b3 && !b4) || (b3 && !b6 && a);
    }
    
    private static boolean a(final String s, final String s2) {
        if (s == null) {
            return s2 == null;
        }
        return s.equals(s2);
    }
    
    private void a(final int h) {
        this.h = h;
        this.b.nativeSetLocationStatus(h);
    }
}
