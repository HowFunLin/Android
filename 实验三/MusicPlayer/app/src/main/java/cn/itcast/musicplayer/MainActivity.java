package cn.itcast.musicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {
    private EditText path;
    private Intent intent;
    private myConn conn;
    MusicService.MyBinder binder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = (EditText) findViewById(R.id.et_inputpath);
        findViewById(R.id.tv_play).setOnClickListener(this);
        findViewById(R.id.tv_pause).setOnClickListener(this);
        conn = new myConn();
        intent = new Intent(this, MusicService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        Toast.makeText(this, "杨柳千条拂面丝，绿烟金穗不胜吹。\n" +
                "香随静婉歌尘起，影伴娇娆舞袖垂。\n" +
                "羌管一声何处曲，流莺百啭最高枝。\n" +
                "千门九陌花如雪，飞过宫墙两自知。", Toast.LENGTH_LONG).show();
    }

    private class myConn implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.MyBinder) service;
        }

        public void onServiceDisconnected(ComponentName name) {
        }
    }

    public void onClick(View v) {
        String pathway = path.getText().toString().trim();
        File SDpath = Environment.getExternalStorageDirectory();
        File file = new File(SDpath, pathway);
        String path = file.getAbsolutePath();
        switch (v.getId()) {
            case R.id.tv_play:
                if (file.exists() && file.length() > 0) {
                    binder.play(path);
                } else {
                    Toast.makeText(this, "找不到音乐文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_pause:
                binder.pause();
                break;
            default:
                break;
        }
    }

    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
