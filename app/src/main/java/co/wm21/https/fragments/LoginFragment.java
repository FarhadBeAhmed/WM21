package co.wm21.https.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import co.wm21.https.R;
import co.wm21.https.api_request.Json;
import co.wm21.https.databinding.FragmentLoginBinding;
import co.wm21.https.databinding.FragmentMainHomeBinding;
import co.wm21.https.helpers.API;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;

public class LoginFragment extends Fragment {

    public FragmentLoginBinding binding;

    public static API API;
    public static User User;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        API = Constant.getAPI();
        User = new User(getContext());

//        NavigateRegister.setOnClickListener(view -> {
//            startActivity(new Intent(this, RegisterActivity.class));
//            finish();
//        });

        binding.btnSignIn.setOnClickListener(view -> {
            if (Constant.validation(binding.username, binding.password)) {
                String user = Constant.getTextFromTIL(binding.username);
                String pass = Constant.getTextFromTIL(binding.password);

                openSnackbar(binding.loginFragment.getId(), "Logging In.. Please wait...", Snackbar.LENGTH_INDEFINITE);

                Json.addRequests(API.login(user, pass).success(response -> {
                    try {
                        if (response.getInt(Constant.Login.STATUS) == 0) {
                            User.getSession().loginUser(user, pass, binding.rememberMe.isChecked(), response.getString(Constant.Login.TYPE));
                            switchFragment(new HomeFragment());
                        } else openDismissButtonSnackbar(binding.loginFragment.getId(), response.getString(Constant.Login.MESSAGE), "OK", Snackbar.LENGTH_INDEFINITE);
                    } catch (JSONException e) { e.printStackTrace(); }
                }));
            }
        });

        return binding.getRoot();
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    /**
     * Open Dissmisibble Button Snackbar is remove an arg of actionClick.
     * Because we set snackbar dismiss.
     * @param id What is the layout to insert a snackbar.
     * @param text What is you want to write with on bottom snackbar.
     * @param action What is your action text like "OK", "Cancel", "Learn more", "Action".
     * @param duration What time during you see.
     */
    private void openDismissButtonSnackbar(@IdRes int id, String text, String action, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(getActivity().findViewById(id), text, duration);
        snackbar.setAction(action, v -> snackbar.dismiss());
        snackbar.show();
    }

    /**
     * Open Snackbar.
     * @param id What is the layout to insert a snackbar.
     * @param text What is you want to write with on bottom snackbar.
     * @param duration What time during you see.
     */
    private void openSnackbar(@IdRes int id, String text, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(getActivity().findViewById(id), text, duration);
        snackbar.show();
    }
}