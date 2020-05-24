package com.example.clicker.data;

import androidx.annotation.MainThread;

public interface BaseCallback {
    @MainThread
    void onSuccess();

    @MainThread
    void onError();
}
