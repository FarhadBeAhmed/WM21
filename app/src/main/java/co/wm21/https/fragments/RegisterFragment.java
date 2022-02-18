package co.wm21.https.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

import co.wm21.https.helpers.Constant;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    public FragmentRegisterBinding binding;
    public Integer countryValue = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        String[] country = {"Australia", "Bangladesh", "Bhutan", "India", "Malaysia", "Nepal", "Pakistan", "Qatar", "Saudi Arabia", "Singapore", "South America", "Sri Lanka", "UAE", "UK", "USA"};

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
                    case "": countryValue = 0; break;
                    case "Sri Lanka": countryValue = 944; break;
                    case "India": countryValue = 945; break;
                    case "Pakistan": countryValue = 946; break;
                    case "Bangladesh": countryValue = 947; break;
                    case "Nepal": countryValue = 948; break;
                    case "Bhutan": countryValue = 949; break;
                    case "Malaysia": countryValue = 951; break;
                    case "Singapore": countryValue = 952; break;
                    case "Australia": countryValue = 953; break;
                    case "UK": countryValue = 954; break;
                    case "USA": countryValue = 955; break;
                    case "UAE": countryValue = 956; break;
                    case "Qatar": countryValue = 1033; break;
                    case "Saudi Arabia": countryValue = 1034; break;
                    case "South America": countryValue = 1035; break;
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
                        binding.applicantMobile.setError("");
                        binding.applicantEmail.setError("");
                        binding.country.setVisibility(View.GONE);
                        binding.country.setEnabled(false);
                        binding.applicantMobile.setVisibility(View.GONE);
                        binding.applicantMobile.setEnabled(false);
                        binding.verificationCode.setVisibility(View.VISIBLE);
                        binding.signUpWithSocial.setVisibility(View.GONE);
                        binding.socials.setVisibility(View.GONE);
                        binding.btnSignUp.setText("Confirm");
                        view.setOnClickListener(view1 -> {
                            if (Constant.validation(binding.verificationCode)) {
                                if (!Pattern.matches("^[0-9][0-9][0-9][0-9][0-9]$", binding.verificationCode.getEditText().getText()))
                                    binding.verificationCode.setError("Check Verification Code Again.");
                                else {
                                    binding.verificationCode.setError("");
                                    binding.verificationCode.setVisibility(View.GONE);
                                    binding.country.setVisibility(View.VISIBLE);
                                    ((LinearLayout) binding.verificationCode.getParent()).removeView(binding.country);
                                    ((LinearLayout) binding.verificationCode.getParent()).addView(binding.country, 5);
                                    binding.referId.setVisibility(View.VISIBLE);
                                    binding.applicantName.setVisibility(View.VISIBLE);
                                    binding.applicantMobile.setVisibility(View.VISIBLE);
                                    binding.applicantEmail.setVisibility(View.VISIBLE);
                                    binding.division.setVisibility(View.VISIBLE);
                                    binding.password.setVisibility(View.VISIBLE);
                                    binding.registerInfo.setVisibility(View.VISIBLE);
                                    binding.agreePrivacyPolicy.setVisibility(View.VISIBLE);
                                    view1.setOnClickListener(view2 -> {
                                        if (Constant.validation(binding.referId, binding.applicantName, binding.applicantEmail, binding.division, binding.password) && binding.agreePrivacyPolicy.isChecked()) {
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
                        binding.verificationCode.setVisibility(View.VISIBLE);
                        binding.signUpWithSocial.setVisibility(View.GONE);
                        binding.socials.setVisibility(View.GONE);
                        binding.btnSignUp.setText("Confirm");
                        view.setOnClickListener(view1 -> {
                            if (Constant.validation(binding.verificationCode)) {
                                if (!Pattern.matches("^[0-9][0-9][0-9][0-9][0-9]$", binding.verificationCode.getEditText().getText()))
                                    binding.verificationCode.setError("Check Verification Code Again.");
                                else {
                                    binding.verificationCode.setError("");
                                    binding.verificationCode.setVisibility(View.GONE);
                                    binding.country.setVisibility(View.VISIBLE);
                                    ((LinearLayout) binding.verificationCode.getParent()).removeView(binding.country);
                                    ((LinearLayout) binding.verificationCode.getParent()).addView(binding.country, 5);
                                    binding.referId.setVisibility(View.VISIBLE);
                                    binding.applicantName.setVisibility(View.VISIBLE);
                                    binding.applicantMobile.setVisibility(View.VISIBLE);
                                    binding.applicantEmail.setVisibility(View.VISIBLE);
                                    binding.division.setVisibility(View.VISIBLE);
                                    binding.password.setVisibility(View.VISIBLE);
                                    binding.registerInfo.setVisibility(View.VISIBLE);
                                    binding.agreePrivacyPolicy.setVisibility(View.VISIBLE);
                                    view1.setOnClickListener(view2 -> {
                                        if (Constant.validation(binding.referId, binding.applicantName, binding.applicantMobile, binding.division, binding.password) && binding.agreePrivacyPolicy.isChecked()) {
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

//        NavigateLogin.setOnClickListener(view -> {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        });

        return binding.getRoot();
    }
}