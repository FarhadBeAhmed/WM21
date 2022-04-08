package co.wm21.https.fragments.member.mlm;

import static co.wm21.https.helpers.Constant.dp;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;

import co.wm21.https.R;
import co.wm21.https.adapters.item_menu.ItemMenuAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuView;
import co.wm21.https.api_request.Json;
import co.wm21.https.databinding.FragmentMlmBinding;
import co.wm21.https.fragments.HomeFragment;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;

public class MlmFragment extends Fragment {

    public FragmentMlmBinding binding;

    public static co.wm21.https.helpers.API API;
    public static co.wm21.https.helpers.User user;

    ArrayList<ItemMenuView> socialsList, digitalServiceList, digitalBusinessList, communityWorkList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mlm, container, false);

        API = Constant.getAPI();
        user = new User();


        Constant.setImageToImageView(binding.userImage, "user", "images", "paid.jpg");

        binding.userInfo.setText(user.getUsername());

        Json.addRequests(API.affiliate_balance(user.getUsername(), user.getPassword()).success(response -> {
            try {
                ((TextView) ((LinearLayout)binding.balanceInfo.getChildAt(0)).getChildAt(1)).setText(response.getString(Constant.Balance.Affiliate.CURRENT_WALLET_BALANCE));
            } catch (Exception e) { e.printStackTrace(); }
        }), API.affiliate_profile(user.getUsername(), user.getPassword()).success(response -> {
            try {
                binding.usernameInfo.setText(response.getString(Constant.Profile.Affiliate.NAME));
//                profileFab.setText(response.getString(Constant.Profile.Affiliate.NAME));
            } catch (Exception e) { e.printStackTrace(); }
        }));

        binding.balanceInfo.setOnClickListener(view -> Json.addRequests(API.affiliate_balance(user.getUsername(), user.getPassword()).success(response -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            MaterialCardView balance_dialog = (MaterialCardView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_affiliate_balance, viewGroup, false);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                MaterialCardView.LayoutParams layoutParams = new MaterialCardView.LayoutParams(dp(340), MaterialCardView.LayoutParams.MATCH_PARENT);
                balance_dialog.setLayoutParams(layoutParams);
                layoutParams = new MaterialCardView.LayoutParams(MaterialCardView.LayoutParams.MATCH_PARENT, MaterialCardView.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(dp(18), dp(85), dp(18), dp(20));
                balance_dialog.findViewById(R.id.affiliate_balance_dialog_scroll).setLayoutParams(layoutParams);
            }
            builder.setView(balance_dialog);

            TextView[] balances = new TextView[] {
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_1),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_2),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_3),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_4),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_5),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_6),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_7),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_8),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_9),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_10)};

            try {
                balances[0].setText(response.getString(Constant.Balance.Affiliate.MY_PROMOTIONAL_EARNING));
                balances[1].setText(response.getString(Constant.Balance.Affiliate.OWN_PRIMARY_EARNING));
                balances[2].setText(response.getString(Constant.Balance.Affiliate.OWN_ADVANCE_EARNING));
                balances[3].setText(response.getString(Constant.Balance.Affiliate.TEAM_PRIMARY_EARNING));
                balances[4].setText(response.getString(Constant.Balance.Affiliate.TEAM_ADVANCE_EARNING));
                balances[5].setText(response.getString(Constant.Balance.Affiliate.TEAM_EXTRA_EARNING));
                balances[6].setText(response.getString(Constant.Balance.Affiliate.TOTAL_AFFILIATE_EARNING));
                balances[7].setText(response.getString(Constant.Balance.Affiliate.TOTAL_MOBILE_RECHARGE));
                balances[8].setText(response.getString(Constant.Balance.Affiliate.TOTAL_WITHDRAWAL_BALANCE));
                balances[9].setText(response.getString(Constant.Balance.Affiliate.CURRENT_WALLET_BALANCE));
            } catch (Exception e) { e.printStackTrace(); }

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        })));

        socialsList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Social Timeline", R.drawable.ic_social_timelines, "#6979E0"),
                new ItemMenuView("Online News", R.drawable.ic_online_news, "#f6ac00"),
                new ItemMenuView("Live TV", R.drawable.ic_live_tv, "#e13a7b"),
                new ItemMenuView("Invite Friends", R.drawable.ic_invite_friends, "#b524eb"),
                new ItemMenuView("Business Live", R.drawable.ic_business_live, "#2e83df"),
                new ItemMenuView("Important Links", R.drawable.ic_important_link, "#a438cc")));

        digitalServiceList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Mobile Recharge", R.drawable.ic_mobile_recharge, "#a438cc"),
                new ItemMenuView("Utility Bill", R.drawable.ic_bill, "#2e83df"),
                new ItemMenuView("Bus Ticket", R.drawable.ic_bus_ticket, "#f6ac00"),
                new ItemMenuView("Air Ticket", R.drawable.ic_ticket_flight, "#b524eb"),
                new ItemMenuView("Hotel Booking", R.drawable.ic_hotel, "#6979E0"),
                new ItemMenuView("Bank Accounts", R.drawable.ic_bank, "#f6ac00"),
                new ItemMenuView("Hello Doctors", R.drawable.ic_doctor, "#a438cc"),
                new ItemMenuView("Expert Finder", R.drawable.ic_expert_finder, "#e13a7b")));

        digitalBusinessList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Company", R.drawable.img_company),
                new ItemMenuView("Franchise and e-shop", R.drawable.img_company),
                new ItemMenuView("Received"),
                new ItemMenuView("Genealogy"),
                new ItemMenuView("Training"),
                new ItemMenuView("Notification", R.drawable.ic_baseline_notifications_24),
                new ItemMenuView("Products")));

        communityWorkList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Blood Bank", R.drawable.ic_blood_bank, "#a438cc"),
                new ItemMenuView("Donation",R.drawable.ic_donation, "#f6ac00"),
                new ItemMenuView("Scholarship", R.drawable.ic_scholarship, "#b524eb"),
                new ItemMenuView("Idea Tracking", R.drawable.ic_idea_tracking,"#6979E0"),
                new ItemMenuView("Royal Fortune Club", R.drawable.ic_royal_fortune_club,"#2e83df"),
                new ItemMenuView("Volunteer Club", R.drawable.ic_volunteer_club, "#e13a7b")));

        binding.socialsRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), socialsList, R.layout.layout_item_socials));
        binding.digitalServiceRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), digitalServiceList, R.layout.layout_item_digital_service_category));
        binding.digitalBusinessRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), digitalBusinessList, R.layout.layout_item_digital_business_category));
        binding.communityWorkRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), communityWorkList, R.layout.layout_item_community_work));

        binding.updateAccount.setOnClickListener(view -> {
            switchFragment(new HomeFragment());
        });

        return binding.getRoot();
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}