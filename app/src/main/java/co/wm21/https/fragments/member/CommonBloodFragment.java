package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonObject;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Remote.APIService;
import com.wm21ltd.wm21.stores.AppSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonBloodFragment extends Fragment {


    private AppSessionManager appSessionManager;

    public CommonBloodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_blood, container, false);

        appSessionManager = new AppSessionManager(getContext());

        final Spinner bloodGroupSpnr = (Spinner) view.findViewById(R.id.spnr_BloodGroup);
        Button btnSubmitRequest = (Button) view.findViewById(R.id.btn_bloodRequest);

        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getBlood = bloodGroupSpnr.getSelectedItem().toString().trim();
                submitRequest(getBlood);
            }
        });

        return view;
    }


    private void submitRequest(String bloodName) {
        final MaterialDialog dialog = new MaterialDialog.Builder(getContext()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .show();

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.submitBloodRequest(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERNAME),
                appSessionManager.getUserDetails().get(AppSessionManager.KEY_CATEGORY),
                bloodName).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    int errorCount = response.body().get("error").getAsInt();
                    if (errorCount == 0) {
                        Toast.makeText(getContext(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("BLOODREQ", response.code() + "! Error");
                    Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.d("BLOODREQ", "onFailure: " + t.getMessage());
            }
        });
    }

}
