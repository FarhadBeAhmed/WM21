package co.wm21.https.view.fragments.member;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;


import java.util.HashMap;

import co.wm21.https.databinding.FragmentMyFranchiseBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnMyFranchiseView;
import co.wm21.https.presenter.MyFranchiseInfoPresenterr;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFranchiseFragment extends Fragment implements OnMyFranchiseView {

    View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;

    FragmentMyFranchiseBinding binding;

    //MaterialDialog dialog;
    LoadingDialog loadingDialog;
    MyFranchiseInfoPresenterr myFranchiseInfoPresenterr;


    public MyFranchiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMyFranchiseBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
       loadingDialog=new LoadingDialog(getActivity());
        myFranchiseInfoPresenterr = new MyFranchiseInfoPresenterr(this);
        loadInitialData();
        return binding.getRoot();
    }

    private void loadInitialData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            myFranchiseInfoPresenterr.onMyFranchiseInfoResponseData(appSessionManager.getUserDetails().getUserId());
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onResponseData(HashMap hashMap) {
        binding.txtMyFranchiseType.setText("Franchise Type: " + hashMap.get("title").toString());
        binding.txtMyFranchiseName.setText("Franchise Name: " + hashMap.get("name").toString());
        binding.txtMyFranchiseMobileNo.setText("Mobile No: " + hashMap.get("mobile").toString());
        binding.txtMyFranchiseEmail.setText("Email: " + hashMap.get("email").toString());
        binding.txtMyFranchiseAddress.setText("Address: " + hashMap.get("addrss").toString());
    }

    @Override
    public void startLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void stopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void showMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
