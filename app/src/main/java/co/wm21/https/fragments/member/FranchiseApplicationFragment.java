package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnDistrictListView;
import com.wm21ltd.wm21.interfaces.OnDivisionListView;
import com.wm21ltd.wm21.interfaces.OnFranchiseApplicationView;
import com.wm21ltd.wm21.interfaces.OnThanaListView;
import com.wm21ltd.wm21.presenters.DistrictListPresenter;
import com.wm21ltd.wm21.presenters.DivisionListPresenter;
import com.wm21ltd.wm21.presenters.FranchiseApplicationPresenter;
import com.wm21ltd.wm21.presenters.ThanaListPersenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseApplicationFragment extends Fragment implements OnDivisionListView, OnDistrictListView, OnThanaListView, View.OnClickListener, OnFranchiseApplicationView {

    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    DivisionListPresenter divisionListPresenter;
    DistrictListPresenter districtListPresenter;
    ThanaListPersenter thanaListPersenter;
    FranchiseApplicationPresenter franchiseApplicationPresenter;
    View mView;
    MaterialDialog dialog;

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
        mView = inflater.inflate(R.layout.fragment_franchise_application, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        divisionListPresenter = new DivisionListPresenter(this);
        districtListPresenter = new DistrictListPresenter(this);
        thanaListPersenter = new ThanaListPersenter(this);
        franchiseApplicationPresenter = new FranchiseApplicationPresenter(this);
        loadDivisionData();
        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadDistrictData("947", spinnerDivision.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadThanaData("947", spinnerDivision.getSelectedItemPosition(), spinnerDistrict.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buttonSubmit.setOnClickListener(this);

        return mView;
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
        spinnerDivision.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionList));

    }

    @Override
    public void onDivisionListStartLoading() {
        dialog.show();
    }

    @Override
    public void onDivisionListStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onDivisionListShowMessage(String errmsg) {
        divisionList.clear();
        divisionList.add("Select Zone");
        divisionIDList.clear();
        divisionIDList.add("0");
        spinnerDivision.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionList));
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
            spinnerDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));
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
        spinnerDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));

    }

    @Override
    public void onDistrictListStartLoading() {
        dialog.show();
    }

    @Override
    public void onDistrictListStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onDistrictListShowMessage(String errMsg) {
        districtList.clear();
        districtList.add("Select District");
        districtIDList.add("0");
        spinnerDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));
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
            spinnerThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
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
        spinnerThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));

    }

    @Override
    public void onThanaListStartLoading() {
        dialog.show();
    }

    @Override
    public void onThanaListStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onThanaListShowMessage(String errMsg) {
        thanaList.clear();
        thanaIDList.clear();
        thanaList.add("Select Thana");
        thanaIDList.add("0");
        spinnerThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_FranApp_Submit:
                String divisionID;
                String districtID = "";
                String thanaID = "";
                if (spinnerDivision.getSelectedItemPosition() > 0) {
                    divisionID = divisionIDList.get(spinnerDivision.getSelectedItemPosition() - 1);
                } else {
                    ((TextView) spinnerDivision.getSelectedView()).setError("Country field not selected!");
                    return;
                }

                if (spinnerDistrict.getSelectedItemPosition() > 0) {
                    districtID = districtIDList.get(spinnerDistrict.getSelectedItemPosition() - 1);
                } else {
                    districtID = "";
                }

                if (spinnerThana.getSelectedItemPosition() > 0) {
                    thanaID = thanaIDList.get(spinnerThana.getSelectedItemPosition() - 1);
                } else {
                    thanaID = "";
                }

                String tempFranName = editTextName.getText().toString().trim();
                String tempFranAddress = editTextAddress.getText().toString().trim();
                String tempFranLicense = editTextTradeLicense.getText().toString().trim();
                if (tempFranName.length() == 0) {
                    editTextName.setError("Blank!");
                    return;
                }

                if (tempFranAddress.length() == 0) {
                    editTextAddress.setError("Blank!");
                    return;
                }

                if (tempFranLicense.length() < 3) {
                    editTextTradeLicense.setError("Invalid!");
                    return;
                }

                if (checkInternetConnection.isInternetAvailable(getActivity())) {
                    franchiseApplicationPresenter.onFranchiseApplicationResponseData("0", appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID),
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
            spinnerDivision.setSelection(0);
            editTextName.setText("");
            editTextAddress.setText("");
            editTextTradeLicense.setText("");
        }
    }

    @Override
    public void onFranchiseApplicationStartLoading() {
        dialog.show();
    }

    @Override
    public void onFranchiseApplicationStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onFranchiseApplicationShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
