package co.wm21.https.activities;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.activities.affiliate.HomeActivityAff;
import co.wm21.https.activities.mlm.HomeActivity;
import co.wm21.https.databinding.ActivityLoginBinding;
import co.wm21.https.fragments.registration.RegisterFragment;
import co.wm21.https.helpers.User;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    public static co.wm21.https.helpers.User User;
    String userType = "", mobile = "", user_id = "", tree_id = "";


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User = new User(this);

        binding.btnSignIn.setOnClickListener(view -> {
            if (ConstantValues.validation(binding.username, binding.password)) {
                String user = ConstantValues.getTextFromTIL(binding.username);
                String pass = ConstantValues.getTextFromTIL(binding.password);

                //openSnackbar(binding.loginFragment.getId(), "Logging In.. Please wait...", Snackbar.LENGTH_INDEFINITE);

                co.wm21.https.FHelper.API api = ConstantValues.getAPI();
                MySingleton.getInstance(this).addToRequestQueue(api.login(user, pass, response -> {
                    try {//wm78032675
                        if (response.getInt(ConstantValues.Login.STATUS) == 0) {
                            userType = response.getString(ConstantValues.Login.TYPE);
                            mobile = response.getString(ConstantValues.Login.MOBILE);
                            tree_id = response.getString(ConstantValues.Login.TREE_ID);
                            user_id = response.getString(ConstantValues.Login.USER_ID);
                            User.getSession().loginUser(user, pass, binding.rememberMe.isChecked(), userType, mobile, tree_id, user_id);
                            if (userType.equals("MLM"))
                                startActivity(new Intent(this, HomeActivity.class));
                            //switchFragment(new MlmFragment(), "MlmFragment");
                            if (userType.equals("Aff"))
                                //switchFragment(new AffiliateFragment(), "AffiliateFragment");
                            startActivity(new Intent(this, HomeActivityAff.class));
                        } else
                            openDismissButtonSnackbar(binding.loginFragment.getId(), response.getString(ConstantValues.Login.MESSAGE), "OK", Snackbar.LENGTH_INDEFINITE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // switchFragment(new MlmFragment());
                    }
                }));


            }
        });

       // binding.btnSignUp.setOnClickListener(view -> switchFragment(new RegisterFragment(), "RegisterFragment"));



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
        snackbar = Snackbar.make(this.findViewById(id), text, duration);
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
        snackbar = Snackbar.make(this.findViewById(id), text, duration);
        snackbar.show();
    }
}