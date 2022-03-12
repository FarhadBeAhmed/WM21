package co.wm21.https.fragments.member;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import co.wm21.https.R;


public class FlexiloadFragment extends Fragment {

    private static final int READ_CONTACTS_PERMISSION = 435;
    private static final int CONTACTS_PICK_CODE = 1;

    private TextInputLayout MobileNoParent, RechargeAmount, PinCode, SimCardParent, NMPParent;
    private AutoCompleteTextView SimCard, NMP;
    private AppCompatEditText MobileNo;
    private TableLayout table;
    private TableRow tableHeader;

    private String type, nmp;
    private View view;

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_flexiload, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        MobileNoParent = view.findViewById(R.id.flexiload_til_mobile_no);
        MobileNo = view.findViewById(R.id.flexiload_mobile_no);
        RechargeAmount = view.findViewById(R.id.flexiload_til_recharge_amount);
        PinCode = view.findViewById(R.id.flexiload_til_pin_code);
        SimCard = view.findViewById(R.id.flexiload_til_sim_card);
        SimCardParent = view.findViewById(R.id.flexiload_til_sim_card_parent);
        NMP = view.findViewById(R.id.flexiload_til_nmp);
        NMPParent = view.findViewById(R.id.flexiload_til_nmp_parent);
//        table = view.findViewById(R.id.flexiload_report);
//        tableHeader = view.findViewById(R.id.table_header);

        SimCard.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[] {
                "Prepaid", "Postpaid", "Skitto" }));
        SimCard.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                type = charSequence.toString();
            }
            @Override public void afterTextChanged(Editable editable) { }
        });

        NMP.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[] {
                "None", "Grameenphone", "Banglalink", "Robi", "Airtel", "Teletalk" }));
        NMP.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nmp = charSequence.toString();
            }
            @Override public void afterTextChanged(Editable editable) { }
        });

//        MobileNo.setOnTouchListener((v, event) -> {
//            final int DRAWABLE_RIGHT = 2;
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                if (event.getRawX() >= (MobileNo.getRight() - MobileNo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                    if (!checkContactPermission()) {
//                        requestContactPermission();
//                    } else {
//                        pickContact();
//                    }
//                    return true;
//                }
//            }
//            return false;
//        });
//
//        view.findViewById(R.id.recharge_submit).setOnClickListener(v -> {
//            if (validation(SimCardParent, NMPParent, MobileNoParent, RechargeAmount, PinCode)) {
//                MySingleton.getInstance(getContext()).addToRequestQueue(API.flexiload(user.getId(), user.getPassword(), getTextFromTextInputLayout(SimCardParent), getTextFromTextInputLayout(MobileNoParent), getTextFromTextInputLayout(RechargeAmount), getTextFromTextInputLayout(PinCode), response -> {
//                    try {
//                        openSnackbar(view, R.id.fragment_accounts_flexiload, response.getString("msg"), Snackbar.LENGTH_LONG);
//                        loadFlexiloadReport();
//                        HomeActivity.updateBalance(getContext());
//                        HomeActivity.updateVerificationStatus(getContext());
//                    } catch (Exception e) { e.printStackTrace(); }
//                }));
//            }
//        });
//
//        loadFlexiloadReport();
//        loadFlexiloadReport();

    }
}