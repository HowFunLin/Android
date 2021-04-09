package cn.itcast.registercard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtTXTNumber;
    private EditText edtTXTNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "已被创建");
        initView();//初始化控件
    }

    private void initView() {
        edtTXTNumber = (EditText) findViewById(R.id.edtTxt_number);
        edtTXTNickName = (EditText) findViewById(R.id.edtTxt_nickName);
    }

    public void register(View view) {
        //获取手机号码与昵称
        String number = edtTXTNumber.getText().toString().trim();
        String nickName = edtTXTNickName.getText().toString().trim();

        //手机号码和昵称均不可为空
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(nickName)) {
            Toast.makeText(this, "选项不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //将数据传递给SecondActivity
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("number", number);
        intent.putExtra("nickName", nickName);
        startActivity(intent);
        Log.i("MainActivity", "将数据传递给SecondActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "调用onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MainActivity", "调用onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "调用onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "调用onPause()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("MainActivity", "调用onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "调用onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "调用onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "调用onRestart()");
    }
}
