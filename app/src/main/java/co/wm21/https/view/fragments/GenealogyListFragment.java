package co.wm21.https.view.fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListData;
import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListResponse;
import co.wm21.https.view.adapters.GenealogyListAdapter;
import co.wm21.https.view.adapters.GenerationAdapter;
import co.wm21.https.databinding.FragmentGenealogyListBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.gelealogy.OnGenealogyListView;
import co.wm21.https.presenter.genealogy.GenealogyListPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenealogyListFragment extends Fragment implements OnGenealogyListView {

    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    API api;
    User user;
    FragmentGenealogyListBinding binding;
    private List<GenealogyListData> genList = new ArrayList<>();
    private GenerationAdapter gAdapter;
    View mView;
    GenealogyListPresenter genealogyListPresenter;
    GenealogyListAdapter genealogyListAdapter;
    LoadingDialog loadingDialog;

    public GenealogyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentGenealogyListBinding.inflate(getLayoutInflater());
        user = new User(getContext());
        checkInternetConnection = new CheckInternetConnection();
        genealogyListPresenter= new GenealogyListPresenter(this);
        loadingDialog = new LoadingDialog(getActivity());
        loadIncomeBalanceData();
        return binding.getRoot();

    //  startActivity(goGenerationDetailsActivity);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadIncomeBalanceData() {
        genealogyListAdapter = new GenealogyListAdapter(genList, getContext(), this::onGenealogyItemClick);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.genealogyListRecyclerView.setLayoutManager(layoutManager);
        binding.genealogyListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.genealogyListRecyclerView.setAdapter(genealogyListAdapter);

        if (checkInternetConnection.isInternetAvailable(getActivity())) {

            genealogyListPresenter.getDataLoad(user.getUserId());

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

    private void onGenealogyItemClick(GenealogyListData item) {
        // Perform action on click, e.g., show details
        if (checkInternetConnection.isInternetAvailable(getActivity())) {

            genealogyListPresenter.getDataLoad(item.getTreId().toString());

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
    public void onGenealogyListDataLoad(GenealogyListResponse response) {
        loadingDialog.dismissDialog();
        genList.clear();
        genList.addAll(response.getData());
        genealogyListAdapter.notifyDataSetChanged();

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
