package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnFranchiseApplicationView;
import com.wm21ltd.wm21.presenters.FranchiseApplicationPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardRegistrationFragment extends Fragment implements OnFranchiseApplicationView, View.OnClickListener {

    View mView;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    private MaterialDialog dialog;
    FranchiseApplicationPresenter franchiseApplicationPresenter;

    @BindView(R.id.et_rewardRegistation_Category)
    EditText editTextCategory;
    @BindView(R.id.et_rewardRegistation_Name)
    EditText editTextName;
    @BindView(R.id.et_rewardRegistation_Address)
    EditText editTextAddress;
    @BindView(R.id.btn_rewardRegistation_Apply)
    Button buttonApply;

    public RewardRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reward_registration, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();

        franchiseApplicationPresenter = new FranchiseApplicationPresenter(this);
        buttonApply.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rewardRegistation_Apply:
                String tempCategory = editTextCategory.getText().toString().trim();
                String tempFranName = editTextName.getText().toString().trim();
                String tempFranAddress = editTextAddress.getText().toString().trim();
                if (tempCategory.length() == 0) {
                    editTextCategory.setError("Blank!");
                    return;
                }

                if (tempFranName.length() == 0) {
                    editTextName.setError("Blank!");
                    return;
                }

                if (tempFranAddress.length() == 0) {
                    editTextAddress.setError("Blank!");
                    return;
                }

                if (checkInternetConnection.isInternetAvailable(getActivity())) {
                    franchiseApplicationPresenter.onFranchiseApplicationResponseData("0", appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID),
                            "6", "", "", "", tempFranName, tempFranAddress, "", tempCategory);
                } else {
                    Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onFranchiseApplicationData(HashMap hashMap) {
        String errCode = hashMap.get("error").toString();
        if (errCode.equals("0")) {
            Toast.makeText(getActivity(), hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
            editTextCategory.setText("");
            editTextName.setText("");
            editTextAddress.setText("");
        } else {
            Toast.makeText(getActivity(), hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }


}
