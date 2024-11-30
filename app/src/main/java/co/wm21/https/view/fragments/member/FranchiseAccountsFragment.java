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

import co.wm21.https.FHelper.networks.Models.FranchiseAccountDataListModel;
import co.wm21.https.view.adapters.FranchiseAccountCommissionAdapter;
import co.wm21.https.databinding.FragmentFranchiseAccountsBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnBottomReachedListener;
import co.wm21.https.presenter.interfaces.OnFranchiseAccountView;
import co.wm21.https.presenter.FranchiseAccountCommissionPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseAccountsFragment extends Fragment implements OnFranchiseAccountView {

    View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    LoadingDialog loadingDialog;
    FranchiseAccountCommissionPresenter franchiseAccountCommissionPresenter;


    FragmentFranchiseAccountsBinding binding;
    private List<FranchiseAccountDataListModel> franList = new ArrayList<>();
    private FranchiseAccountCommissionAdapter adapter;
    int loadMore = 0;

    public FranchiseAccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentFranchiseAccountsBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
       loadingDialog=new LoadingDialog(getActivity());
        franchiseAccountCommissionPresenter = new FranchiseAccountCommissionPresenter(this);
        initializedFields();
        return binding.getRoot();
    }

    private void initializedFields() {
        adapter = new FranchiseAccountCommissionAdapter(franList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvFranchiseCommission.setAdapter(adapter);
        binding.rvFranchiseCommission.setLayoutManager(layoutManager);
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
            franchiseAccountCommissionPresenter.onFranchiseAccountRequestData(appSessionManager.getUserDetails().getUsername(),
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
       loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onFranchiseAccountStopLoading() {
       loadingDialog.dismissDialog();
    }

    @Override
    public void onFranchiseAccountShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
