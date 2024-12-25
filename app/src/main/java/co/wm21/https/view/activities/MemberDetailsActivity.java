package co.wm21.https.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.MemberDetailsListModel;
import co.wm21.https.R;
import co.wm21.https.view.adapters.MemberDetailsAdapter;
import co.wm21.https.utils.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnBottomReachedListener;
import co.wm21.https.presenter.interfaces.OnCustomerCareItemClickListner;
import co.wm21.https.presenter.interfaces.OnMemberDetailsView;
import co.wm21.https.presenter.MemberDetailsPresenter;

public class MemberDetailsActivity extends AppCompatActivity implements OnMemberDetailsView, OnCustomerCareItemClickListner {
    private static final int CALL_PHONE_PERMISSION_CODE = 11;
    private RecyclerView memberDetailRecycler;
            private MemberDetailsAdapter memberDetailsAdapter;
    private MemberDetailsPresenter memberDetailsPresenter;
    private List<MemberDetailsListModel> memberDetailsList = new ArrayList<>();
    private int loadMore = 0;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    private String rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        rank = getIntent().getStringExtra("rank");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_memberDetails);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Total "+rank);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        appSessionManager = new SessionHandler(this);
        checkInternetConnection = new CheckInternetConnection();

        Log.d("id",appSessionManager.getUserDetails().getUsername());

        memberDetailRecycler = findViewById(R.id.memberDetailRecycler);
        memberDetailsPresenter = new MemberDetailsPresenter(this);
        initializedRecyclerView();
    }

    @Override
    public void onResponseData(Object object) {
       // MemberDetailsListModel detailsListModel = (MemberDetailsListModel) object;
        memberDetailsList.addAll((List<MemberDetailsListModel>) object);
        memberDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onStopLoading() {

    }

    @Override
    public void onShowMessage(String errMsg) {

    }

    private void initializedRecyclerView(){
        memberDetailsAdapter = new MemberDetailsAdapter(this,memberDetailsList,this);
        memberDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
        memberDetailRecycler.setAdapter(memberDetailsAdapter);
        parseData(0);
        memberDetailsAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                parseData(loadMore = loadMore + 10);
            }
        });
    }

    private void parseData(int loadMoreData) {
        memberDetailsPresenter.getMemberDetailsResponse(appSessionManager.getUserDetails().getUsername(),rank,loadMoreData + ",10");
    }


    private void requestCallphonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("To make a call this permission is needed.")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(MemberDetailsActivity.this,new String[] {Manifest.permission.SEND_SMS},CALL_PHONE_PERMISSION_CODE))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},CALL_PHONE_PERMISSION_CODE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void callToCustomerCare(String number) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" +number));
            startActivity(callIntent);
        }
        else {
            requestCallphonePermission();
        }
    }
}
