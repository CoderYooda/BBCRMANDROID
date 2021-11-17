package com.yooda.app.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AnswerCallBroadcastReceiver extends BroadcastReceiver {

    final String TAG = "PhoneBroadcast";
    String savedNumber = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            Log.d("TEST :","NUMBER1 =>"+savedNumber);
        }
        else{

            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            //Log.d("CALL_EVENT","СОБЫТИЕ!" + number);

            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            if (number != null && !number.isEmpty() && !number.equals("null")) {
                //onCallStateChanged(context, state, number);
                Log.d("TEST :","NUMBER =>"+number + "Состояние =>" + state);
                return;
            }

        }
    }
}