package com.example.clicker.data;

import android.support.annotation.MainThread;

public interface BaseCallback {
    @MainThread
    void onSuccess();

    @MainThread
    void onError();
}
