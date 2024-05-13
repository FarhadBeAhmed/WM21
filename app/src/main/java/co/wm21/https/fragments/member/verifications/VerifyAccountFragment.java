package co.wm21.https.fragments.member.varifications;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import co.wm21.https.R;
import co.wm21.https.databinding.FragmentAccountVerifyBinding;
import co.wm21.https.fragments.member.ProfileFragment;


public class VerifyAccountFragment extends Fragment {

    FragmentAccountVerifyBinding binding;

    int taskNum = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_verify, container, false);

        try {
            assert this.getArguments() != null;
            taskNum = this.getArguments().getInt("TabInvoke");
        } catch (Exception e) {

        }
        if (taskNum != 0) {
            switch (taskNum) {
                case 1:
                    switchFragment(new ProfileFragment());
                    break;
                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    break;

                default:
            }
        }

        return binding.getRoot();
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

    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.mem_contain, fragment).addToBackStack(null).commit();
    }

}