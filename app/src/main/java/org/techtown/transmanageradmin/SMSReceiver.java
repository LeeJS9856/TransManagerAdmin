package org.techtown.transmanageradmin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Date;

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive called");
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);
        if(messages.length>0) {
            String sender = messages[0].getOriginatingAddress();
            String content = messages[0].getMessageBody().toString();
            Date date = new Date(messages[0].getTimestampMillis());

            Log.d(TAG, "sender" + sender);
            Log.d(TAG, "content" + content);
            Log.d(TAG, "date" + date);
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];
        for(int i=0;i<objs.length;i++) {
            messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
        }
        return messages;
    }


}