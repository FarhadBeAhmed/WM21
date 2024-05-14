package co.wm21.https.fragments.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.networks.Models.RewardGalleryDataListModel;
import co.wm21.https.R;
import co.wm21.https.adapters.RewardGalleryApapter;
import co.wm21.https.databinding.FragmentRewardGalleryBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.interfaces.OnBottomReachedListener;
import co.wm21.https.interfaces.OnRewardGalleryView;
import co.wm21.https.presenter.RewardGalleryPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardGalleryFragment extends Fragment implements OnRewardGalleryView {

    View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    RewardGalleryPresenter rewardGalleryPresenter;


    private  List<RewardGalleryDataListModel> rgList;
    private RewardGalleryApapter rAdapter;
    FragmentRewardGalleryBinding binding;
    LoadingDialog loadingDialog;
    int loadMore = 0;


    public RewardGalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentRewardGalleryBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        loadingDialog=new LoadingDialog(getActivity());
        rewardGalleryPresenter = new RewardGalleryPresenter(this);
        initializedFields();
        return binding.getRoot();
    }

    private void initializedFields() {
        rgList = new ArrayList<>();
        rAdapter = new RewardGalleryApapter(rgList, getActivity());
        binding.rvRewardGallery.setAdapter(rAdapter);
        binding.rvRewardGallery.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        parseData(0);
        rAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                parseData(loadMore = loadMore + 10);
            }
        });
    }

    private void parseData(int loadMoreValue) {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardGalleryPresenter.onRewardGalleryDataResponse(appSessionManager.getUserDetails().getUsername(),
                    loadMoreValue + ",10");
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardGalleryData(List<RewardGalleryDataListModel> rewardGalleryDataListModels) {
        rgList.addAll(rewardGalleryDataListModels);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardGalleryStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onRewardGalleryStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onRewardGalleryShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
