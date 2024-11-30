package co.wm21.https.view.fragments.registration;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.SmsBroadcastReceiver;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.FHelper.networks.Models.SignupNumberVerifyModel;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentRegisterBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.LoginFragment;
import co.wm21.https.helpers.Constant;
import co.wm21.https.presenter.interfaces.OnSignupFinalView;
import co.wm21.https.presenter.interfaces.OnSignupFirstView;
import co.wm21.https.presenter.interfaces.OnSignupNumberVerifyView;
import co.wm21.https.presenter.SignupFinalPresenter;
import co.wm21.https.presenter.SignupFirstPresenter;
import co.wm21.https.presenter.SignupNumberVerifyPresenter;


public class RegisterFragment extends Fragment implements OnSignupFirstView, OnSignupNumberVerifyView, OnSignupFinalView {

    public FragmentRegisterBinding binding;
    public Integer countryValue = 0;
    API api;
    String mobileNumber;
    int code;

    private static final int REQ_USER_CODE = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;

    LoadingDialog loadingDialog;
    SignupFirstPresenter signupFirstPresenter;
    SignupNumberVerifyPresenter signupNumberVerifyPresenter;
    SignupFinalPresenter signupFinalPresenter;
    View btnView;

    ArrayList<String> districts = new ArrayList();
    ArrayList<String> ids = new ArrayList();
    ArrayAdapter<String> dAdapter;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        loadingDialog=new LoadingDialog(getActivity());
        signupFirstPresenter=new SignupFirstPresenter(this);
        signupNumberVerifyPresenter=new SignupNumberVerifyPresenter(this);
        signupFinalPresenter=new SignupFinalPresenter(this);


        String[] country = {"Australia", "Bangladesh", "Bhutan", "India", "Malaysia", "Nepal", "Pakistan", "Qatar", "Saudi Arabia", "Singapore", "South America", "Sri Lanka", "UAE", "UK", "USA"};
        api = ConstantValues.getAPI();


        //requestPermission();
        startUserConstant();

        binding.pinView.setLineColor(ResourcesCompat.getColor(getResources(), R.color.primary_red, requireActivity().getTheme()));
        binding.selectCountry.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, country));
        binding.selectCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean checked = false;
                for (int j = 0; j < country.length; j++) {
                    if (charSequence.toString().equals(country[i]))
                        checked = true;
                }
                if (!checked) {
                    charSequence = "";
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch (charSequence.toString()) {
                    case "":
                        countryValue = 0;
                        break;
                    case "Sri Lanka":
                        countryValue = 944;
                        break;
                    case "India":
                        countryValue = 945;
                        break;
                    case "Pakistan":
                        countryValue = 946;
                        break;
                    case "Bangladesh":
                        countryValue = 947;
                        break;
                    case "Nepal":
                        countryValue = 948;
                        break;
                    case "Bhutan":
                        countryValue = 949;
                        break;
                    case "Malaysia":
                        countryValue = 951;
                        break;
                    case "Singapore":
                        countryValue = 952;
                        break;
                    case "Australia":
                        countryValue = 953;
                        break;
                    case "UK":
                        countryValue = 954;
                        break;
                    case "USA":
                        countryValue = 955;
                        break;
                    case "UAE":
                        countryValue = 956;
                        break;
                    case "Qatar":
                        countryValue = 1033;
                        break;
                    case "Saudi Arabia":
                        countryValue = 1034;
                        break;
                    case "South America":
                        countryValue = 1035;
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                switch (countryValue) {
                    case 947:
                        binding.applicantMobile.setVisibility(View.VISIBLE);
                        binding.applicantEmail.setVisibility(View.GONE);
                        break;
                    case 0:
                        binding.applicantMobile.setVisibility(View.GONE);
                        binding.applicantEmail.setVisibility(View.GONE);
                        break;
                    default:
                        binding.applicantMobile.setVisibility(View.GONE);
                        binding.applicantEmail.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        binding.btnSignUp.setOnClickListener(view -> {
            if (countryValue == 947) {
                if (Constant.validation(binding.country, binding.applicantMobile)) {

                    if (!Pattern.matches("^[0][1][3-9]\\d{8}$", binding.applicantMobile.getEditText().getText()))
                        binding.applicantMobile.setError("It's not Bangladeshi Mobile Number.");
                    else {
                        mobileNumber = binding.applicantMobile.getEditText().getText().toString();
                        signupFirstPresenter.signupFirstDataLoad(mobileNumber, String.valueOf(countryValue));
                        btnView=view;
                    }
                }
            } else {
                if (Constant.validation(binding.country, binding.applicantEmail)) {
                    if (!Pattern.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,63}$", binding.applicantEmail.getEditText().getText()))
                        binding.applicantEmail.setError("It's not email.");
                    else {
                        binding.applicantMobile.setError("");
                        binding.applicantEmail.setError("");
                        binding.country.setVisibility(View.GONE);
                        binding.country.setEnabled(false);
                        binding.applicantEmail.setVisibility(View.GONE);
                        binding.applicantEmail.setEnabled(false);
                        binding.pinView.setVisibility(View.VISIBLE);
//                        binding.signUpWithSocial.setVisibility(View.GONE);
//                        binding.socials.setVisibility(View.GONE);
                        binding.btnSignUp.setText("Confirm");
                        view.setOnClickListener(view1 -> {
                            if (Constant.validation2(binding.pinView)) {
                                if (!Pattern.matches("^[0-9][0-9][0-9][0-9][0-9]$", binding.pinView.getText()))
                                    binding.pinView.setError("Check Verification Code Again.");
                                else {
                                    binding.pinView.setError("");
                                    binding.pinView.setVisibility(View.GONE);
                                    binding.country.setVisibility(View.VISIBLE);
                                    ((LinearLayout) binding.pinView.getParent()).removeView(binding.country);
                                    ((LinearLayout) binding.pinView.getParent()).addView(binding.country, 5);
                                    binding.referId.setVisibility(View.VISIBLE);
                                    binding.applicantName.setVisibility(View.VISIBLE);
                                    binding.applicantMobile.setVisibility(View.VISIBLE);
                                    binding.applicantEmail.setVisibility(View.VISIBLE);
                                    binding.division.setVisibility(View.VISIBLE);
                                    binding.registerInfo.setVisibility(View.VISIBLE);
                                    binding.agreePrivacyPolicy.setVisibility(View.VISIBLE);
                                    view1.setOnClickListener(view2 -> {
                                        if (Constant.validation(binding.referId, binding.applicantName, binding.applicantMobile, binding.division) && binding.agreePrivacyPolicy.isChecked()) {
                                            binding.agreePrivacyPolicy.setError("");
                                        } else {
                                            binding.agreePrivacyPolicy.setError("You didn't accept our privacy policy");
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
        binding.btnSignIn.setOnClickListener(view -> switchFragment(new LoginFragment(), "LoginFragment"));

        return binding.getRoot();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECEIVE_SMS}, REQ_USER_CODE);
        }

    }

    private void startUserConstant() {

        SmsRetrieverClient client = SmsRetriever.getClient(getContext());
        client.startSmsUserConsent(null);//if number is known then put number here

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOTPFromMessage(message);

            }
        }
    }

    private void getOTPFromMessage(String message) {
        Pattern otpPattern = Pattern.compile("(|^)\\d{5}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()) {
            binding.pinView.setHideLineWhenFilled(false);
            binding.pinView.setText(matcher.group(0));
        }

    }


    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent, REQ_USER_CODE);
            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        requireActivity().registerReceiver(smsBroadcastReceiver, intentFilter);


    }

    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(smsBroadcastReceiver);
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSignupFirstDataLoad(SignupModel signupModel) {

        if (signupModel.getError()==0) {
            binding.applicantMobile.setError("");
            binding.applicantEmail.setError("");
            binding.country.setVisibility(View.GONE);
            binding.country.setEnabled(false);
            binding.applicantMobile.setVisibility(View.GONE);
            binding.applicantMobile.setEnabled(false);
            binding.pinView.setVisibility(View.VISIBLE);
            binding.btnSignUp.setText("Confirm");
            btnView.setOnClickListener(view1 -> {
                btnView=view1;
                if (Constant.validation2(binding.pinView)) {
                    code = Integer.parseInt(binding.pinView.getText().toString());
                    if (!Pattern.matches("^[0-9][0-9][0-9][0-9][0-9]$", binding.pinView.getText()))
                        binding.pinView.setError("Check Verification Code Again.");
                    else {
                        dAdapter = new ArrayAdapter<String>(getContext(), R.layout.drop_down_single_item, districts);
                        dAdapter.setDropDownViewResource(R.layout.drop_down_single_item);
                        binding.selectDivision.setAdapter(dAdapter);
                        signupNumberVerifyPresenter.signupNumberVerifyDataLoad(mobileNumber, String.valueOf(countryValue), String.valueOf(code));
                    }
                }
            });
        }
    }

    @Override
    public void onSignupFirstStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onSignupFirstStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onSignupFirstShowMessage(String errmsg) {
        Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSignupNumberVerifyDataLoad(SignupNumberVerifyModel signupModel) {
        if (signupModel.getError()==0) {


            for (int i = 0; i < signupModel.getCountries().size(); i++) {

                ids.add(signupModel.getCountries().get(i).getId());
                districts.add(signupModel.getCountries().get(i).getDiven());
            }


            dAdapter.notifyDataSetChanged();

            binding.pinView.setError("");
            binding.pinView.setVisibility(View.GONE);
            binding.country.setVisibility(View.VISIBLE);
            ((LinearLayout) binding.pinView.getParent()).removeView(binding.country);
            ((LinearLayout) binding.pinView.getParent()).addView(binding.country, 5);
            binding.referId.setVisibility(View.VISIBLE);
            binding.applicantName.setVisibility(View.VISIBLE);
            binding.applicantMobile.setVisibility(View.VISIBLE);
            binding.applicantEmail.setVisibility(View.VISIBLE);
            binding.division.setVisibility(View.VISIBLE);
            binding.registerInfo.setVisibility(View.VISIBLE);
            binding.agreePrivacyPolicy.setVisibility(View.VISIBLE);
            btnView.setOnClickListener(view2 -> {
                if (Constant.validation(binding.referId, binding.applicantName, binding.applicantEmail, binding.division) && binding.agreePrivacyPolicy.isChecked()) {

                    signupFinalPresenter.signupFinalDataLoad( binding.applicantMobile.getEditText().getText().toString(),
                            binding.applicantName.getEditText().getText().toString(),
                            binding.referId.getEditText().getText().toString(),
                            binding.applicantEmail.getEditText().getText().toString(),
                            binding.division.getEditText().getText().toString(),
                            binding.country.getEditText().getText().toString());

//                                                                    MySingleton.getInstance(getContext()).addToRequestQueue(api.signUpFinal(
//                                                                            binding.applicantMobile.getEditText().getText().toString(),
//                                                                            binding.applicantName.getEditText().getText().toString(),
//                                                                            binding.referId.getEditText().getText().toString(),
//                                                                            binding.applicantEmail.getEditText().getText().toString(),
//                                                                            binding.division.getEditText().getText().toString(),
//                                                                            binding.country.getEditText().getText().toString(),
//                                                                            response2 -> {
//                                                                                try {
//                                                                                    if (response2.getString("error").equals("0")) {
//                                                                                        switchFragment(new HomeFragment(), "HomeFragment");
//                                                                                        binding.agreePrivacyPolicy.setError("");
//
//                                                                                    }
//                                                                                    Snackbar.make(view.getRootView(), response2.getString("msg"), Snackbar.LENGTH_LONG).show();
//
//                                                                                } catch (JSONException e) {
//                                                                                    e.printStackTrace();
//                                                                                }
//
//                                                                            }
//                                                                    ));
                } else {
                    binding.agreePrivacyPolicy.setError("You didn't accept our privacy policy");
                }
            });
        }
        Snackbar.make(btnView.getRootView(), signupModel.getData(), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onSignupNumberVerifyStartLoading() {
        loadingDialog.startLoadingAlertDialog();

    }

    @Override
    public void onSignupNumberVerifyStopLoading() {
        loadingDialog.dismissDialog();

    }

    @Override
    public void onSignupNumberVerifyShowMessage(String errmsg) {

    }

    @Override
    public void onSignupFinalDataLoad(SignupModel signupModel) {
        if (signupModel.getError()==0) {
            switchFragment(new LoginFragment(), "LoginFragment");
            binding.agreePrivacyPolicy.setError("");

        }
       // Snackbar.make(view.getRootView(), response2.getString("msg"), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSignupFinalStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onSignupFinalStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onSignupFinalShowMessage(String errmsg) {

    }
}