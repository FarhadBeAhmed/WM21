package co.wm21.https.fragments.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentFranchiseApplicationBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.interfaces.OnDistrictListView;
import co.wm21.https.interfaces.OnDivisionListView;
import co.wm21.https.interfaces.OnFranchiseApplicationView;
import co.wm21.https.interfaces.OnThanaListView;
import co.wm21.https.presenter.DistrictListPresenter;
import co.wm21.https.presenter.DivisionListPresenter;
import co.wm21.https.presenter.FranchiseApplicationPresenter;
import co.wm21.https.presenter.ThanaListPersenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseApplicationFragment extends Fragment implements OnDivisionListView, OnDistrictListView, OnThanaListView, View.OnClickListener, OnFranchiseApplicationView {

    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    DivisionListPresenter divisionListPresenter;
    DistrictListPresenter districtListPresenter;
    ThanaListPersenter thanaListPersenter;
    FranchiseApplicationPresenter franchiseApplicationPresenter;

    LoadingDialog loadingDialog;

/*
    @BindView(R.id.spnr_FranApp_Division)
    Spinner spinnerDivision;
    @BindView(R.id.spnr_Franchise_District)
    Spinner spinnerDistrict;
    @BindView(R.id.spnr_FranApp_Thana)
    Spinner spinnerThana;

    @BindView(R.id.et_FranApp_Name)
    EditText editTextName;
    @BindView(R.id.et_FranApp_Address)
    EditText editTextAddress;
    @BindView(R.id.et_FranApp_LicenseNo)
    EditText editTextTradeLicense;

    @BindView(R.id.btn_FranApp_Submit)
    Button buttonSubmit;
*/
    FragmentFranchiseApplicationBinding binding;

    private List<String> divisionList = new ArrayList<>();
    private List<String> divisionIDList = new ArrayList<>();

    private List<String> districtList = new ArrayList<>();
    private List<String> districtIDList = new ArrayList<>();

    private List<String> thanaList = new ArrayList<>();
    private List<String> thanaIDList = new ArrayList<>();

    public FranchiseApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFranchiseApplicationBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        loadingDialog=new LoadingDialog(getActivity());
        divisionListPresenter = new DivisionListPresenter(this);
        districtListPresenter = new DistrictListPresenter(this);
        thanaListPersenter = new ThanaListPersenter(this);
        franchiseApplicationPresenter = new FranchiseApplicationPresenter(this);
        loadDivisionData();
        binding.spnrFranAppDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadDistrictData("947", binding.spnrFranAppDivision.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spnrFranchiseDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadThanaData("947", binding.spnrFranAppDivision.getSelectedItemPosition(), binding.spnrFranchiseDistrict.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnFranAppSubmit.setOnClickListener(this);

        return binding.getRoot();
    }


    //for division data
    public void loadDivisionData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            divisionListPresenter.onDivisionDataLoad("947");
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDivisionListDataLoad(JsonArray jsonArrayDivision) {
        if (jsonArrayDivision.size() > 0) {
            divisionList.clear();
            divisionIDList.clear();
            divisionList.add("Select Zone");
            for (int i = 0; i < jsonArrayDivision.size(); i++) {
                JsonObject divisionObj = jsonArrayDivision.get(i).getAsJsonObject();
                divisionIDList.add(divisionObj.get("Division_ID").getAsString());
                divisionList.add(divisionObj.get("Name").getAsString());
            }
        }
        binding.spnrFranAppDivision.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionList));

    }

    @Override
    public void onDivisionListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDivisionListStopLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDivisionListShowMessage(String errmsg) {
        divisionList.clear();
        divisionList.add("Select Zone");
        divisionIDList.clear();
        divisionIDList.add("0");
        binding.spnrFranAppDivision.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionList));
        Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();
    }

    //for district data
    public void loadDistrictData(String cuntryID, int divPos) {
        if (divPos > 0) {
            if (checkInternetConnection.isInternetAvailable(getActivity())) {
                String tempDivisonID = divisionIDList.get(divPos - 1);
                districtListPresenter.onDistrDataResponse(tempDivisonID, cuntryID);
            } else {
                Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            districtList.clear();
            districtList.add("Select District");
            districtIDList.add("0");
            binding.spnrFranchiseDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));
        }
    }

    @Override
    public void onDistrictListDataLoad(JsonArray jsonArrayDistrict) {
        if (jsonArrayDistrict.size() > 0) {
            districtList.clear();
            districtIDList.clear();
            districtList.add("Select District");
            for (int i = 0; i < jsonArrayDistrict.size(); i++) {
                JsonObject districtObj = jsonArrayDistrict.get(i).getAsJsonObject();
                districtIDList.add(districtObj.get("District_ID").getAsString());
                districtList.add(districtObj.get("Name").getAsString());
            }
        }
        binding.spnrFranchiseDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));

    }

    @Override
    public void onDistrictListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDistrictListStopLoading() {
       loadingDialog.dismissDialog();
    }

    @Override
    public void onDistrictListShowMessage(String errMsg) {
        districtList.clear();
        districtList.add("Select District");
        districtIDList.add("0");
        binding.spnrFranchiseDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    //for thana data
    private void loadThanaData(String countryID, int divPos, int disPos) {
        if (disPos > 0) {
            if (checkInternetConnection.isInternetAvailable(getActivity())) {
                String tempDivisonID = divisionIDList.get(divPos - 1);
                String tempDistrictID = districtIDList.get(disPos - 1);
                thanaListPersenter.onThanaResponseData("947", tempDivisonID, tempDistrictID);
            } else {
                Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            thanaList.clear();
            thanaIDList.clear();
            thanaList.add("Select Thana");
            thanaIDList.add("0");
            binding.spnrFranAppThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
        }
    }

    @Override
    public void onThanaListData(JsonArray jsonArrayThana) {
        if (jsonArrayThana.size() > 0) {
            thanaList.clear();
            thanaIDList.clear();
            thanaList.add("Select Thana");
            for (int i = 0; i < jsonArrayThana.size(); i++) {
                JsonObject thanaObj = jsonArrayThana.get(i).getAsJsonObject();
                thanaIDList.add(thanaObj.get("Thana_ID").getAsString());
                thanaList.add(thanaObj.get("Name").getAsString());
            }
        }
        binding.spnrFranAppThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));

    }

    @Override
    public void onThanaListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onThanaListStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onThanaListShowMessage(String errMsg) {
        thanaList.clear();
        thanaIDList.clear();
        thanaList.add("Select Thana");
        thanaIDList.add("0");
        binding.spnrFranAppThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_FranApp_Submit:
                String divisionID;
                String districtID = "";
                String thanaID = "";
                if (binding.spnrFranAppThana.getSelectedItemPosition() > 0) {
                    divisionID = divisionIDList.get(binding.spnrFranAppDivision.getSelectedItemPosition() - 1);
                } else {
                    ((TextView) binding.spnrFranAppDivision.getSelectedView()).setError("Country field not selected!");
                    return;
                }

                if (binding.spnrFranchiseDistrict.getSelectedItemPosition() > 0) {
                    districtID = districtIDList.get(binding.spnrFranchiseDistrict.getSelectedItemPosition() - 1);
                } else {
                    districtID = "";
                }

                if (binding.spnrFranAppThana.getSelectedItemPosition() > 0) {
                    thanaID = thanaIDList.get(binding.spnrFranAppThana.getSelectedItemPosition() - 1);
                } else {
                    thanaID = "";
                }

                String tempFranName = binding.etFranAppName.getText().toString().trim();
                String tempFranAddress = binding.etFranAppAddress.getText().toString().trim();
                String tempFranLicense = binding.etFranAppLicenseNo.getText().toString().trim();
                if (tempFranName.length() == 0) {
                    binding.etFranAppName.setError("Blank!");
                    return;
                }

                if (tempFranAddress.length() == 0) {
                    binding.etFranAppAddress.setError("Blank!");
                    return;
                }

                if (tempFranLicense.length() < 3) {
                    binding.etFranAppLicenseNo.setError("Invalid!");
                    return;
                }

                if (checkInternetConnection.isInternetAvailable(getActivity())) {
                    franchiseApplicationPresenter.onFranchiseApplicationResponseData("0", appSessionManager.getUserDetails().getUsername(),
                            "4", divisionID, districtID, thanaID, tempFranName, tempFranAddress, tempFranLicense, "");
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
            binding.spnrFranAppDivision.setSelection(0);
            binding.etFranAppName.setText("");
            binding.etFranAppAddress.setText("");
            binding.etFranAppLicenseNo.setText("");
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
