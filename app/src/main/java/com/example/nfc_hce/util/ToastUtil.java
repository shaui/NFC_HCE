package com.example.nfc_hce.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static Toast mToast;
    public static void showMsg(Context context, String msg){
        /*如果還沒有設置Toast,就返回一個Toast來用,並設置訊息*/
        if(mToast == null){
            mToast = Toast.makeText(context, msg,Toast.LENGTH_LONG);
        }
        /*如果有設置Toast,就純粹設定msg*/
        else{
            /*因為這裡純粹setText，並沒有重新回傳一個Toast，所以時間疊加的問題就解決了*/
            mToast.setText(msg);
        }

        mToast.show();
    }

}