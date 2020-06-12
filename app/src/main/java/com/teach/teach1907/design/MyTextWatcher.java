package com.teach.teach1907.design;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onMyTextChanged(s, start, before, count);
    }
    @Override
    public void afterTextChanged(Editable s) {

    }

    protected abstract void onMyTextChanged(CharSequence s, int start, int before, int count);
}
