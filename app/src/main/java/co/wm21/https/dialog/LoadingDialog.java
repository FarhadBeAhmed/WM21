package co.wm21.https.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import co.wm21.https.R;

public class LodingDialig {
   private AlertDialog dialog;
    private Activity activity;
    public LodingDialig(Activity myActivity){
        activity=myActivity;

    }
    public void startLoadingAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater= activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.show();
    }
   public void dismissDialog(){
        dialog.dismiss();
    }
}
