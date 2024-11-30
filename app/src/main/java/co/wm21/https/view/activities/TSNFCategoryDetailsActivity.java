package co.wm21.https.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.TSNFCategoryDetailsListModel;
import co.wm21.https.R;
import co.wm21.https.view.adapters.TSNFCategoryDetailsAdapter;
import co.wm21.https.presenter.interfaces.OnBottomReachedListener;
import co.wm21.https.presenter.interfaces.OnTSNFApplyClickListener;
import co.wm21.https.presenter.interfaces.OnTSNFCategoryDetailsListView;
import co.wm21.https.presenter.TSNFCategoryDetailsPresenter;

public class TSNFCategoryDetailsActivity extends AppCompatActivity implements OnTSNFCategoryDetailsListView, OnTSNFApplyClickListener {

    private String categoryName;
    private RecyclerView tsnfRecycler;
    private TSNFCategoryDetailsAdapter tsnfCategoryDetailsAdapter;
    private TSNFCategoryDetailsPresenter tsnfCategoryDetailsPresenter;
    private List<TSNFCategoryDetailsListModel> tsnfCategoryList = new ArrayList<>();
    private int loadMore = 0;
    private ConstraintLayout contextView;
    private MaterialDialog dialog;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_service_news_franchise_category_details);

        tsnfRecycler = findViewById(R.id.tsnf_recycler);
        contextView = findViewById(R.id.tsnf_detail_contextView);

        categoryName = getIntent().getStringExtra("categoryName");
        type = getIntent().getStringExtra("type");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_tsnf_detail);
        setSupportActionBar(myToolbar);
        if (type.equals("1")){
            getSupportActionBar().setTitle("Training Details");
        }else {

            getSupportActionBar().setTitle("Service Details");
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dialog = new MaterialDialog.Builder(this).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();


        tsnfCategoryDetailsPresenter = new TSNFCategoryDetailsPresenter(this);
        initializedRecyclerView();
    }

    @Override
    public void onTSNFCategoryDetailsReqData(List<TSNFCategoryDetailsListModel> tsnfCategoryDetailsListModels) {
        tsnfCategoryList.addAll(tsnfCategoryDetailsListModels);
        tsnfCategoryDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTSNFCategoryDetailsStartLoading() {
        dialog.show();
    }

    @Override
    public void onTSNFCategoryDetailsStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void oonTSNFCategoryDetailsShowMessage(String msg) {
        showSnackBar(msg);
    }


    private void initializedRecyclerView() {
        tsnfCategoryDetailsAdapter = new TSNFCategoryDetailsAdapter(this,type, tsnfCategoryList,this);
        tsnfRecycler.setLayoutManager(new LinearLayoutManager(this));
        tsnfRecycler.setAdapter(tsnfCategoryDetailsAdapter);
        parseData(0);
        tsnfCategoryDetailsAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                parseData(loadMore = loadMore + 10);
            }
        });
    }

    private void parseData(int loadMoreData) {
        tsnfCategoryDetailsPresenter.tsnfCategoryDetailsDataResponse(type, categoryName, loadMoreData + ",10");
    }

    private void showSnackBar(String msg) {

        Snackbar snackbar = Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void tsnfApplyInfo(String serviceId, String CategoryName) {
        Intent intent = new Intent(this,TSNFApplyActivity.class);
        intent.putExtra("serviceId",serviceId);
        intent.putExtra("CategoryName",CategoryName);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
