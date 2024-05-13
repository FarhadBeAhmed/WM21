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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnDistrictListView;
import com.wm21ltd.wm21.interfaces.OnDivisionListView;
import com.wm21ltd.wm21.interfaces.OnThanaListView;
import com.wm21ltd.wm21.interfaces.OnTrainingRequestFormView;
import com.wm21ltd.wm21.interfaces.OnTrainingServiceNewsListView;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.TrainingServiceNewsListModel;
import com.wm21ltd.wm21.networks.Remote.APIService;
import com.wm21ltd.wm21.presenters.DistrictListPresenter;
import com.wm21ltd.wm21.presenters.DivisionListPresenter;
import com.wm21ltd.wm21.presenters.ThanaListPersenter;
import com.wm21ltd.wm21.presenters.TrainerRequestPresenter;
import com.wm21ltd.wm21.presenters.TrainingServiceNewsPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingRequestFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, OnTrainingRequestFormView, OnTrainingServiceNewsListView, OnDivisionListView, OnDistrictListView, OnThanaListView {

    View mView;
    MaterialDialog dialog;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    TrainerRequestPresenter trainerRequestPresenter;

    @BindView(R.id.spinr_training_category)
    Spinner spinnerTrainingCategory;
    @BindView(R.id.et_training_Title)
    EditText editTextTraininTitle;
    @BindView(R.id.et_training_Details)
    EditText editTextTrainingDetails;
    @BindView(R.id.spinr_trainer_name)
    Spinner spinnerTrainingTrainer;
    @BindView(R.id.et_training_Charge)
    EditText editTextTrainingCharge;
    @BindView(R.id.et_training_Duration)
    EditText editTextTrainingDuration;
    @BindView(R.id.et_trainingApp_Venue)
    EditText editTextTrainingVenue;
    @BindView(R.id.et_trainingApp_Seats)
    EditText editTextTrainingSeats;
    @BindView(R.id.tv_training_Date)
    TextView textViewTrainingDate;
    @BindView(R.id.btn_taining_RequestSubmit)
    Button buttonRequestSubmit;
    @BindView(R.id.spnr_tRequest_Division)
    Spinner spinnerDivision;
    @BindView(R.id.spnr_tRequest_District)
    Spinner spinnerDistrict;
    @BindView(R.id.spnr_tRequest_Thana)
    Spinner spinnerThana;


    private List<String> divisionList = new ArrayList<>();
    private List<String> divisionIDList = new ArrayList<>();

    private List<String> districtList = new ArrayList<>();
    private List<String> districtIDList = new ArrayList<>();

    private List<String> thanaList = new ArrayList<>();
    private List<String> thanaIDList = new ArrayList<>();

    private DivisionListPresenter divisionListPresenter;
    private DistrictListPresenter districtListPresenter;
    private ThanaListPersenter thanaListPersenter;
    private List <String> categoryList = new ArrayList<>();
    private List <String> trainerList = new ArrayList<>();

    private TrainingServiceNewsPresenter trainingServiceNewsPresenter;

    public TrainingRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_training_request, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        trainerRequestPresenter = new TrainerRequestPresenter(this);
        trainingServiceNewsPresenter = new TrainingServiceNewsPresenter(this);
        trainingServiceNewsPresenter.TrainingServiceNewsDataResponse("1","0,500");
        loadTrainerList();
        divisionListPresenter = new DivisionListPresenter(this);
        districtListPresenter = new DistrictListPresenter(this);
        thanaListPersenter = new ThanaListPersenter(this);
        textViewTrainingDate.setOnClickListener(this);
        buttonRequestSubmit.setOnClickListener(this);

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
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_training_Date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                break;

            case R.id.btn_taining_RequestSubmit:

                if (spinnerTrainingCategory.getSelectedItemPosition() != 0 || spinnerTrainingTrainer.getSelectedItemPosition() != 0){
                    String tempTrainingCategory = spinnerTrainingCategory.getSelectedItem().toString().trim();
                    String tempTitle = editTextTraininTitle.getText().toString().trim();
                    String tempDetails = editTextTrainingDetails.getText().toString().trim();
                    String tempTrainer = spinnerTrainingTrainer.getSelectedItem().toString().trim();
                    String tempCharge = editTextTrainingCharge.getText().toString().trim();
                    String tempDuration = editTextTrainingDuration.getText().toString().trim();
                    String tempVenue = editTextTrainingVenue.getText().toString().trim();
                    String tempSeats = editTextTrainingSeats.getText().toString().trim();
                    String tempTargetDate = textViewTrainingDate.getText().toString().trim();



                    if (tempTitle.length() <= 0) {
                        editTextTraininTitle.setError("Error!");
                        return;
                    }
                    if (tempDetails.length() <= 0) {
                        editTextTrainingDetails.setError("Error!");
                        return;
                    }

                    if (tempCharge.length() <= 0) {
                        editTextTrainingCharge.setError("Error!");
                        return;
                    }
                    if (tempDuration.length() <= 0) {
                        editTextTrainingDuration.setError("Error!");
                        return;
                    }
                    if (tempVenue.length() <= 0) {
                        editTextTrainingVenue.setError("Error!");
                        return;
                    }
                    if (tempSeats.length() <= 0) {
                        editTextTrainingSeats.setError("Error!");
                        return;
                    }

                    if (tempTargetDate.length() <= 0) {
                        textViewTrainingDate.setError("Error!");
                        return;
                    }

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

                    if (checkInternetConnection.isInternetAvailable(getActivity())) {
                        trainerRequestPresenter.onTrainerRequestDataResponse(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID),
                                tempTrainingCategory, tempTitle, tempDetails, tempTrainer, tempCharge, tempDuration, tempVenue, tempSeats, tempTargetDate,divisionID,districtID,thanaID);
                    } else {
                        Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Select Category and Trainer name!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + String.format("%02d", monthOfYear + 1) + "-" + dayOfMonth;
        textViewTrainingDate.setText(date);
    }

    @Override
    public void onTrainingRequestFormData(HashMap hashMap) {
        String errCode = hashMap.get("error").toString();
        if (errCode.equals("0")) {
            Toast.makeText(getActivity(), hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
            //editTextTrainingCategory.setText("");
            editTextTraininTitle.setText("");
            editTextTrainingDetails.setText("");
            //editTextTrainingTrainer.setText("");
            editTextTrainingCharge.setText("");
            editTextTrainingDuration.setText("");
            editTextTrainingVenue.setText("");
            editTextTrainingSeats.setText("");
            textViewTrainingDate.setText("");
        } else {
            Toast.makeText(getActivity(), hashMap.get("error_report").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTrainingRequestFormStartLoading() {
        dialog.show();
    }

    @Override
    public void onTrainingRequestFormStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onTrainingRequestFormShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrainingServiceNewsResponseData(List<TrainingServiceNewsListModel> trainingServiceNewsList) {
        categoryList.add("Select Category");
        for (int i=1; i< trainingServiceNewsList.size(); i++){
            categoryList.add(trainingServiceNewsList.get(i).getCategory());
        }
        spinnerTrainingCategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, categoryList));
    }

    @Override
    public void onTrainingServiceNewsStartLoading() {

    }

    @Override
    public void onTrainingServiceNewsStopLoading() {

    }

    @Override
    public void onTrainingServiceNewsShowMessage(String msg) {

    }

    public void loadTrainerList(){

        if (checkInternetConnection.isInternetAvailable(getContext())) {
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTrainerList("0,500").enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().get("error").getAsInt();
                        if (errCount == 0) {
                            JsonArray trainerArr = response.body().get("category").getAsJsonArray();

                            if (trainerArr.size()>0){
                                trainerList.add("Select Trainer");
                                for (int i=0; i<trainerArr.size(); i++){
                                    JsonObject trainerObject = trainerArr.get(i).getAsJsonObject();
                                    trainerList.add(trainerObject.get("Name").getAsString());
                                }

                                spinnerTrainingTrainer.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, trainerList));
                            }

                        }
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        }
    }


    //.......................Division.....................................

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


    //.......................District.....................................

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


    //.......................Thana.....................................
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

    //division data load
    public void loadDivisionData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            divisionListPresenter.onDivisionDataLoad("947");
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
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
            spinnerDistrict.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtList));
        }
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
            spinnerThana.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, thanaList));
        }
    }
}
