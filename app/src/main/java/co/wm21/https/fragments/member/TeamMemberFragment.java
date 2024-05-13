package com.wm21ltd.wm21.fragments;


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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.TeamInfoAdapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.TeamInfoDataListModel;
import com.wm21ltd.wm21.networks.Models.TeamInfoDataModel;
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
public class TeamMemberFragment extends Fragment {

    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    View mView;

    @BindView(R.id.rv_teamInfo)
    RecyclerView recyclerViewTeamInfo;
    @BindView(R.id.img_teamInfo_CompanyLogo)
    ImageView imageViewCompanyLogo;
    @BindView(R.id.txt_teamInfo_CompanyName)
    TextView textViewCompanyName;
    @BindView(R.id.txt_teamInfo_CompanyAddress)
    TextView textViewCompanyAddress;
    @BindView(R.id.txt_teamInfo_CompanyPhone)
    TextView textViewCompanyPhone;
    @BindView(R.id.txt_teamInfo_CompanyEmail)
    TextView textViewCompanyEmail;

    @BindView(R.id.txt_teamInfoReport_TeamA)
    TextView textViewHeaderTeamA;
    @BindView(R.id.view_teamInfoReport_TeamA)
    View viewHeaderTeamA;
    @BindView(R.id.txt_teamInfoReport_TeamB)
    TextView textViewHeaderTeamB;
    @BindView(R.id.view_teamInfoReport_TeamB)
    View viewHeaderTeamB;

    private List<TeamInfoDataListModel> dataModel = new ArrayList<>();
    private TeamInfoAdapter teamInfoAdapter;

    public TeamMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_team_info, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();

        Glide.with(getActivity()).load(ConstantValues.URL + "s/"
                + appSessionManager.getUserDetails().get(AppSessionManager.KEY_COMPANYLOGO)).apply(new RequestOptions().error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher).fitCenter()).into(imageViewCompanyLogo);
        textViewCompanyName.setText(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERFULLNAME));
        textViewCompanyAddress.setText(appSessionManager.getUserDetails().get(AppSessionManager.KEY_COMPANYFULLADDRESS));
        textViewCompanyPhone.setText(appSessionManager.getUserDetails().get(AppSessionManager.KEY_PHONEDETAILS));
        textViewCompanyEmail.setText(appSessionManager.getUserDetails().get(AppSessionManager.KEY_COMPANYEMAIL));
        initializedFields();
        return mView;
    }

    private void initializedFields() {
        teamInfoAdapter = new TeamInfoAdapter(dataModel, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTeamInfo.setLayoutManager(layoutManager);
        recyclerViewTeamInfo.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTeamInfo.setAdapter(teamInfoAdapter);
        loadData();
    }

    private void loadData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources().getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTeamInfoData(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID)).enqueue(new Callback<TeamInfoDataModel>() {
                @Override
                public void onResponse(Call<TeamInfoDataModel> call, Response<TeamInfoDataModel> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().getError();
                        if (errCount == 0) {
                            dataModel.clear();
                            dataModel.addAll(response.body().getTeamInfo());
                            teamInfoAdapter.notifyDataSetChanged();
                            String teamAData = response.body().getTeamInfo().get(0).getTeamA();
                            String teamBData = response.body().getTeamInfo().get(0).getTeamB();
                            if (teamAData == null || teamAData.length() <= 0) {
                                textViewHeaderTeamA.setVisibility(View.GONE);
                                viewHeaderTeamA.setVisibility(View.GONE);
                            } else if (teamBData == null || teamBData.length() <= 0) {
                                textViewHeaderTeamB.setVisibility(View.GONE);
                                viewHeaderTeamB.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), response.body().getErrorReport(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("TEAM_INFO", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<TeamInfoDataModel> call, Throwable t) {
                    dialog.dismiss();
                    Log.d("TEAM_INFO", "onFailure: " + t.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadData();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

}
