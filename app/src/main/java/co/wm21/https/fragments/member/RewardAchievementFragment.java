package co.wm21.https.fragments.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel;
import co.wm21.https.R;
import co.wm21.https.adapters.RewardAchievementAdapter;
import co.wm21.https.databinding.FragmentAchievementBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.interfaces.OnRewardAchievementView;
import co.wm21.https.presenter.RewardAchievementPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardAchievementFragment extends Fragment implements OnRewardAchievementView {

    View mView;
    CheckInternetConnection checkInternetConnection;
    SessionHandler appSessionManager;
    LoadingDialog loadingDialog;

    FragmentAchievementBinding binding;

    private List<RewardAchievementDataListModel> dataListModels = new ArrayList<>();
    private RewardAchievementAdapter mAdapter;
    RewardAchievementPresenter rewardAchievementPresenter;

    public RewardAchievementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAchievementBinding.inflate(getLayoutInflater());

        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        loadingDialog=new LoadingDialog(getActivity());
        rewardAchievementPresenter = new RewardAchievementPresenter(this);
        initializedFields();
        return binding.getRoot();
    }

    private void initializedFields() {
        mAdapter = new RewardAchievementAdapter(dataListModels, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvRewardAchievement.setAdapter(mAdapter);
        binding.rvRewardAchievement.setLayoutManager(layoutManager);
        parseData();
    }

    private void parseData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardAchievementPresenter.onRewardAchievementResponseData(appSessionManager.getUserDetails().getUsername());
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardAchievementData(List<RewardAchievementDataListModel> achievementData) {
        dataListModels.clear();
        dataListModels.addAll(achievementData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardAchievementStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onRewardAchievementStopLoading() {
       loadingDialog.dismissDialog();
    }

    @Override
    public void onRewardAchievementShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
