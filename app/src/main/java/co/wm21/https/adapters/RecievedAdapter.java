package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.ArrayList;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.ReceivedItemsModel;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.databinding.ItemReceivePageSingleRowBinding;
import co.wm21.https.fragments.manageOrder.ReceivedFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.model.ReceivedProModel;

public class RecievedAdapter extends RecyclerView.Adapter<RecievedAdapter.viewHolder> {

    @LayoutRes
    int layout;
    Context context;
    ArrayList<ReceivedItemsModel> cartList;
    LinearLayout fragmentLayout;
    User user;
    ItemReceivePageSingleRowBinding binding;
    String serial = "";
    int memPos = 0;
    API api;

    public RecievedAdapter(int layout, Context context, ArrayList<ReceivedItemsModel> cartList, LinearLayout fragmentLayout) {
        this.layout = layout;
        this.context = context;
        this.cartList = cartList;
        this.fragmentLayout = fragmentLayout;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new viewHolder(binding);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        user = new User(context);
        api = ConstantValues.getAPI();
        ReceivedItemsModel receivedProModel = cartList.get(position);
        holder.rp_pont.setText("RP: " + receivedProModel.getPoint());
        holder.date.setText(receivedProModel.getDate());

        holder.pinLayout.setVisibility(View.GONE);
        holder.plLayout.setVisibility(View.GONE);
        holder.leftRightGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == binding.leftRadio.getId()) {
                memPos = 1;
            } else if (checkedId == binding.rightRadio.getId()) {
                memPos = 2;
            }
        });
        if (Integer.parseInt(receivedProModel.getUsed()) == 0) {
            if (Integer.parseInt(receivedProModel.getCount()) == 0) {
                holder.buttonText.setText("Activation");
                if (Double.parseDouble(receivedProModel.getPoint()) >= 25) {
                    holder.eCom_signBtnCard.setVisibility(View.VISIBLE);
                    holder.activationBtnCard.setVisibility(View.GONE);
                    holder.plLayout.setVisibility(View.VISIBLE);

                } else {
                    holder.plLayout.setVisibility(View.GONE);
                    holder.activationBtnCard.setVisibility(View.VISIBLE);
                    holder.eCom_signBtnCard.setVisibility(View.GONE);

                }
                holder.pinLayout.setVisibility(View.VISIBLE);
                holder.activationBtn.setVisibility(View.VISIBLE);
                serial = receivedProModel.getSerial();

            } else {
                holder.pinLayout.setVisibility(View.VISIBLE);
                holder.buttonText.setText("Migration");

            }
            holder.pinCode.getEditText().setText(receivedProModel.getPin());
        } else {
            holder.buttonText.setText("USED");
            holder.buttonText.setTextColor(Color.BLACK);
            holder.activationBtn.setBackgroundColor(Color.parseColor("#D0EDDA"));
        }
        holder.eCom_sign_Btn.setOnClickListener(view -> {
            if (ConstantValues.validation(holder.placementId)) {
                String placementID = holder.placementId.getEditText().getText().toString();
                eCom_sign(memPos, receivedProModel.getPin(), receivedProModel.getSerial(), placementID);
            }

        });

        holder.activationBtn.setOnClickListener(view -> {
            String btnTxt = holder.buttonText.getText().toString();
            if (btnTxt.equals("Activation")) {
                receiveAction("active", receivedProModel.getSerial(), receivedProModel.getPin());
            } else if (btnTxt.equals("Migration")) {
                receiveAction("update", receivedProModel.getSerial(), receivedProModel.getPin());

            }
        });

    }

    private void receiveAction(String action, String serial, String pin) {
        MySingleton.getInstance(context).addToRequestQueue(api.receive_action(user.getUsername(), user.getPassword(), pin, serial, action, response -> {
            try {
                if (response.getString(ConstantValues.ERROR).equals("0")){

                }
                showSnackBar(response.getString(ConstantValues.MSG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));
    }

    private void eCom_sign(int memPos, String pin, String serial, String placementID) {
        MySingleton.getInstance(context).addToRequestQueue(api.eCom_sign(user.getUsername(), user.getPassword(), pin, serial, memPos, placementID, response -> {
            try {
                if (response.getString(ConstantValues.ERROR).equals("0")){
                    switchFragment(new ReceivedFragment(),"ReceivedFragment");
                }
                showSnackBar(response.getString(ConstantValues.MSG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));
    }
    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView rp_pont, date, buttonText;
        TextInputLayout placementId, pinCode;
        RadioButton rLeft, rRight;
        LinearLayout activationBtn, eCom_sign_Btn, plLayout, pinLayout;
        RadioGroup leftRightGroup;
        CardView eCom_signBtnCard, activationBtnCard;

        public viewHolder(@NonNull ItemReceivePageSingleRowBinding itemView) {
            super(itemView.getRoot());
            rp_pont = itemView.rpPoint;
            date = itemView.date;
            rLeft = itemView.leftRadio;
            rRight = itemView.rightRadio;
            leftRightGroup = itemView.rGroup;
            placementId = itemView.placement;
            pinCode = itemView.pinCode;
            activationBtn = itemView.activationBtn;
            plLayout = itemView.plLayout;
            pinLayout = itemView.pinLayout;
            buttonText = itemView.buttonText;
            activationBtnCard = itemView.activationBtnCard;
            eCom_signBtnCard = itemView.eComSignBtnCard;
            eCom_sign_Btn = itemView.eComSignBtn;


        }
    }

    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(fragmentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
