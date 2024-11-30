package co.wm21.https.view.fragments.member;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.BrandAmbassadorListModel;
import co.wm21.https.view.adapters.BrandAmbassadorAdapter;
import co.wm21.https.databinding.FragmentBrandAmbassadorBinding;
import co.wm21.https.presenter.interfaces.OnBrandAmbassadorListView;
import co.wm21.https.presenter.interfaces.SearchSmsCallListener;
import co.wm21.https.presenter.BrandAmbassadorPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrandAmbassadorFragment extends Fragment implements OnBrandAmbassadorListView, SearchSmsCallListener {

    private static final int CALL_PHONE_PERMISSION_CODE = 1;

    private BrandAmbassadorAdapter brandAmbassadorAdapter;
    private BrandAmbassadorPresenter brandAmbassadorPresenter;
    private List<BrandAmbassadorListModel> ambassadorList = new ArrayList<>();
    private int loadMore = 0;

    FragmentBrandAmbassadorBinding binding;

    public BrandAmbassadorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentBrandAmbassadorBinding.inflate(getLayoutInflater());

        brandAmbassadorPresenter = new BrandAmbassadorPresenter(this);
        initializedRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onBrandAmbassadorReqData(List<BrandAmbassadorListModel> brandAmbassadorList) {
        ambassadorList.addAll(brandAmbassadorList);
        brandAmbassadorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBrandAmbassadorStartLoading() {

    }

    @Override
    public void onBrandAmbassadorStopLoading() {

    }

    @Override
    public void onBrandAmbassadorShowMessage(String msg) {

    }

    private void initializedRecyclerView(){
        brandAmbassadorAdapter = new BrandAmbassadorAdapter(getContext(), ambassadorList, this);
        binding.brandAmbassadorRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.brandAmbassadorRecycler.setAdapter(brandAmbassadorAdapter);
        parseData(0);
        brandAmbassadorAdapter.setOnBottomReachedListener(position -> parseData(loadMore = loadMore + 10));
}

    private void parseData(int loadMoreData) {
        brandAmbassadorPresenter.brandAmbassadorDataResponse(loadMoreData + ",10");
    }

    @Override
    public void makeCall(String mobileNumber) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobileNumber));
            startActivity(callIntent);
        } else {
            requestCallphonePermission();
        }
    }
    private void requestCallphonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission Needed")
                    .setMessage("To make a call this permission is needed.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_PHONE_PERMISSION_CODE:
                if (grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Perission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void makeSms(String mobileNumber) {

    }
}
