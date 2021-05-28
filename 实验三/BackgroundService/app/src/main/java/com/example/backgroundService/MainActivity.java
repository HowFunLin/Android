package com.example.backgroundService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CompareBinder binder;
    private AIDLCompare compare;
    private ServiceConnection connection1;
    private ServiceConnection connection2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

        connection1 = new CompareConnection();
        Intent intent1 = new Intent(MainActivity.this, CompareService.class);
        bindService(intent1, connection1, BIND_AUTO_CREATE);

        connection2 = new AIDLCompareConnection();
        Intent intent2 = new Intent(MainActivity.this, AIDLCompareService.class);
        bindService(intent2, connection2, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        EditText text1 = findViewById(R.id.num1);
        EditText text2 = findViewById(R.id.num2);

        int num1 = Integer.parseInt(text1.getText().toString());
        int num2 = Integer.parseInt(text2.getText().toString());
        int max = 0;

        if (v.getId() == R.id.button1)
        {
            max = binder.compare(num1, num2);
        }
        else if (v.getId() == R.id.button2){
            try {
                max = compare.compare(num1, num2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        TextView textView = findViewById(R.id.maxNum);
        textView.setText(String.valueOf(max));
    }

    private class CompareConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (CompareBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private class AIDLCompareConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            compare = AIDLCompare.Stub.asInterface(service) ;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection1);
        unbindService(connection2);
        super.onDestroy();
    }
}