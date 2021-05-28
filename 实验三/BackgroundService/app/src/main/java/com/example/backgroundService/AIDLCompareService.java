package com.example.backgroundService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLCompareService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new AIDLCompareBinder();
    }
}

class AIDLCompareBinder extends AIDLCompare.Stub
{

    @Override
    public int compare(int a, int b) throws RemoteException {
        return Math.max(a, b);
    }
}