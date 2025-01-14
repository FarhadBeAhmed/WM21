package co.wm21.https.view.fragments.registration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.Status;

public class MySmsBroadcastReceiver extends BroadcastReceiver {
    private SmsListener smsListener;

    public MySmsBroadcastReceiver(SmsListener listener) {
        this.smsListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (status.getStatusCode()) {
                case com.google.android.gms.common.api.CommonStatusCodes.SUCCESS:
                    // Successfully retrieved SMS
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    if (smsListener != null) {
                        smsListener.onSmsReceived(message);
                    }
                    break;

                case com.google.android.gms.common.api.CommonStatusCodes.TIMEOUT:
                    // Timeout waiting for SMS
                    Toast.makeText(context, "SMS Timeout", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public interface SmsListener {
        void onSmsReceived(String message);
    }
}
