package com.wm21ltd.wm21.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;


public class TransientDialog {

    private Context mContext;

    public TransientDialog(Context mContext){
        this.mContext = mContext;
    }

    public void showTransientDialogWithOutAction(String title, String message){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext)
                .setTitle(title).setMessage(message);
        final AlertDialog alert = dialog.create();
        alert.show();

        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 2000);
    }
}
