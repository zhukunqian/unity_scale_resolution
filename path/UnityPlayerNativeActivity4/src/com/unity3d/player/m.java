package com.unity3d.player;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.text.method.*;
import android.text.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

public final class m extends Dialog implements TextWatcher, View.OnClickListener
{
    private Context a;
    private UnityPlayer b;
    
    public m(final Context a, final UnityPlayer b, final String s, final int n, final boolean b2, final boolean b3, final boolean b4, final String s2) {
        super(a);
        this.a = null;
        this.b = null;
        this.a = a;
        this.b = b;
        this.getWindow().setGravity(80);
        this.getWindow().requestFeature(1);
        this.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.setContentView(this.createSoftInputView());
        this.getWindow().clearFlags(2);
        final EditText editText = (EditText)this.findViewById(1057292289);
        final Button button = (Button)this.findViewById(1057292290);
        this.a(editText, s, n, b2, b3, b4, s2);
        button.setOnClickListener((View.OnClickListener)this);
        editText.setOnFocusChangeListener((View.OnFocusChangeListener)new View.OnFocusChangeListener() {
            public final void onFocusChange(final View view, final boolean b) {
                if (b) {
                    m.this.getWindow().setSoftInputMode(5);
                }
            }
        });
    }
    
    private void a(final EditText editText, final String text, final int n, final boolean b, final boolean b2, final boolean b3, final String hint) {
        editText.setImeOptions(6);
        editText.setText((CharSequence)text);
        editText.setHint((CharSequence)hint);
        editText.setInputType(a(n, b, b2, b3));
        editText.addTextChangedListener((TextWatcher)this);
        final int inputType = editText.getInputType();
        editText.setKeyListener((KeyListener)TextKeyListener.getInstance());
        editText.setRawInputType(inputType);
        editText.setClickable(true);
        if (!b2) {
            editText.selectAll();
        }
    }
    
    public final void afterTextChanged(final Editable editable) {
        this.b.reportSoftInputStr(editable.toString(), 0, false);
    }
    
    public final void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
    }
    
    public final void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
    }
    
    private static int a(final int n, final boolean b, final boolean b2, final boolean b3) {
        final int n2 = (b ? 32768 : 0) | (b2 ? 131072 : 0) | (b3 ? 128 : 0);
        if (n < 0 || n > 7) {
            return n2;
        }
        return n2 | (new int[] { 1, 16385, 12290, 17, 2, 3, 97, 33 })[n];
    }
    
    private void a(final String s, final boolean b) {
        this.b.reportSoftInputStr(s, 1, b);
    }
    
    public final void onClick(final View view) {
        this.a(this.a(), false);
    }
    
    public final void onBackPressed() {
        this.a(this.a(), true);
    }
    
    protected final View createSoftInputView() {
        final RelativeLayout relativeLayout;
        (relativeLayout = new RelativeLayout(this.a)).setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        final EditText editText = new EditText(this.a) {
            public final boolean onKeyPreIme(final int n, final KeyEvent keyEvent) {
                if (n == 4) {
                    m.this.a(m.this.a(), true);
                }
                return n == 84 || super.onKeyPreIme(n, keyEvent);
            }
            
            public final void onWindowFocusChanged(final boolean b) {
                super.onWindowFocusChanged(b);
                if (b) {
                    ((InputMethodManager)m.this.a.getSystemService("input_method")).showSoftInput((View)this, 0);
                }
            }
        };
        final RelativeLayout.LayoutParams layoutParams;
        (layoutParams = new RelativeLayout.LayoutParams(-1, -2)).addRule(15);
        layoutParams.addRule(0, 1057292290);
        editText.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        editText.setId(1057292289);
        relativeLayout.addView((View)editText);
        final Button button;
        (button = new Button(this.a)).setText(this.a.getResources().getIdentifier("ok", "string", "android"));
        final RelativeLayout.LayoutParams layoutParams2;
        (layoutParams2 = new RelativeLayout.LayoutParams(-2, -2)).addRule(15);
        layoutParams2.addRule(11);
        button.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        button.setId(1057292290);
        relativeLayout.addView((View)button);
        final RelativeLayout relativeLayout2;
        ((EditText)((View)(relativeLayout2 = relativeLayout)).findViewById(1057292289)).setOnEditorActionListener((TextView.OnEditorActionListener)new TextView.OnEditorActionListener() {
            public final boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                if (n == 6) {
                    m.this.a(m.this.a(), false);
                }
                return false;
            }
        });
        return (View)relativeLayout2;
    }
    
    private String a() {
        final EditText editText;
        if ((editText = (EditText)this.findViewById(1057292289)) == null) {
            return null;
        }
        return editText.getText().toString().trim();
    }
    
    public final void a(final String text) {
        final EditText editText;
        if ((editText = (EditText)this.findViewById(1057292289)) != null) {
            editText.setText((CharSequence)text);
            editText.setSelection(text.length());
        }
    }
}
