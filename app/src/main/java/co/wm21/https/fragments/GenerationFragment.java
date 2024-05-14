package co.wm21.https.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.R;
import co.wm21.https.adapters.GenerationAdapter;
import co.wm21.https.databinding.FragmentGenerationBinding;
import co.wm21.https.fragments.member.model.GenerationDataListModel;
import co.wm21.https.fragments.member.model.GenerationDataModel;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.GenerationItemClickListner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenerationFragment extends Fragment implements GenerationItemClickListner {

    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    API api;
    User user;
    FragmentGenerationBinding binding;
    private List<GenerationDataListModel> genList = new ArrayList<>();
    private GenerationAdapter gAdapter;
    View mView;

    public GenerationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentGenerationBinding.inflate(getLayoutInflater());



        api= ConstantValues.getAPI();
        user=new User(getContext());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        initializedList();
        return binding.getRoot();
    }

    private void initializedList() {
        gAdapter = new GenerationAdapter(genList, getActivity(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvGeneration.setLayoutManager(layoutManager);
        binding.rvGeneration.setItemAnimator(new DefaultItemAnimator());
        binding.rvGeneration.setAdapter(gAdapter);
        loadListData();
    }

    private void loadListData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
           /* final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources()
                    .getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();*/
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getGeneration(appSessionManager.getUserDetails().getUsername()).enqueue(new Callback<GenerationDataModel>() {
                @Override
                public void onResponse(Call<GenerationDataModel> call, Response<GenerationDataModel> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().getError();
                        if (errCount == 0) {
                            genList.clear();
                            genList.addAll(response.body().getTreeInfo());
                            gAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), response.body().getErrorReport(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("GENERATION", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    // dialog.dismiss();
                }

                @Override
                public void onFailure(Call<GenerationDataModel> call, Throwable t) {
                    //dialog.dismiss();
                    Log.d("GENERATION", "onFailure: " + t.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadListData();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onItemClickListner(String slNumber) {
        //Intent goGenerationDetailsActivity = new Intent(getActivity(), GenerationDetailsActivity.class);
    //    goGenerationDetailsActivity.putExtra("SLNUMBER", slNumber);
      //  startActivity(goGenerationDetailsActivity);
    }
}
