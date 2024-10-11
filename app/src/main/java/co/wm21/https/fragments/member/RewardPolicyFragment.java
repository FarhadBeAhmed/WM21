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
import co.wm21.https.R;
import co.wm21.https.adapters.RewardPolicyAdapter;
import co.wm21.https.databinding.FragmentRewardPolicyBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.fragments.member.model.RewardPolicyDataListModel;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnRewardPolicyView;
import co.wm21.https.presenter.RewardPolicyPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardPolicyFragment extends Fragment implements OnRewardPolicyView {

    View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    RewardPolicyPresenter rewardPolicyPresenter;
    User user;
   // RecyclerView recyclerViewRewardPolicy;
    FragmentRewardPolicyBinding binding;
    private List<RewardPolicyDataListModel> rewardsModel = new ArrayList<>();
    private RewardPolicyAdapter rAdapter;
  LoadingDialog loadingDialog;
    public RewardPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mView = inflater.inflate(R.layout.fragment_reward_policy, container, false);
       // ButterKnife.bind(this, mView);
        binding=FragmentRewardPolicyBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        loadingDialog=new LoadingDialog(getActivity());
        user = new User(getContext());
        rewardPolicyPresenter = new RewardPolicyPresenter(this);
        initializedFields();
        return binding.getRoot();
    }

    private void initializedFields() {
        parseData();
        rAdapter = new RewardPolicyAdapter(rewardsModel, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvRewardPolicy.setAdapter(rAdapter);
        binding.rvRewardPolicy.setLayoutManager(layoutManager);

    }

    private void parseData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardPolicyPresenter.onRewardPolicyRequestData(user.getUsername());
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardPolicyData(List<RewardPolicyDataListModel> rewardModel) {
        rewardsModel.clear();
        rewardsModel.addAll(rewardModel);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardPolicyStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onRewardPolicyStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onRewardPolicyShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
