package com.example.backgroundService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class CompareService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CompareBinder();
    }
}

class CompareBinder extends Binder
{
    public int compare(int a, int b)
    {
        return Math.max(a, b);
    }
}