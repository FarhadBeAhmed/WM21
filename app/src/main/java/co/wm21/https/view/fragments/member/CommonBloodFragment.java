package co.wm21.https.view.fragments.member;




import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonObject;


import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.R;
import co.wm21.https.helpers.SessionHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonBloodFragment extends Fragment {


    private SessionHandler appSessionManager;

    public CommonBloodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_blood, container, false);

        appSessionManager = new SessionHandler(getContext());

        final Spinner bloodGroupSpnr = (Spinner) view.findViewById(R.id.spnr_BloodGroup);
        Button btnSubmitRequest = (Button) view.findViewById(R.id.btn_bloodRequest);

        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getBlood = bloodGroupSpnr.getSelectedItem().toString().trim();
                submitRequest(getBlood);
            }
        });

        return view;
    }


    private void submitRequest(String bloodName) {
        final MaterialDialog dialog = new MaterialDialog.Builder(getContext()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .show();

        APIService mApiService = ApiUtils.getApiService();
        mApiService.submitBloodRequest(appSessionManager.getUserDetails().getUsername(),
                    appSessionManager.getUserDetails().getMemberType(),
                bloodName).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    int errorCount = response.body().get("error").getAsInt();
                    if (errorCount == 0) {
                        Toast.makeText(getContext(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("BLOODREQ", response.code() + "! Error");
                    Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.d("BLOODREQ", "onFailure: " + t.getMessage());
            }
        });
    }

}
