package co.wm21.https.view.fragments.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.RewardFundDataListModel;
import co.wm21.https.view.adapters.RewardFundAdapter;
import co.wm21.https.databinding.FragmentRewardFundBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnRewardFundView;
import co.wm21.https.presenter.RewardFundPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFundFragment extends Fragment implements OnRewardFundView {

    View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    RewardFundPresenter rewardFundPresenter;

    FragmentRewardFundBinding binding;



    private List<RewardFundDataListModel> rewardModel = new ArrayList<>();
    private RewardFundAdapter rAdapter;
    LoadingDialog loadingDialog;

    public RewardFundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentRewardFundBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
       loadingDialog=new LoadingDialog(getActivity());
        rewardFundPresenter = new RewardFundPresenter(this);
        initializedFields();
        return binding.rvRewardFund;
    }

    private void initializedFields() {
        parseData();
        rAdapter = new RewardFundAdapter(rewardModel, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvRewardFund.setAdapter(rAdapter);
        binding.rvRewardFund.setLayoutManager(layoutManager);

    }

    private void parseData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardFundPresenter.onRewardFundRequestData(appSessionManager.getUserDetails().getUsername());
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardFundData(List<RewardFundDataListModel> rewardList) {
        rewardModel.clear();
        rewardModel.addAll(rewardList);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardFundStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onRewardFundStopLoading() {
       loadingDialog.dismissDialog();
    }

    @Override
    public void onRewardFundShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
