package co.wm21.https.view.activities.mlm;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.databinding.ActivityVerifyAccountBinding;
import co.wm21.https.databinding.FragmentContactUpdateBinding;
import co.wm21.https.databinding.FragmentGatewayVerifyBinding;
import co.wm21.https.databinding.FragmentMobileVerifyBinding;
import co.wm21.https.databinding.FragmentProfileUpdateBinding;
import co.wm21.https.databinding.FragmentUpdatePasswordBinding;
import co.wm21.https.view.fragments.member.verifications.VerifyAccountFragment;
import co.wm21.https.utils.Constant;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;

public class VerifyAccountActivity extends AppCompatActivity {

    ActivityVerifyAccountBinding binding;
    int taskNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityVerifyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            Intent intent = getIntent();
          taskNum = intent.getIntExtra("TabInvoke",0);

        } catch (Exception e) {

        }
        if (taskNum != 0) {
            switch (taskNum) {
                case 1:
                    switchFragment(new VerifyAccountFragment.SignUploadFragment(), "SignUploadFragment");
                    break;
                case 2:
                    switchFragment(new VerifyAccountFragment.PhotoUploadFragment(), "PhotoUploadFragment");

                    break;

                case 3:
                    switchFragment(new VerifyAccountFragment.GatewayVerifyFragment(), "GatewayVerifyFragment");

                    break;

                case 4:
                    switchFragment(new VerifyAccountFragment.ContactUpdateFragment(), "ContactUpdateFragment");

                    break;

                case 5:
                    switchFragment(new VerifyAccountFragment.ProfileUpdateFragment(), "ProfileUpdateFragment");
                    break;

                case 6:
                    switchFragment(new VerifyAccountFragment.UpdatePasswordFragment(), "UpdatePasswordFragment");

                    break;

                case 7:
                    switchFragment(new VerifyAccountFragment.MobileVerifyFragment(), "MobileVerifyFragment");
                    break;

                default:
            }
        }

    }

    public static class MobileVerifyFragment extends Fragment {
        FragmentMobileVerifyBinding binding;
        API api;
        User user;


        @SuppressLint("SetTextI18n")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mobile_verify, container, false);

            api = ConstantValues.getAPI();
            user = new User();


            MySingleton.getInstance(getContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
                try {
                    binding.phoneNumber.getEditText().setText(response.getString(ConstantValues.profile.MOBILE));
                    boolean isVerified = response.getInt("verify3") == 1;
                    if (isVerified) {
                        binding.phoneVerifyCode.setVisibility(View.GONE);
                        binding.phoneSendVerificationBtn.setVisibility(View.GONE);
                        binding.phoneVerifyBtn.setText("Verified");
                        binding.phoneVerifyBtn.setEnabled(false);
                    } else {
                        binding.phoneVerifyCode.setVisibility(View.GONE);
                        binding.phoneSendVerificationBtn.setVisibility(View.VISIBLE);
                        binding.phoneVerifyBtn.setVisibility(View.GONE);
                        binding.phoneNumber.setVisibility(View.VISIBLE);

                    }
                    // openSnackbar(binding.getRoot(), binding.fragmentProfilePhone.getId(), response.getString("msg"), Snackbar.LENGTH_LONG);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));

            binding.phoneSendVerificationBtn.setOnClickListener(v -> mobileVerification(1, ""));

            binding.phoneVerifyBtn.setOnClickListener(v -> mobileVerification(2, ConstantValues.getTextFromTextInputLayout(binding.phoneVerifyCode)));


            return binding.getRoot();
        }


        @SuppressLint("SetTextI18n")
        public void mobileVerification(int mob, String code2) {
            MySingleton.getInstance(getContext()).addToRequestQueue(api.mobileVerify(user.getUsername(), mob, code2, response -> {
                try {
                    if (response.getString("error").equals("0")) {
                        binding.phoneVerifyCode.setVisibility(View.VISIBLE);
                        binding.phoneSendVerificationBtn.setVisibility(View.GONE);
                        binding.phoneVerifyBtn.setVisibility(View.VISIBLE);
                        binding.phoneNumber.setVisibility(View.GONE);
                    }
                    if (response.getString("error").equals("2")) {
                        binding.phoneVerifyCode.setVisibility(View.GONE);
                        binding.phoneSendVerificationBtn.setVisibility(View.GONE);
                        binding.phoneVerifyBtn.setText("Verified");
                        binding.phoneVerifyBtn.setEnabled(false);
                    }
                    openDismissButtonSnackbar(binding.getRoot(), R.id.fragment_profile_phone, response.getString("msg3"), "OK", Snackbar.LENGTH_LONG);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    public static class PhotoUploadFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_photo_upload, container, false);
        }
    }

    public static class SignUploadFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_sign_upload, container, false);
        }
    }

    public static class GatewayVerifyFragment extends Fragment {
        FragmentGatewayVerifyBinding binding;
        API api;
        User user;
        //_String bkash="",rocket="",nagad="",bankName="",accountName="",accountNo="",branchName="",routing="",creditCard="",paypal="",skrill="",bitcoin="",neteller="";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gateway_verify, container, false);
            api = ConstantValues.getAPI();
            user = new User(getContext());
            if (user.getUsername() != null && user.getPassword() != null) {
                MySingleton.getInstance(getContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            binding.prName.setText(response.getString(ConstantValues.profile.NAME));
                            Constant.setImageToImageView(binding.profileImage, "user", "photo", response.getString(ConstantValues.profile.IMAGE));
                            binding.userID.setText(response.getString(ConstantValues.profile.USER_ID).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.USER_ID));

                            if (!response.getString(ConstantValues.profile.MOBILE).equals("")) {
                                binding.mobileNumber.getEditText().setText(response.getString(ConstantValues.profile.MOBILE));
                                binding.mobileNumber.getEditText().setEnabled(false);
                                binding.mobileNumber.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.BKASH).equals("")) {
                                binding.bkashAccountNumber.getEditText().setText(response.getString(ConstantValues.profile.BKASH));
                                binding.bkashAccountNumber.getEditText().setEnabled(false);
                                binding.bkashAccountNumber.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.ROCKET).equals("")) {
                                binding.rocketAccountNumber.getEditText().setText(response.getString(ConstantValues.profile.ROCKET));
                                binding.rocketAccountNumber.getEditText().setEnabled(false);
                                binding.rocketAccountNumber.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.NAGAD).equals("")) {
                                binding.nagadAccountNumber.getEditText().setText(response.getString(ConstantValues.profile.NAGAD));
                                binding.nagadAccountNumber.getEditText().setEnabled(false);
                                binding.nagadAccountNumber.getEditText().setFocusable(false);

                            }
                            if (!response.getString(ConstantValues.profile.BANK_NAME).equals("")) {
                                binding.bankName.getEditText().setText(response.getString(ConstantValues.profile.BANK_NAME));
                                binding.bankName.getEditText().setEnabled(false);
                                binding.bankName.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.ACCOUNT_NAME).equals("")) {
                                binding.accountName.getEditText().setText(response.getString(ConstantValues.profile.ACCOUNT_NAME));
                                binding.accountName.getEditText().setEnabled(false);
                                binding.accountName.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.ACCOUNT_NUMBER).equals("")) {
                                binding.accountNumber.getEditText().setText(response.getString(ConstantValues.profile.ACCOUNT_NUMBER));
                                binding.accountNumber.getEditText().setEnabled(false);
                                binding.accountNumber.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.BRANCH_NAME).equals("")) {
                                binding.branchName.getEditText().setText(response.getString(ConstantValues.profile.BRANCH_NAME));
                                binding.branchName.getEditText().setEnabled(false);
                                binding.branchName.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.ROUTING).equals("")) {
                                binding.routing.getEditText().setText(response.getString(ConstantValues.profile.ROUTING));
                                binding.routing.getEditText().setEnabled(false);
                                binding.routing.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.CREDIT_CARD).equals("")) {
                                binding.creditCard.getEditText().setText(response.getString(ConstantValues.profile.CREDIT_CARD));
                                binding.creditCard.getEditText().setEnabled(false);
                                binding.creditCard.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.PAYPAL).equals("")) {
                                binding.paypal.getEditText().setText(response.getString(ConstantValues.profile.PAYPAL));
                                binding.paypal.getEditText().setEnabled(false);
                                binding.paypal.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.SKRILL).equals("")) {
                                binding.skrill.getEditText().setText(response.getString(ConstantValues.profile.SKRILL));
                                binding.skrill.getEditText().setEnabled(false);
                                binding.skrill.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.BITCOIN).equals("")) {
                                binding.bitcoin.getEditText().setText(response.getString(ConstantValues.profile.BITCOIN));
                                binding.bitcoin.getEditText().setEnabled(false);
                                binding.bitcoin.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.NETELLER).equals("")) {
                                binding.neteller.getEditText().setText(response.getString(ConstantValues.profile.NETELLER));
                                binding.neteller.getEditText().setEnabled(false);
                                binding.neteller.getEditText().setFocusable(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
            }

            binding.sendPinBtn.setOnClickListener(this::sendCodeBtnClicked);
            binding.updateSubmitBtn.setOnClickListener(this::updateSubmitBtnClicked);

            return binding.getRoot();
        }

        private void updateSubmitBtnClicked(View view) {
            if (ConstantValues.validation(binding.pinCode)){
                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                        .content(getResources().getString(R.string.pleaseWait))
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.gatewayUpdateForVar(
                        user.getUsername(), user.getPassword(),
                        binding.bkashAccountNumber.getEditText().getText().toString(),
                        binding.rocketAccountNumber.getEditText().getText().toString(),
                        binding.nagadAccountNumber.getEditText().getText().toString(),
                        binding.bankName.getEditText().getText().toString(),
                        binding.accountName.getEditText().getText().toString(),
                        binding.accountNumber.getEditText().getText().toString(),
                        binding.branchName.getEditText().getText().toString(),
                        binding.routing.getEditText().getText().toString(),
                        binding.creditCard.getEditText().getText().toString(),
                        binding.paypal.getEditText().getText().toString(),
                        binding.skrill.getEditText().getText().toString(),
                        binding.bitcoin.getEditText().getText().toString(),
                        binding.neteller.getEditText().getText().toString(),
                        binding.pinCode.getEditText().getText().toString(),
                        response -> {
                            try {
                                if (response.getString(ConstantValues.ERROR).equals("0")){
                                    switchFragment(new VerifyAccountFragment.PhotoUploadFragment(), "PhotoUploadFragment");
                                }
                                dialog.dismiss();
                                showSnackBar(response.getString(ConstantValues.MSG));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                ));

            }
        }

        private void sendCodeBtnClicked(View view) {

            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources().getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            MySingleton.getInstance(getContext()).addToRequestQueue(api.sendOTPForGatewayVar(user.getUsername(), user.getPassword(), response -> {
                try {
                    if (response.getString(ConstantValues.ERROR).equals("0")) {
                        binding.confirmationTxt.setText(response.getString(ConstantValues.MSG));
                        binding.confirmationTxt.setVisibility(View.VISIBLE);
                    }
                    dialog.dismiss();
                    showSnackBar(response.getString(ConstantValues.MSG));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));

        }

        public void switchFragment(Fragment fragment, String tag) {
            FragmentManager fm = getChildFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                fm.popBackStack();
            fm.beginTransaction().replace(R.id.mem_contain, fragment, tag).addToBackStack(tag).commit();
        }

        private void showSnackBar(String msg) {

            Snackbar snackbar = Snackbar.make(binding.contextView, msg, Snackbar.LENGTH_SHORT);

            /*View view = snackbar.getView();
            TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/
            snackbar.show();
        }
    }

    public static class ContactUpdateFragment extends Fragment {
        FragmentContactUpdateBinding binding;
        API api;
        User user;
        String countryId = "", divId = "", disId = "", thanaId = "", unionId = "";

        ArrayList<String> countryList, divList, disList, thanaList, unionList;
        ArrayList<String> countryListId, divListId, disListId, thanaListId, unionListId;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_update, container, false);
            api = ConstantValues.getAPI();
            user = new User(getContext());
            if (user.getUsername() != null && user.getPassword() != null) {
                MySingleton.getInstance(getContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            binding.prName.setText(response.getString(ConstantValues.profile.NAME));
                            Constant.setImageToImageView(binding.profileImage, "user", "photo", response.getString(ConstantValues.profile.IMAGE));
                            binding.userID.setText(response.getString(ConstantValues.profile.USER_ID).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.USER_ID));


                            if (!response.getString(ConstantValues.profile.COUNTRY).equals("")) {
                                countryId = response.getString(ConstantValues.profile.COUNTRY_ID);
                                binding.countryTxt.setText(response.getString(ConstantValues.profile.COUNTRY));
                                if (!response.getString(ConstantValues.profile.DIVISION).equals("")) {
                                    divId = response.getString(ConstantValues.profile.DIVISION_ID);
                                    binding.divisionTxt.setText(response.getString(ConstantValues.profile.DIVISION));
                                    if (!response.getString(ConstantValues.profile.DISTRICT).equals("")) {
                                        disId = response.getString(ConstantValues.profile.DISTRICT_ID);
                                        binding.districtTxt.setText(response.getString(ConstantValues.profile.DISTRICT));
                                        if (!response.getString(ConstantValues.profile.THANA).equals("")) {
                                            thanaId = response.getString(ConstantValues.profile.THANA_ID);
                                            binding.thanaTxt.setText(response.getString(ConstantValues.profile.THANA));
                                            if (!response.getString(ConstantValues.profile.UNION).equals("")) {
                                                unionId = response.getString(ConstantValues.profile.UNION_ID);
                                                binding.unionTxt.setText(response.getString(ConstantValues.profile.UNION));
                                            } else
                                                getLocation(ConstantValues.profile.UNION, response.getString(ConstantValues.profile.THANA));
                                        } else
                                            getLocation(ConstantValues.profile.THANA, response.getString(ConstantValues.profile.DISTRICT));
                                    } else
                                        getLocation(ConstantValues.profile.DISTRICT, response.getString(ConstantValues.profile.DIVISION));
                                } else
                                    getLocation(ConstantValues.profile.DIVISION, response.getString(ConstantValues.profile.COUNTRY));
                            } else getLocation(ConstantValues.location.COUNTRY, "");


                            if (!response.getString(ConstantValues.profile.WORD).equals("")) {
                                binding.word.getEditText().setText(response.getString(ConstantValues.profile.WORD));
                            }
                            if (!response.getString(ConstantValues.profile.ADDRESS).equals("")) {
                                binding.Village.getEditText().setText(response.getString(ConstantValues.profile.ADDRESS));
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
            }

            binding.countryTxt.setOnItemClickListener((adapterView, view, i, l) -> {
                String val = adapterView.getAdapter().getItem(i).toString();
                countryId = countryListId.get(i);
                getLocation(ConstantValues.profile.DIVISION, val);
            });
            binding.divisionTxt.setOnItemClickListener((adapterView, view, i, l) -> {
                String val = adapterView.getAdapter().getItem(i).toString();
                divId = divListId.get(i);
                getLocation(ConstantValues.profile.DISTRICT, val);
            });
            binding.districtTxt.setOnItemClickListener((adapterView, view, i, l) -> {
                String val = adapterView.getAdapter().getItem(i).toString();
                disId = disListId.get(i);
                getLocation(ConstantValues.profile.THANA, val);
            });
            binding.thanaTxt.setOnItemClickListener((adapterView, view, i, l) -> {
                String val = adapterView.getAdapter().getItem(i).toString();
                thanaId = thanaListId.get(i);
                getLocation(ConstantValues.profile.UNION, val);
            });
            binding.unionTxt.setOnItemClickListener((adapterView, view, i, l) -> {
                String val = adapterView.getAdapter().getItem(i).toString();
                unionId = unionListId.get(i);
            });
            binding.updateSubmitBtn.setOnClickListener(this::submitBtnClicked);
            return binding.getRoot();
        }

        private void submitBtnClicked(View view) {

            if (!user.getUsername().equals("") && !user.getPassword().equals("")) {

                String word = binding.word.getEditText().getText().toString();
                String village = binding.Village.getEditText().getText().toString();

                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                        .content(getResources().getString(R.string.pleaseWait))
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.contactUpdateForVar(
                        user.getUsername(), user.getPassword(), countryId, divId, disId, thanaId, unionId, word, village,
                        response -> {
                            try {
                                if (response.getString("error").equals("0"))
                                    switchFragment(new VerifyAccountFragment.GatewayVerifyFragment(), "GatewayVerifyFragment");
                                dialog.dismiss();
                                showSnackBar(response.getString("msg"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                ));
            }


        }

        @SuppressLint("SetTextI18n")
        private void getLocation(String id, String val) {
            if (id.equals(ConstantValues.location.COUNTRY)) {
                countryList = new ArrayList<>();
                countryListId = new ArrayList<>();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.addresses(id, val, response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            JSONArray countryArray = response.getJSONArray("results");
                            for (int i = 0; i < countryArray.length(); i++) {
                                JSONObject cObj = countryArray.getJSONObject(i);
                                countryList.add(cObj.getString(ConstantValues.VALUE));
                                countryListId.add(cObj.getString(ConstantValues.ID));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (countryList.isEmpty()) {
                        countryList.add("Not Applicable");
                    }
                }));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, countryList);
                binding.countryTxt.setAdapter(adapter);
            } else if (id.equals(ConstantValues.profile.DIVISION)) {
                divList = new ArrayList<>();
                divListId = new ArrayList<>();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.addresses(id, val, response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            JSONArray countryArray = response.getJSONArray("results");
                            for (int i = 0; i < countryArray.length(); i++) {
                                JSONObject cObj = countryArray.getJSONObject(i);
                                divList.add(cObj.getString(ConstantValues.VALUE));
                                divListId.add(cObj.getString(ConstantValues.ID));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, divList);
                binding.divisionTxt.setAdapter(adapter);
            } else if (id.equals(ConstantValues.profile.DISTRICT)) {
                disList = new ArrayList<>();
                disListId = new ArrayList<>();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.addresses(id, val, response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            JSONArray countryArray = response.getJSONArray("results");
                            for (int i = 0; i < countryArray.length(); i++) {
                                JSONObject cObj = countryArray.getJSONObject(i);
                                disList.add(cObj.getString(ConstantValues.VALUE));
                                disListId.add(cObj.getString(ConstantValues.ID));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, disList);
                binding.districtTxt.setAdapter(adapter);
            } else if (id.equals(ConstantValues.profile.THANA)) {
                thanaList = new ArrayList<>();
                thanaListId = new ArrayList<>();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.addresses(id, val, response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            JSONArray countryArray = response.getJSONArray("results");
                            for (int i = 0; i < countryArray.length(); i++) {
                                JSONObject cObj = countryArray.getJSONObject(i);
                                thanaList.add(cObj.getString(ConstantValues.VALUE));
                                thanaListId.add(cObj.getString(ConstantValues.ID));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, thanaList);
                binding.thanaTxt.setAdapter(adapter);
            } else if (id.equals(ConstantValues.profile.UNION)) {
                unionList = new ArrayList<>();
                unionListId = new ArrayList<>();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.addresses(id, val, response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            JSONArray countryArray = response.getJSONArray("results");
                            for (int i = 0; i < countryArray.length(); i++) {
                                JSONObject cObj = countryArray.getJSONObject(i);
                                unionList.add(cObj.getString(ConstantValues.VALUE));
                                unionListId.add(cObj.getString(ConstantValues.ID));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (unionList.isEmpty()) {
                        unionList.add("Not Applicable");
                    }
                }));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, unionList);
                binding.unionTxt.setAdapter(adapter);
            }
        }

        public void switchFragment(Fragment fragment, String tag) {
            FragmentManager fm = getChildFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                fm.popBackStack();
            fm.beginTransaction().replace(R.id.mem_contain, fragment, tag).addToBackStack(tag).commit();
        }

        private void showSnackBar(String msg) {
            Snackbar snackbar = Snackbar.make(binding.contextView, msg, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    public static class ProfileUpdateFragment extends Fragment {
        FragmentProfileUpdateBinding binding;
        API api;
        User user;
        SessionHandler sessionHandler;
        ArrayAdapter<String> genders, bloodGroups;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_update, container, false);
            api = ConstantValues.getAPI();
            user = new User(getContext());
            sessionHandler = new SessionHandler(requireContext());

            if (user.getUsername() != null && user.getPassword() != null) {
                MySingleton.getInstance(getContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            binding.prName.setText(response.getString(ConstantValues.profile.NAME));
                            Constant.setImageToImageView(binding.profileImage, "user", "photo", response.getString(ConstantValues.profile.IMAGE));
                            binding.userID.setText(response.getString(ConstantValues.profile.USER_ID).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.USER_ID));

                            if (!response.getString(ConstantValues.profile.NAME).equals("")) {
                                binding.name.getEditText().setText(response.getString(ConstantValues.profile.NAME));
                                binding.name.getEditText().setEnabled(false);
                                binding.name.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.MOBILE).equals("")) {
                                binding.number.getEditText().setText(response.getString(ConstantValues.profile.MOBILE));
                                binding.number.getEditText().setEnabled(false);
                                binding.number.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.FATHER).equals("")) {
                                binding.fathersName.getEditText().setText(response.getString(ConstantValues.profile.FATHER));
                                binding.fathersName.getEditText().setEnabled(false);
                                binding.fathersName.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.MOTHER).equals("")) {
                                binding.mothersName.getEditText().setText(response.getString(ConstantValues.profile.MOTHER));
                                binding.mothersName.getEditText().setEnabled(false);
                                binding.mothersName.getEditText().setFocusable(false);

                            }
                            if (!response.getString(ConstantValues.profile.BIRTH).equals("")) {
                                binding.dateOfBirth.getEditText().setText(response.getString(ConstantValues.profile.BIRTH));
                                binding.dateOfBirth.getEditText().setEnabled(false);
                                binding.dateOfBirth.getEditText().setFocusable(false);
                                binding.datePickCard.setVisibility(View.GONE);
                            } else {
                                binding.datePickCard.setVisibility(View.VISIBLE);
                                binding.datePickBtn.setOnClickListener(this::datePick);

                            }
                            if (!response.getString(ConstantValues.profile.GENDER).equals("")) {
                                binding.genderTxt.setText(response.getString(ConstantValues.profile.GENDER));
                                binding.genderTxt.setEnabled(false);
                                binding.genderTxt.setFocusable(false);
                            } else {
                                genders = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, getResources().getStringArray(R.array.genderArray));
                                binding.genderTxt.setAdapter(genders);
                            }
                            if (!response.getString(ConstantValues.profile.BLOOD).equals("")) {
                                binding.bloodTxt.setText(response.getString(ConstantValues.profile.BLOOD));
                                binding.bloodTxt.setEnabled(false);
                                binding.bloodTxt.setFocusable(false);
                            } else {
                                bloodGroups = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, getResources().getStringArray(R.array.bloodArray));
                                binding.bloodTxt.setAdapter(bloodGroups);
                            }
                            if (!response.getString(ConstantValues.profile.PROFESSION).equals("")) {
                                binding.profession.getEditText().setText(response.getString(ConstantValues.profile.PROFESSION));
                                binding.profession.getEditText().setEnabled(false);
                                binding.profession.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.EDUCATION).equals("")) {
                                binding.education.getEditText().setText(response.getString(ConstantValues.profile.EDUCATION));
                                binding.education.getEditText().setEnabled(false);
                                binding.education.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.RELIGION).equals("")) {
                                binding.religion.getEditText().setText(response.getString(ConstantValues.profile.RELIGION));
                                binding.religion.getEditText().setEnabled(false);
                                binding.religion.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.NOMINEE).equals("")) {
                                binding.nominee.getEditText().setText(response.getString(ConstantValues.profile.NOMINEE));
                                binding.nominee.getEditText().setEnabled(false);
                                binding.nominee.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.NOMINEE_RELATION).equals("")) {
                                binding.nomineeRelation.getEditText().setText(response.getString(ConstantValues.profile.NOMINEE_RELATION));
                                binding.nomineeRelation.getEditText().setEnabled(false);
                                binding.nomineeRelation.getEditText().setFocusable(false);
                            }
                            if (!response.getString(ConstantValues.profile.EMAIL).equals("")) {
                                binding.email.getEditText().setText(response.getString(ConstantValues.profile.EMAIL));
                                binding.email.getEditText().setEnabled(false);
                                binding.email.getEditText().setFocusable(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
            }

            binding.updateSubmitBtn.setOnClickListener(this::submit);

            return binding.getRoot();
        }

        @SuppressLint("SimpleDateFormat")
        private void datePick(View view) {

            MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
            // builder.setTheme(R.style.MyDatePickerDialogTheme);
            builder.setTitleText("SELECT A DATE");
            final MaterialDatePicker materialDatePicker = builder.build();
            materialDatePicker.show(getChildFragmentManager(), "DATE_PICKER");
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                // Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
                String formattedDate = format.format(calendar.getTime());

                binding.dateOfBirth.getEditText().setText(formattedDate);
            });


        }

        private void submit(View view) {
            if (!user.getUsername().equals("") && !user.getPassword().equals("")) {


                String fName = binding.fathersName.getEditText().getText().toString();
                String mName = binding.mothersName.getEditText().getText().toString();
                String dBirth = binding.dateOfBirth.getEditText().getText().toString();
                String gen = binding.gender.getEditText().getText().toString();
                String bGroup = binding.bloodGroup.getEditText().getText().toString();
                String prof = binding.profession.getEditText().getText().toString();
                String edu = binding.education.getEditText().getText().toString();
                String rel = binding.religion.getEditText().getText().toString();
                String nom = binding.nominee.getEditText().getText().toString();
                String nomRel = binding.nomineeRelation.getEditText().getText().toString();

                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                        .content(getResources().getString(R.string.pleaseWait))
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
                MySingleton.getInstance(getContext()).addToRequestQueue(api.profileUpdateForVar(
                        user.getUsername(), user.getPassword(), fName, mName, dBirth, gen, bGroup, prof, edu, rel, nom, nomRel,
                        response -> {
                            try {
                                if (response.getString("error").equals("0"))
                                    switchFragment(new VerifyAccountFragment.ContactUpdateFragment(), "ContactUpdateFragment");
                                dialog.dismiss();
                                showSnackBar(response.getString("msg"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                ));
            }
        }

        public void switchFragment(Fragment fragment, String tag) {
            FragmentManager fm = getChildFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                fm.popBackStack();
            fm.beginTransaction().replace(R.id.mem_contain, fragment, tag).addToBackStack(tag).commit();
        }

        private void showSnackBar(String msg) {

            Snackbar snackbar = Snackbar.make(binding.contextView, msg, Snackbar.LENGTH_SHORT);

            /*View view = snackbar.getView();
            TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/
            snackbar.show();
        }
    }

    public static class UpdatePasswordFragment extends Fragment {
        FragmentUpdatePasswordBinding binding;
        API api;
        User user;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_password, container, false);
            api = ConstantValues.getAPI();
            user = new User(getContext());
            binding.submitBtn.setOnClickListener(this::submitBtnClicked);
            return binding.getRoot();
        }

        private void submitBtnClicked(View view) {
            if (ConstantValues.validation(binding.confirmPass, binding.newPass, binding.currentPass)) {
                if (binding.newPass.getEditText().getText().toString().equals(binding.confirmPass.getEditText().getText().toString())) {
                    MySingleton.getInstance(getContext()).addToRequestQueue(api.updatePassword(user.getUsername(), binding.currentPass.getEditText().getText().toString(), binding.newPass.getEditText().getText().toString(), response -> {
                        try {
                            if (response.getString("error").equals("0")) {

                            }
                            showSnackBar(response.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }));
                } else {
                    showSnackBar("confirm Password does not Match");
                }
            }
        }

        private void showSnackBar(String msg) {

            Snackbar snackbar = Snackbar.make(binding.contextView, msg, Snackbar.LENGTH_LONG);

            View view = snackbar.getView();
            TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private static void openDismissButtonSnackbar(View view, @IdRes int id, String text, String action, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view.findViewById(id), text, duration);
        snackbar.setAction(action, v -> snackbar.dismiss());
        snackbar.show();
    }

    private static void openSnackbar(View view, @IdRes int id, String text, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view.findViewById(id), text, duration);
        snackbar.show();
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.contain_verify_Acc, fragment, tag).addToBackStack(tag).commit();
    }
}