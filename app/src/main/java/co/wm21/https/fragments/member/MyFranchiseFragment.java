package com.wm21ltd.wm21.fragments;


import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnMyFranchiseView;
import com.wm21ltd.wm21.presenters.MyFranchiseInfoPresenterr;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFranchiseFragment extends Fragment implements OnMyFranchiseView {

    View mView;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    @BindView(R.id.txt_myFranchise_Type)
    TextView textViewFranchiseType;
    @BindView(R.id.txt_myFranchise_Name)
    TextView textViewFranchiseName;
    @BindView(R.id.txt_myFranchise_MobileNo)
    TextView textViewFranchiseMobileNo;
    @BindView(R.id.txt_myFranchise_Address)
    TextView textViewFranchiseAddress;
    @BindView(R.id.txt_myFranchise_Email)
    TextView textViewFranchiseEmail;

    MaterialDialog dialog;
    MyFranchiseInfoPresenterr myFranchiseInfoPresenterr;


    public MyFranchiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_franchise, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        myFranchiseInfoPresenterr = new MyFranchiseInfoPresenterr(this);
        loadInitialData();
        return mView;
    }

    private void loadInitialData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            myFranchiseInfoPresenterr.onMyFranchiseInfoResponseData(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID));
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadInitialData();
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
    public void onResponseData(HashMap hashMap) {
        textViewFranchiseType.setText("Franchise Type: " + hashMap.get("title").toString());
        textViewFranchiseName.setText("Franchise Name: " + hashMap.get("name").toString());
        textViewFranchiseMobileNo.setText("Mobile No: " + hashMap.get("mobile").toString());
        textViewFranchiseEmail.setText("Email: " + hashMap.get("email").toString());
        textViewFranchiseAddress.setText("Address: " + hashMap.get("addrss").toString());
    }

    @Override
    public void startLoading() {
        dialog.show();
    }

    @Override
    public void stopLoading() {
        dialog.dismiss();
    }

    @Override
    public void showMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
