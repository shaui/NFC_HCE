package com.example.nfc_hce;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nfc_hce.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private Button btn_I017, btn_I204, btn_stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PackageManager pm = getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)){
            ToastUtil.showMsg(this,"Can use the Card Emulation");
        }else {
            ToastUtil.showMsg(this,"Cannot use the Card Emulation");
        }
        /*詢問是否設定為預設付款模式*/
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        CardEmulation mCardEmulation = CardEmulation.getInstance(adapter);
        ComponentName myComponent = new ComponentName("com.example.nfc_hce",
                "com.example.nfc_hce.MyHostApduService");
        if (!mCardEmulation.isDefaultServiceForCategory(myComponent, CardEmulation.CATEGORY_PAYMENT)) {
            Intent intent = new Intent(CardEmulation.ACTION_CHANGE_DEFAULT);
            intent.putExtra(CardEmulation.EXTRA_CATEGORY, CardEmulation.CATEGORY_PAYMENT);
            intent.putExtra(CardEmulation.EXTRA_SERVICE_COMPONENT, myComponent);
            startActivityForResult(intent, 0);
        } else {
            Log.e("MainActivityHost", "on Create: Already default!");
        }

        Intent intent = new Intent(this, MyHostApduService.class);
        Bundle bundle = new Bundle();
        bundle.putString("msg", "1824247328");
        intent.putExtras(bundle);
        startService(intent);

        btn_I017 = findViewById(R.id.btn_I017);
        btn_I204 = findViewById(R.id.btn_I204);
        btn_stopService = findViewById(R.id.btn_stopService);
        btn_I017.setOnClickListener(new ActiveSendMsgListener());
        btn_I204.setOnClickListener(new ActiveSendMsgListener());
        btn_stopService.setOnClickListener(new ActiveSendMsgListener());

//        MyHostApduService hostApduService = new MyHostApduService(this);
//        String mgs = "I017";
//        hostApduService.sendResponseApdu(mgs.getBytes());
    }

    class ActiveSendMsgListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_I017:
                    Intent intent1 = new Intent(MainActivity.this, MyHostApduService.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("msg", "1824247328");
                    intent1.putExtras(bundle1);
                    startService(intent1);
                    ToastUtil.showMsg(MainActivity.this, "1824247328");
                    break;
                case R.id.btn_I204:
                    Intent intent2 = new Intent(MainActivity.this, MyHostApduService.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("msg", "6666666666");
                    intent2.putExtras(bundle2);
                    startService(intent2);
                    ToastUtil.showMsg(MainActivity.this, "6666666666");
                    break;
                case R.id.btn_stopService:

                    Intent intent3 = new Intent(MainActivity.this, MyHostApduService.class);
                    stopService(intent3);
                    ToastUtil.showMsg(MainActivity.this, "Stop");
                    Log.d("---Stop---", "stop service");

                    break;
            }
        }
    }


}
