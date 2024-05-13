package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.FranchiseAccountCommissionAdapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnBottomReachedListener;
import com.wm21ltd.wm21.interfaces.OnFranchiseAccountView;
import com.wm21ltd.wm21.networks.Models.FranchiseAccountDataListModel;
import com.wm21ltd.wm21.presenters.FranchiseAccountCommissionPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseAccountsFragment extends Fragment implements OnFranchiseAccountView {

    View mView;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    MaterialDialog dialog;
    FranchiseAccountCommissionPresenter franchiseAccountCommissionPresenter;

    @BindView(R.id.rv_franchiseCommission)
    RecyclerView recyclerViewFranchiseCommission;
    private List<FranchiseAccountDataListModel> franList = new ArrayList<>();
    private FranchiseAccountCommissionAdapter adapter;
    int loadMore = 0;

    public FranchiseAccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_franchise_accounts, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        franchiseAccountCommissionPresenter = new FranchiseAccountCommissionPresenter(this);
        initializedFields();
        return mView;
    }

    private void initializedFields() {
        adapter = new FranchiseAccountCommissionAdapter(franList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFranchiseCommission.setAdapter(adapter);
        recyclerViewFranchiseCommission.setLayoutManager(layoutManager);
        parseData(0);
        adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                parseData(loadMore = loadMore + 10);
            }
        });
    }

    private void parseData(int loadMoreValue) {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            franchiseAccountCommissionPresenter.onFranchiseAccountRequestData(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID),
                    loadMoreValue + ",10");
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFranchiseAccountData(List<FranchiseAccountDataListModel> dataListModel) {
        if (dataListModel.size()>0){
            franList.addAll(dataListModel);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFranchiseAccountStartLoading() {
        dialog.show();
    }

    @Override
    public void onFranchiseAccountStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onFranchiseAccountShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
