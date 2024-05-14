package co.wm21.https.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import co.wm21.https.R;

public class LoadingDialog {
   private AlertDialog dialog;
    private Activity activity;
    public LoadingDialog(Activity myActivity){
        activity=myActivity;

    }
    public void startLoadingAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater= activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.home_progress_dialog,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.getWindow().setLayout(200, 200);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
   public void dismissDialog(){
        dialog.dismiss();
    }
}
