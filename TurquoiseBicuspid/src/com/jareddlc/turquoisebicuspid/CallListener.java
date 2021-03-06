package com.jareddlc.turquoisebicuspid;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallListener extends PhoneStateListener {
    private static final String LOG_TAG = "TurquoiseBicuspid:CallListener";

    private Context context;

    public CallListener(Context cntxt) {
        context = cntxt;
    }

    public void destroy() {
        context = null;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch(state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(LOG_TAG, "Phone: Idle");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(LOG_TAG, "Phone: Offhook");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(LOG_TAG, "Phone: Ringing - "+incomingNumber);
                Intent msg = new Intent("phone");
                msg.putExtra("sender", incomingNumber);
                LocalBroadcastManager.getInstance(context).sendBroadcast(msg);
                break;
        }
    }
}
