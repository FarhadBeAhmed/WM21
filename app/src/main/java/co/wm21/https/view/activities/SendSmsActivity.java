package co.wm21.https.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import java.util.ArrayList;

import co.wm21.https.FHelper.networks.Models.MemberDetailsListModel;
import co.wm21.https.R;

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
