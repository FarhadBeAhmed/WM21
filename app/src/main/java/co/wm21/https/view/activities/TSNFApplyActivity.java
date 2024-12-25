package co.wm21.https.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.R;
import co.wm21.https.utils.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnFranchiseApplicationView;
import co.wm21.https.presenter.FranchiseApplicationPresenter;

public class TSNFApplyActivity extends AppCompatActivity implements View.OnClickListener, OnFranchiseApplicationView {

    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    private MaterialDialog dialog;
    FranchiseApplicationPresenter franchiseApplicationPresenter;

    @BindView(R.id.et_trainingApp_Name)
    EditText editTextName;
    @BindView(R.id.et_trainingApp_Address)
    EditText editTextAddress;
    @BindView(R.id.btn_trainingApp_Apply)
    Button buttonApply;

    private String categoryName;
    private String type;
    private String serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsnfapply);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_trainingApplication);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Training Application");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);
        appSessionManager = new SessionHandler(TSNFApplyActivity.this);
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(this).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();

        categoryName = getIntent().getStringExtra("categoryName");
        serviceId = getIntent().getStringExtra("serviceId");
        type = getIntent().getStringExtra("type");

        franchiseApplicationPresenter = new FranchiseApplicationPresenter(this);
        buttonApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_trainingApp_Apply:
                String tempFranName = editTextName.getText().toString().trim();
                String tempFranAddress = editTextAddress.getText().toString().trim();
                if (tempFranName.length() == 0) {
                    editTextName.setError("Blank!");
                    return;
                }

                if (tempFranAddress.length() == 0) {
                    editTextAddress.setError("Blank!");
                    return;
                }

                if (checkInternetConnection.isInternetAvailable(TSNFApplyActivity.this)) {
                    franchiseApplicationPresenter.onFranchiseApplicationResponseData(serviceId, appSessionManager.getUserDetails().getUsername(),
                            type, "", "", "", tempFranName, tempFranAddress, "", "");
                } else {
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onFranchiseApplicationData(HashMap hashMap) {
        String errCode = hashMap.get("error").toString();
        if (errCode.equals("0")) {
            Toast.makeText(TSNFApplyActivity.this, hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextAddress.setText("");
        } else {
            Toast.makeText(TSNFApplyActivity.this, hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFranchiseApplicationStartLoading() {
        dialog.show();
    }

    @Override
    public void onFranchiseApplicationStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onFranchiseApplicationShowMessage(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
