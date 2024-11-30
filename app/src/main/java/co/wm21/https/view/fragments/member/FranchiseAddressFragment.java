package co.wm21.https.view.fragments.member;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.wm21.https.R;
import co.wm21.https.databinding.FragmentFranchiseAddressBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnDistrictListView;
import co.wm21.https.presenter.interfaces.OnDivisionListView;
import co.wm21.https.presenter.interfaces.OnFranchiseInfoView;
import co.wm21.https.presenter.interfaces.OnThanaListView;
import co.wm21.https.presenter.DistrictListPresenter;
import co.wm21.https.presenter.DivisionListPresenter;
import co.wm21.https.presenter.FranchiseInfoPresenter;
import co.wm21.https.presenter.ThanaListPersenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseAddressFragment extends Fragment implements OnDivisionListView, OnDistrictListView, OnThanaListView, View.OnClickListener, OnFranchiseInfoView {

    //View mView;
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    DivisionListPresenter divisionListPresenter;
    DistrictListPresenter districtListPresenter;
    ThanaListPersenter thanaListPersenter;
    FranchiseInfoPresenter franchiseInfoPresenter;


    private List<String> divisionList = new ArrayList<>();
    private List<String> divisionIDList = new ArrayList<>();

    private List<String> districtList = new ArrayList<>();
    private List<String> districtIDList = new ArrayList<>();

    private List<String> thanaList = new ArrayList<>();
    private List<String> thanaIDList = new ArrayList<>();
    LoadingDialog loadingDialog;

    FragmentFranchiseAddressBinding binding;
    public FranchiseAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFranchiseAddressBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        loadingDialog=new LoadingDialog(getActivity());
        divisionListPresenter = new DivisionListPresenter(this);
        districtListPresenter = new DistrictListPresenter(this);
        thanaListPersenter = new ThanaListPersenter(this);
        franchiseInfoPresenter = new FranchiseInfoPresenter(this);
        loadDivisionData();

        binding.spnrFranchiseDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadDistrictData("947", binding.spnrFranchiseDivision.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spnrFranchiseDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadThanaData("947", binding.spnrFranchiseDivision.getSelectedItemPosition(), binding.spnrFranchiseDistrict.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnFranchiseSearch.setOnClickListener(this);
        return binding.getRoot();
    }


    //division data load
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
        binding.spnrFranchiseDivision.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionList));
    }

    @Override
    public void onDivisionListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDivisionListStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onDivisionListShowMessage(String errmsg) {
        divisionList.clear();
        divisionList.add("Select Zone");
        divisionIDList.clear();
        divisionIDList.add("0");
        binding.spnrFranchiseDivision.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, divisionList));
        Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();
    }

    //district data load
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

    //Thana Data Load
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
            binding.spnrFranchiseThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
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
        binding.spnrFranchiseThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
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
        binding.spnrFranchiseThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }


    //for search
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Franchise_Search:
                String divisionID;
                String districtID = "";
                String thanaID = "";
                if (binding.spnrFranchiseDivision.getSelectedItemPosition() > 0) {
                    divisionID = divisionIDList.get(binding.spnrFranchiseDivision.getSelectedItemPosition() - 1);
                } else {
                    ((TextView) binding.spnrFranchiseDivision.getSelectedView()).setError("Country field not selected!");
                    return;
                }

                if (binding.spnrFranchiseDistrict.getSelectedItemPosition() > 0) {
                    districtID = districtIDList.get(binding.spnrFranchiseDistrict.getSelectedItemPosition() - 1);
                } else {
                    districtID = "";
                }

                if (binding.spnrFranchiseThana.getSelectedItemPosition() > 0) {
                    thanaID = thanaIDList.get(binding.spnrFranchiseThana.getSelectedItemPosition() - 1);
                } else {
                    thanaID = "";
                }

                if (checkInternetConnection.isInternetAvailable(getActivity())) {
                    franchiseInfoPresenter.onFranchiseInfoDataResponse(divisionID, districtID, thanaID);
                } else {
                    Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onFranchiseInfoData(HashMap hashMap) {
        binding.lnrFranchiseInfoSearchResult.setVisibility(View.VISIBLE);
        binding.txtMyFranchiseType.setText("Franchise Title: " + hashMap.get("title").toString());
        binding.txtMyFranchiseName.setText("Franchise Name: " + hashMap.get("name").toString());
        binding.txtMyFranchiseMobileNo.setText("Mobile No: " + hashMap.get("mobile").toString());
        binding.txtMyFranchiseEmail.setText("Email: " + hashMap.get("email").toString());
        binding.txtMyFranchiseAddress.setText("Address: " + hashMap.get("addrss").toString());
    }

    @Override
    public void onFranchiseInfoStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onFranchiseInfoStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onFranchiseInfoShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
