package co.wm21.https.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.LoginModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.view.activities.affiliate.HomeActivityAff;
import co.wm21.https.view.activities.mlm.HomeActivity;
import co.wm21.https.databinding.FragmentLoginBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.registration.RegisterFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnLoginView;
import co.wm21.https.presenter.LoginPresenter;

public class LoginFragment extends Fragment implements OnLoginView {

    public FragmentLoginBinding binding;

    public static User User;
    String userType = "", mobile = "", user_id = "", tree_id = "";

    LoginPresenter loginPresenter;
    String username;
    String password;
    LoadingDialog loadingDialog;
    MainActivity mainActivity;


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);


        binding.username.setHint("User id");
        User = new User(getContext());
        loginPresenter=new LoginPresenter(this);
        loadingDialog=new LoadingDialog(getActivity());
        binding.btnSignIn.setOnClickListener(view -> {
            if (ConstantValues.validation(binding.username, binding.password)) {
                 username = ConstantValues.getTextFromTIL(binding.username);
                 password = ConstantValues.getTextFromTIL(binding.password);
                 loginPresenter.login(username,password);

            }
        });
        mainActivity=(MainActivity) getActivity();
        binding.btnSignUp.setOnClickListener(view -> switchFragment(new RegisterFragment(), "RegisterFragment"));

        return binding.getRoot();
    }


    @Override
    public void onLoginDataLoad(LoginModel loginModel) {
        if (loginModel.getError() == 0) {
            userType = loginModel.getType();
            mobile = loginModel.getMobile();
            tree_id = loginModel.getTreeId();
            user_id = loginModel.getProfileId();
            User.getSession().loginUser(username, password, binding.rememberMe.isChecked(), userType, mobile, tree_id, user_id);
            if (userType.equals("MLM")){
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }

            else if (userType.equals("Aff")){
                startActivity(new Intent(getActivity(), HomeActivityAff.class));
            }
            else if (userType.equals("Eco")){

                mainActivity.bottomMenuItemAcc.setVisible(false);
            }

        } else
            openDismissButtonSnackbar(binding.loginFragment.getId(), loginModel.getErrorReport(), "OK", Snackbar.LENGTH_INDEFINITE);

    }

    @Override
    public void onLoginStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onLoginStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onLoginShowMessage(String errmsg) {
        openDismissButtonSnackbar(binding.loginFragment.getId(), errmsg, "OK", Snackbar.LENGTH_INDEFINITE);

    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }

    /**
     * Open Dismissible Button Snackbar is remove an arg of actionClick.
     * Because we set snackbar dismiss.
     *
     * @param id       What is the layout to insert a snackbar.
     * @param text     What is you want to write with on bottom snackbar.
     * @param action   What is your action text like "OK", "Cancel", "Learn more", "Action".
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
     *
     * @param id       What is the layout to insert a snackbar.
     * @param text     What is you want to write with on bottom snackbar.
     * @param duration What time during you see.
     */
    private void openSnackbar(@IdRes int id, String text, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(getActivity().findViewById(id), text, duration);
        snackbar.show();
    }

}