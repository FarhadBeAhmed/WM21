package co.wm21.https.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.SearchDataModel;
import co.wm21.https.FHelper.networks.Models.SearchModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.R;
import co.wm21.https.adapters.SearchAdapter;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.interfaces.RecycleViewItemClickListener;
import co.wm21.https.interfaces.SearchSmsCallListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchSmsCallListener, RecycleViewItemClickListener {

    private static final int CALL_PHONE_PERMISSION_CODE = 1;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;

    @BindView(R.id.search_view)
    SearchView searchView;
    List<String> suggestionsList;
    CursorAdapter suggestionAdapter;


    @BindView(R.id.search_catagory_spinner)
    Spinner searchCatagorySpinner;
    String selectedUserCatagory = "Employee";

    SearchAdapter mSearchAdapter;
    @BindView(R.id.search_list_recyclerview)
    RecyclerView recyclerView;
    List<SearchDataModel> finalSearchList = new ArrayList<>();

    List<String> arrList = new ArrayList<>();

    Intent getData;
    private int formType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Search User");
        ButterKnife.bind(this);
        appSessionManager = new SessionHandler(SearchActivity.this);
        getData = getIntent();
        formType = getData.getIntExtra("backResult", 0);
        createSearchCatagorySpinner(formType);
        createSearchView();
        setResltListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void createSearchCatagorySpinner(int formTypes) {
        if (formTypes == 100) {
            arrList.add("Member");
        } else {
            arrList.add("Admin");
            arrList.add("Agent");
            arrList.add("Member");
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.row_spinner_search, arrList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCatagorySpinner.setAdapter(spinnerAdapter);
        searchCatagorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("selected user type ", "" + arrList.get(i));
                selectedUserCatagory = arrList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void createSearchView() {
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorWhite));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorWhite));

        suggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);
        suggestionsList = new ArrayList<>();
        searchView.setSuggestionsAdapter(suggestionAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(suggestionsList.get(position), false);
                searchView.clearFocus();
                Log.e("suggestionClicked", ": " + suggestionsList.get(position));
                getSearchList(suggestionsList.get(position), true);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchList(newText, false);
                return false;
            }
        });

        View closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_view);
                et.setText("");
                finalSearchList.clear();
                mSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    void getSearchList(String suggestionStr, final Boolean suggestoinClicked) {

        final APIService mService = ApiUtils.getApiService(ConstantValues.web_url);
        mService.searchUser(suggestionStr, selectedUserCatagory)
                .enqueue(new Callback<SearchModel>() {

                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        if (response.isSuccessful()) {
                            suggestionsList.clear();
                            for (SearchDataModel mOdel : response.body().getSearchResult()) {
                                String str = mOdel.getName();
                                suggestionsList.add(str);
                            }
                            if (suggestoinClicked) {
                                finalSearchList.clear();
                                finalSearchList.addAll(response.body().getSearchResult());
                                mSearchAdapter.notifyDataSetChanged();
                                return;
                            }
                            String[] columns = {
                                    BaseColumns._ID,
                                    SearchManager.SUGGEST_COLUMN_TEXT_1,
                                    SearchManager.SUGGEST_COLUMN_INTENT_DATA
                            };

                            MatrixCursor cursor = new MatrixCursor(columns);

                            for (int i = 0; i < suggestionsList.size(); i++) {
                                String[] tmp = {Integer.toString(i), suggestionsList.get(i), suggestionsList.get(i)};
                                cursor.addRow(tmp);
                            }
                            suggestionAdapter.swapCursor(cursor);
                        } else {
                            int statusCode = response.code();
                            Log.e("fail ", "fail to download by retrofit");
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {
                        Log.e("error ", "error to download by retrofit");
                    }
                });
    }

    void setResltListView() {
        mSearchAdapter = new SearchAdapter(this, finalSearchList,formType, this,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mSearchAdapter);
    }


    @Override
    public void makeCall(String mobileNumber) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobileNumber));
            startActivity(callIntent);
        } else {
            requestCallphonePermission();
        }
    }

    @Override
    public void makeSms(String mobileNumber) {

        Intent I = new Intent(Intent.ACTION_VIEW);
        I.setData(Uri.parse("smsto:"));
        I.putExtra("address", mobileNumber);
        I.putExtra("sms_body", "");

        if (I.resolveActivity(getPackageManager()) != null) {
            startActivity(I);
            Log.e("Sms_Send", "SMS SEND!");
        } else {
            Log.e("Sms_SEND", "SMS FAIL!");
        }


    }

    private void requestCallphonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("To make a call this permission is needed.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_PHONE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Perission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    public void onItemClick(int position) {
        SearchDataModel sData = finalSearchList.get(position);
            Intent output = new Intent();
            output.putExtra("name", sData.getName());
            output.putExtra("category", selectedUserCatagory);
            output.putExtra("id", sData.getId());
            setResult(RESULT_OK, output);
            finish();
    }
}
