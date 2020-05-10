package com.example.nfc_hce;


import android.content.Context;
import android.content.Intent;

import android.nfc.cardemulation.HostApduService;

import android.os.Bundle;

import android.util.Log;

import com.example.nfc_hce.util.ToastUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MyHostApduService extends HostApduService {

    private Context context;
    public static final byte[] SELECT_AID_COMMAND = {
            (byte) 0x00,
            (byte) 0xA4,
            (byte) 0x04,
            (byte) 0x00,
            (byte) 0x07,
            (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x47, (byte) 0x10, (byte) 0x01,
    };
    public static final byte[] REQUEST_CARD_NUMBER = {
            (byte) 0x80,
            (byte) 0x01,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x01,
            (byte) 0x64
    };


    public MyHostApduService(){

    }

    public MyHostApduService(Context context){
        this.context = context;
    }
    /*Document*/
    //https://developer.android.com/reference/android/nfc/cardemulation/HostApduService.html

    //NFC reader send a AID
    /*Parameter*/
    /*byte[] commandApdu : byte: The APDU that was received from the remote device
    /*Bundle extras : Bundle: A bundle containing extra data. May be null.*/

    /*Return*/
    /*byte[] : a byte-array containing the response APDU, or null if no response APDU can be sent at this point. */

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("---onCreate---", "create service");
//        if(!isSnedMsg){
//            Log.d("---onCreate---","send message");
//            sendResponseApdu("I017".getBytes());
//            isSnedMsg = true;
//        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("---onStartCommand---", "onStartCommand");
        Bundle bundle = intent.getExtras();
        String msg = bundle.getString("msg");
//        String msg = "1824247328";

        final byte[] answer = new byte[7];
        answer[0] = (byte) 0x90;
        answer[1] = (byte) 0x00;
        answer[2] = (byte) 0x20;
        answer[3] = (byte) 0x20;
        answer[4] = (byte) 0x20;
        answer[5] = (byte) 0x20;
        answer[6] = (byte) 0x20;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("---onStartCommand---","send message " + answer.toString());
                sendResponseApdu(answer);
            }
        }).start();

        //        sendResponseApdu(msg.getBytes());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        final byte[] answer = new byte[2];

        if(Arrays.equals(SELECT_AID_COMMAND, commandApdu)){
            Log.d("--processCommandApdu--", "SELECT_AID_COMMAND is right");
            ToastUtil.showMsg(context, "SELECT_AID_COMMAND is right");
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            return answer;
        } else if(Arrays.equals(REQUEST_CARD_NUMBER, commandApdu)){
            Log.d("--processCommandApdu--", "REQUEST_CARD_NUMBER is success");
            ToastUtil.showMsg(context, "REQUEST_CARD_NUMBER is success");
            answer[0] = (byte) 0x66;
            answer[1] = (byte) 0x66;
            return answer;
        } else {
            Log.d("--processCommandApdu--", "SELECT_AID_COMMAND is false");
            ToastUtil.showMsg(context, "SELECT_AID_COMMAND is false");
        }
        ToastUtil.showMsg(context, "processCommandApdu");
        String msg = "1824247328";

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtil.showMsg(context, answer.toString());
//                Log.d("--processCommandApdu--",answer.toString());
//                sendResponseApdu(answer);
//            }
//        }).start();

//        sendResponseApdu(msg.getBytes());
        ToastUtil.showMsg(context, ByteArrayToHexString(answer));
        Log.d("--processCommandApdu--",ByteArrayToHexString(answer));
        return null;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.d("---onDeactivated---","onDeactivated: link is connectionless");
    }

    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }



}
