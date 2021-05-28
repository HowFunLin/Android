package cn.itcast.readsms;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private TextView tvSms;
    private TextView tvDes;
    private String text = "";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSms = (TextView) findViewById(R.id.tv_sms);
        tvDes = (TextView) findViewById(R.id.tv_des);
    }
    //点击Button时触发的方法
    public void readSMS(View view) {
        //查询系统信息的uri
        Uri uri = Uri.parse("content://sms/");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasReadSmsPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            if (hasReadSmsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        //获取ContentResolver对象
        ContentResolver resolver = getContentResolver();
        //通过ContentResolver对象查询系统短信
        Cursor cursor = resolver.query(uri, new String[]{ "_id","address",
                "type","body", "date"}, null, null, null);
        List<SmsInfo> smsInfos = new ArrayList<SmsInfo>();
        if (cursor != null && cursor.getCount() > 0) {
            tvDes.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(0);
                String address = cursor.getString(1);
                int type = cursor.getInt(2);
                String body = cursor.getString(3);
                long date = cursor.getLong(4);
                SmsInfo smsInfo = new SmsInfo(_id, address, type, body, date);
                smsInfos.add(smsInfo);
            }
            cursor.close();
        }
        //将查询到的短信内容显示到界面上
        for (int i = 0; i < smsInfos.size(); i++) {
            text += "账单来源：" + smsInfos.get(i).getAddress() + "\n";
            text += "账单内容：\n" + smsInfos.get(i).getBody() + "\n\n";
            tvSms.setText(text);
        }
    }
}
