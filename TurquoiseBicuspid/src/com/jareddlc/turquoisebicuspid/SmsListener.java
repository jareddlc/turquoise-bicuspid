package com.jareddlc.turquoisebicuspid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
//import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {
    private static final String LOG_TAG = "TurquoiseBicuspid:SmsListener";

    //private static final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {            
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");

                for(int i=0; i<pdus.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d(LOG_TAG, "SMS: "+senderNum+" - "+message);
                    Intent msg = new Intent("sms");
                    msg.putExtra("message", message);
                    msg.putExtra("sender", senderNum);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(msg);
                }
            }
            catch(Exception e) {
                Log.e(LOG_TAG, "Error: (Object[]) bundle.get()", e);
            }
        }
        else {
            Log.d(LOG_TAG, "Else bundle != null");
        }
    }
}
