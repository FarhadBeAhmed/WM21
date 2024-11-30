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

import co.wm21.https.FHelper.networks.Models.application.EShopStatusData;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentEShopStatusBinding;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.application.EShopStatusPresenter;
import co.wm21.https.presenter.interfaces.aplication.OnEShopStatusView;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.adapters.WithdrawalHistoryAdapter;
import co.wm21.https.view.adapters.application.EShopStatusAdapter;


public class EShopStatusFragment extends Fragment implements OnEShopStatusView {

  FragmentEShopStatusBinding binding;
  EShopStatusPresenter presenter;
  SessionHandler appSessionManager;
  CheckInternetConnection checkInternetConnection;
  User user;
  LoadingDialog loadingDialog;
  List<EShopStatusData> listData=new ArrayList<>();
  EShopStatusAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentEShopStatusBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment

        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        loadingDialog=new LoadingDialog(getActivity());
        user = new User(getContext());
        presenter= new EShopStatusPresenter(this);
        loadIncomeBalanceData();

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadIncomeBalanceData() {
        adapter = new EShopStatusAdapter(listData, requireContext(), item -> {});


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);


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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onEShopStatusDataLoad(EShopStatusResponse response) {
        listData.clear();
        listData.addAll(response.getData());
        adapter.notifyDataSetChanged();
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