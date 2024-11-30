package co.wm21.https.view.fragments.member;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryData;
import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryResponse;
import co.wm21.https.view.adapters.WithdrawalHistoryAdapter;
import co.wm21.https.databinding.FragmentWithdrawalBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.transaction.OnWithdrawalHistoryView;
import co.wm21.https.presenter.transaction.WithdrawalHistoryPresenter;


public class WithdrawalHistoryFragment extends Fragment implements OnWithdrawalHistoryView {

    FragmentWithdrawalBinding binding;
    CheckInternetConnection checkInternetConnection;
    User user;
    private List<WithdrawalHistoryData> listData = new ArrayList<>();
    private WithdrawalHistoryAdapter withdrawalHistoryAdapterdapter;
    WithdrawalHistoryPresenter presenter;
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentWithdrawalBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment

        checkInternetConnection = new CheckInternetConnection();
        user = new User(getContext());
        presenter=new WithdrawalHistoryPresenter(this);
        loadingDialog = new LoadingDialog(getActivity());

        loadIncomeBalanceData();

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadIncomeBalanceData() {
        withdrawalHistoryAdapterdapter = new WithdrawalHistoryAdapter(listData, requireContext(), item -> {});


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerViewId.setLayoutManager(layoutManager);
        binding.recyclerViewId.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewId.setAdapter(withdrawalHistoryAdapterdapter);


        if (checkInternetConnection.isInternetAvailable(getActivity())) {

            presenter.getDataLoad(user.getUsername());

        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadIncomeBalanceData();
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
    public void onWithdrawalHistoryDataLoad(WithdrawalHistoryResponse response) {
        loadingDialog.dismissDialog();
        listData.clear();
        listData.addAll(response.getData());
        withdrawalHistoryAdapterdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onStopLoading() {
        loadingDialog.dismissDialog();

    }

    @Override
    public void onError(String errmsg) {
        loadingDialog.dismissDialog();

    }
}