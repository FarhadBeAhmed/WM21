package com.wm21ltd.wm21.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.activities.GenerationDetailsActivity;
import com.wm21ltd.wm21.adapters.GenerationAdapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.GenerationItemClickListner;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.GenerationDataListModel;
import com.wm21ltd.wm21.networks.Models.GenerationDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenerationFragment extends Fragment implements GenerationItemClickListner {

    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;

    @BindView(R.id.rv_generation)
    RecyclerView recyclerViewGen;
    private List<GenerationDataListModel> genList = new ArrayList<>();
    private GenerationAdapter gAdapter;
    View mView;

    public GenerationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_generation, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        initializedList();
        return mView;
    }

    private void initializedList() {
        gAdapter = new GenerationAdapter(genList, getActivity(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewGen.setLayoutManager(layoutManager);
        recyclerViewGen.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGen.setAdapter(gAdapter);
        loadListData();
    }

    private void loadListData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
           /* final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources().getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();*/
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getGeneration(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID)).enqueue(new Callback<GenerationDataModel>() {
                @Override
                public void onResponse(Call<GenerationDataModel> call, Response<GenerationDataModel> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().getError();
                        if (errCount == 0) {
                            genList.clear();
                            genList.addAll(response.body().getTreeInfo());
                            gAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), response.body().getErrorReport(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("GENERATION", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                   // dialog.dismiss();
                }

                @Override
                public void onFailure(Call<GenerationDataModel> call, Throwable t) {
                    //dialog.dismiss();
                    Log.d("GENERATION", "onFailure: " + t.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadListData();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onItemClickListner(String slNumber) {
        Intent goGenerationDetailsActivity = new Intent(getActivity(), GenerationDetailsActivity.class);
        goGenerationDetailsActivity.putExtra("SLNUMBER", slNumber);
        startActivity(goGenerationDetailsActivity);
    }
}
