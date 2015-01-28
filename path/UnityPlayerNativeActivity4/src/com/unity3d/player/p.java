package com.unity3d.player;

final class p
{
    private static boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    
    p() {
        this.b = false;
        this.c = false;
        this.d = true;
    }
    
    static void a() {
        p.a = true;
    }
    
    static void b() {
        p.a = false;
    }
    
    static boolean c() {
        return p.a;
    }
    
    final void a(final boolean b) {
        this.b = b;
    }
    
    final void b(final boolean d) {
        this.d = d;
    }
    
    final boolean d() {
        return this.d;
    }
    
    final void c(final boolean c) {
        this.c = c;
    }
    
    final boolean e() {
        return p.a && this.b && !this.d && !this.c;
    }
    
    final boolean f() {
        return this.c;
    }
    
    @Override
    public final String toString() {
        return super.toString();
    }
    
    static {
        p.a = false;
    }
}
