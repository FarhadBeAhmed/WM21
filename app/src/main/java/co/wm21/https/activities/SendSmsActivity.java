package com.wm21ltd.wm21.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.networks.Models.MemberDetailsListModel;

import java.util.ArrayList;

public class SendSmsActivity extends AppCompatActivity {

    private static final int SEND_SMS_PERMISSION_CODE = 11;
    private TextView selectedPersons;
    private EditText msgText;
    private ArrayList<MemberDetailsListModel> selectedItems = new ArrayList<>();
    private SmsManager sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_sendSms);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Send Message");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        selectedPersons = findViewById(R.id.selectedPersons);
        msgText = findViewById(R.id.msgText);

        selectedItems = (ArrayList<MemberDetailsListModel>) getIntent().getExtras().getSerializable("personList");

        //Log.d("person",selectedItems.get(0).getMobile());

        selectedPersons.setText("Selected ("+selectedItems.size()+") persons");
        sms = SmsManager.getDefault();


    }

    public void sendSms(View view) {
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            String message = msgText.getText().toString();
            if (!TextUtils.isEmpty(message)){
                for (int i=0; i<selectedItems.size(); i++){
                    sms.sendTextMessage(selectedItems.get(i).getMobile(), null, message, null, null);
                }
            }
        }
        else {
            requestCallphonePermission();
        }*/


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
