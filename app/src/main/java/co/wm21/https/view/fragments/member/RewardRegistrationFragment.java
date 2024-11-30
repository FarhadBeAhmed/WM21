package co.wm21.https.view.fragments.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;


import java.util.HashMap;

import co.wm21.https.R;
import co.wm21.https.databinding.FragmentRewardRegistrationBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnFranchiseApplicationView;
import co.wm21.https.presenter.FranchiseApplicationPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardRegistrationFragment extends Fragment implements OnFranchiseApplicationView, View.OnClickListener {

    View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;

    FranchiseApplicationPresenter franchiseApplicationPresenter;
    FragmentRewardRegistrationBinding binding;

    public RewardRegistrationFragment() {
        // Required empty public constructor
    }
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentRewardRegistrationBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();

        loadingDialog=new LoadingDialog(getActivity());
        franchiseApplicationPresenter = new FranchiseApplicationPresenter(this);
        binding.btnRewardRegistationApply.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rewardRegistation_Apply:
                String tempCategory = binding.etRewardRegistationCategory.getText().toString().trim();
                String tempFranName = binding.etRewardRegistationName.getText().toString().trim();
                String tempFranAddress = binding.etRewardRegistationAddress.getText().toString().trim();
                if (tempCategory.length() == 0) {
                    binding.etRewardRegistationCategory.setError("Blank!");
                    return;
                }

                if (tempFranName.length() == 0) {
                    binding.etRewardRegistationName.setError("Blank!");
                    return;
                }

                if (tempFranAddress.length() == 0) {
                    binding.etRewardRegistationAddress.setError("Blank!");
                    return;
                }

                if (checkInternetConnection.isInternetAvailable(getActivity())) {
                    franchiseApplicationPresenter.onFranchiseApplicationResponseData("0", appSessionManager.getUserDetails().getUsername(),
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
            binding.etRewardRegistationCategory.setText("");
            binding.etRewardRegistationName.setText("");
            binding.etRewardRegistationAddress.setText("");
        } else {
            Toast.makeText(getActivity(), hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFranchiseApplicationStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onFranchiseApplicationStopLoading() {
       loadingDialog.dismissDialog();
    }

    @Override
    public void onFranchiseApplicationShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }


}
