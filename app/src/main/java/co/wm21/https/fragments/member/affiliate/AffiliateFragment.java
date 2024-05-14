package co.wm21.https.fragments.member.affiliate;

import static co.wm21.https.helpers.Constant.dp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import org.json.JSONException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.SliderAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuView;
import co.wm21.https.databinding.FragmentMemberAffiliateBinding;
import co.wm21.https.fragments.member.FlexiloadFragment;
import co.wm21.https.fragments.member.ProfileFragment;
import co.wm21.https.fragments.member.mlm.MlmFragment;
import co.wm21.https.fragments.member.verifications.VerifyAccountFragment;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;

public class AffiliateFragment extends Fragment {

    public FragmentMemberAffiliateBinding binding;

    List<SliderItem> sliderItemList;
    SliderAdapter adapter;

    public static co.wm21.https.helpers.API API;
    public static co.wm21.https.helpers.User user;
    View view;
    int progress = 0;
    String nextTask;
    final Handler handler = new Handler();
    final Handler handler2 = new Handler();
    Runnable runnable,runnable2;
    int taskNumber;
    private Double before2021_08_01, direct_Referral_Commission, daily_Matching_Commission, brand_Promoter_Incentive, brand_Ambassador_Royalty;
    private Double executive_Royalty, franchise_Commission, businessDevelop, promotionalIncentive, topEarnerIncentive;
    private Double shopReferCommission, ICTLabCommission, total_Income, withdrawal, expenses, totalExpense, netBalance;
    ArrayList<ItemMenuView> socialsList,offerList, digitalServiceList, digitalBusinessList, communityWorkList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_affiliate, container, false);


        user = new User();
        view = binding.getRoot();


        sliderItemList = new ArrayList<>();
        adapter = new SliderAdapter(getContext(),sliderItemList);
        sliderItemList.add(new SliderItem("20220921.png",""));
        sliderItemList.add(new SliderItem("20220928.png",""));
        adapter.renewItems(sliderItemList);
        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();


        binding.shimmerImageSlider.setVisibility(View.GONE);
        binding.imageSlider.setVisibility(View.VISIBLE);
        ((MainActivity) requireActivity()).signOutItem.setVisible(true);
        sliderItemList = new ArrayList<>();
        adapter = new SliderAdapter(getContext(),sliderItemList);
        sliderItemList.add(new SliderItem("20220921.png",""));
        sliderItemList.add(new SliderItem("20220928.png",""));
        adapter.renewItems(sliderItemList);
        binding.imageSlider2.setSliderAdapter(adapter);
        binding.imageSlider2.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider2.startAutoCycle();

        binding.shimmerImageSlider2.setVisibility(View.GONE);
        binding.imageSlider2.setVisibility(View.VISIBLE);


/*
        Json.addRequests(API.slide().before(obj -> {
            binding.shimmerImageSlider.setVisibility(View.VISIBLE);
            binding.imageSlider.setVisibility(View.GONE);
        }).success(response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.getJSONObject(i);
                    json.getString(Constant.Slide.INFO);
                    sliderItemList.add(i,
                            new SliderItem(
                                    json.getString(Constant.Slide.IMAGE),
                                    json.getString(Constant.Slide.INFO)
                            ));
                }
                Log.d("SLIDER IMAGE", "onCreateView: " + response + " - " + sliderItemList.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).after(returnObj -> {
            adapter.renewItems(sliderItemList);
            binding.imageSlider.setSliderAdapter(adapter);
            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            binding.imageSlider.startAutoCycle();

            binding.shimmerImageSlider.setVisibility(View.GONE);
            binding.imageSlider.setVisibility(View.VISIBLE);

        }));*/


        loadVerificationData();




        binding.userInfo.setText(user.getUsername());

        /*Json.addRequests(API.affiliate_balance(user.getUsername(), user.getPassword()).success(response -> {
            try {
                ((TextView) ((LinearLayout) binding.balanceInfo.getChildAt(0)).getChildAt(1)).setText(response.getString(Constant.Balance.Affiliate.CURRENT_WALLET_BALANCE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }), API.affiliate_profile(user.getUsername(), user.getPassword()).success(response -> {
            try {
                binding.usernameInfo.setText(response.getString(Constant.Profile.Affiliate.NAME));
//                profileFab.setText(response.getString(Constant.Profile.Affiliate.NAME));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));*/

        co.wm21.https.FHelper.API api= ConstantValues.getAPI();
        MySingleton.getInstance(getContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
            try {
                if (response.getString("error").equals("0")) {

                    binding.usernameInfo.setText(response.getString(ConstantValues.profile.NAME).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.NAME));
                    Constant.setImageToImageView(binding.userImage, "user","photo", response.getString(ConstantValues.profile.IMAGE));
                    // Constant.setImageToImageView(binding.userImage, "user","photo", "demo.jpg");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));




        /*binding.balanceInfo.setOnClickListener(view -> Queue.addRequests(API.affiliate_balance(user.getUsername(), user.getPassword()).success(response -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            MaterialCardView balance_dialog = (MaterialCardView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_mlm_balance, viewGroup, false);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                MaterialCardView.LayoutParams layoutParams = new MaterialCardView.LayoutParams(dp(340), MaterialCardView.LayoutParams.MATCH_PARENT);
                balance_dialog.setLayoutParams(layoutParams);
                layoutParams = new MaterialCardView.LayoutParams(MaterialCardView.LayoutParams.MATCH_PARENT, MaterialCardView.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(dp(18), dp(85), dp(18), dp(20));
                balance_dialog.findViewById(R.id.mlm_balance_dialog_scroll).setLayoutParams(layoutParams);
            }
            builder.setView(balance_dialog);

            TextView[] balances = new TextView[]{
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_1),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_2),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_3),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_4),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_5),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_6),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_7),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_8),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_9),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_10),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_11),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_12),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_13),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_14),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_15),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_16),
                    balance_dialog.findViewById(R.id.affiliate_balance_dialog_17)};

            try {
                balances[0].setText(response.getString(Constant.Balance.MLM.MY_PROMOTIONAL_EARNING));
                balances[1].setText(response.getString(Constant.Balance.MLM.OWN_PRIMARY_EARNING));
                balances[2].setText(response.getString(Constant.Balance.MLM.OWN_ADVANCE_EARNING));
                balances[3].setText(response.getString(Constant.Balance.MLM.TEAM_PRIMARY_EARNING));
                balances[4].setText(response.getString(Constant.Balance.MLM.TEAM_ADVANCE_EARNING));
                balances[5].setText(response.getString(Constant.Balance.MLM.TEAM_EXTRA_EARNING));
                balances[6].setText(response.getString(Constant.Balance.MLM.TOTAL_AFFILIATE_EARNING));
                balances[7].setText(response.getString(Constant.Balance.MLM.TOTAL_MOBILE_RECHARGE));
                balances[8].setText(response.getString(Constant.Balance.MLM.TOTAL_WITHDRAWAL_BALANCE));
                balances[9].setText(response.getString(Constant.Balance.MLM.CURRENT_WALLET_BALANCE));
            } catch (Exception e) {
                e.printStackTrace();
            }

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        })));
*/
        binding.goToProfile.setOnClickListener(view -> {

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager fm = getParentFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                fm.popBackStack();
            fm.beginTransaction().replace(R.id.fragmentContainer, profileFragment).addToBackStack("profileFragment").commit();
            switchFragment(new ProfileFragment(), "ProfileFragment");

        });
        socialsList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Social Timeline", R.drawable.ic_social_timelines, "#6979E0"),
                new ItemMenuView("Online News", R.drawable.ic_online_news, "#f6ac00"),
                new ItemMenuView("Live TV", R.drawable.ic_live_tv, "#e13a7b"),
                new ItemMenuView("Invite Friends", R.drawable.ic_invite_friends, "#b524eb"),
                new ItemMenuView("Business Live", R.drawable.ic_business_live, "#2e83df"),
                new ItemMenuView("Important Links", R.drawable.ic_important_link, "#a438cc")));
        binding.socialsRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), socialsList, R.layout.layout_item_socials));

        digitalServiceList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Mobile Recharge", R.drawable.ic_mobile_recharge, "#a438cc"),
                new ItemMenuView("Utility Bill", R.drawable.ic_bill, "#2e83df"),
                new ItemMenuView("Bus Ticket", R.drawable.ic_bus_ticket, "#f6ac00"),
                new ItemMenuView("Air Ticket", R.drawable.ic_ticket_flight, "#b524eb"),
                new ItemMenuView("Hotel Booking", R.drawable.ic_hotel, "#6979E0"),
                new ItemMenuView("Bank Accounts", R.drawable.ic_bank, "#f6ac00"),
                new ItemMenuView("Hello Doctors", R.drawable.ic_doctor, "#a438cc"),
                new ItemMenuView("Expert Finder", R.drawable.ic_expert_finder, "#e13a7b")));

        offerList=new ArrayList<>(Arrays.asList(
                new ItemMenuView("Dhamaka Offer",R.drawable.offer_1,"up To 50% off","up To 50% off"),
                new ItemMenuView("Darun Offer",R.drawable.offer_2,"up To 50% off","up To 50% off")
        ));
        binding.offersRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), offerList, R.layout.item_offers_layout));


        /*digitalBusinessList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Company", R.drawable.company_red),
                new ItemMenuView("Franchise and e-shop", R.drawable.eshop_red),
                new ItemMenuView("Received", R.drawable.received_red),
                new ItemMenuView("Genealogy", R.drawable.genealogy_red),
                new ItemMenuView("Training", R.drawable.training_red),
                new ItemMenuView("Notification", R.drawable.notification_red),
                new ItemMenuView("Products", R.drawable.product_red)));*/

        digitalBusinessList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Company", R.drawable.company),
                new ItemMenuView("Franchise and e-shop", R.drawable.franchise),
                new ItemMenuView("SMS & Call", R.drawable.sms_and_call),
                new ItemMenuView("Genealogy", R.drawable.generalogy),
                new ItemMenuView("Training", R.drawable.training),
                new ItemMenuView("Products Info", R.drawable.products),
                new ItemMenuView("Purchase", R.drawable.purchase),
                new ItemMenuView("Gallery", R.drawable.gallery),
                new ItemMenuView("Setting", R.drawable.settings),
                new ItemMenuView("Reward", R.drawable.reward),
                new ItemMenuView("Account Status", R.drawable.earning),
                new ItemMenuView("Transaction", R.drawable.transections),
                new ItemMenuView("Reports", R.drawable.report),
                new ItemMenuView("Services", R.drawable.services),
                new ItemMenuView("Apps Marketing", R.drawable.app_marketing)));

        binding.digitalServiceRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), digitalServiceList, R.layout.layout_item_digital_service_category).addOnClickListener((View, position) -> {
            Toast.makeText(getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    switchFragment(new FlexiloadFragment(), "FlexiloadFragment");

                    break;
                default:
            }
        }));

        communityWorkList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Blood Bank", R.drawable.ic_blood_bank, "#FE0000"),
                new ItemMenuView("Donation", R.drawable.ic_donation, "#f6ac00"),
                new ItemMenuView("Scholarship", R.drawable.ic_scholarship, "#b524eb"),
                new ItemMenuView("Idea Tracking", R.drawable.ic_idea_tracking, "#6979E0"),
                new ItemMenuView("Royal Fortune Club", R.drawable.ic_royal_fortune_club, "#2e83df"),
                new ItemMenuView("Volunteer Club", R.drawable.ic_volunteer_club, "#e13a7b")));



        binding.digitalBusinessRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), digitalBusinessList, R.layout.layout_item_digital_business_category));
        binding.communityWorkRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), communityWorkList, R.layout.layout_item_community_work));

        binding.tapForBalance.setOnClickListener(view -> {
            binding.animationView.setVisibility(View.VISIBLE);
            binding.tapForBalanceTxt.setVisibility(View.GONE);
            handler2.postDelayed(runnable2,500);


        });
        runnable= () -> {
            binding.tapForBalanceTxt.setText("Tap for balance");
        };
        runnable2= this::loadBalanceInfo2;
        binding.verificationStatus.nextTask.setOnClickListener(v -> {
            Fragment fragment = new VerifyAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("TabInvoke", taskNumber);
            fragment.setArguments(bundle);
            switchFragment(fragment, "VerifyAccountFragment");

        });

        binding.downgradeAccountLayout.setVisibility(View.GONE);
        binding.upgradeAccountLayout.setOnClickListener(view -> switchFragment(new MlmFragment(), "MlmFragment"));

        // new thread().start();
        return binding.getRoot();
    }
    public void loadVerificationData() {
        co.wm21.https.FHelper.API api = ConstantValues.getAPI();
        MySingleton.getInstance(getContext()).addToRequestQueue(api.verification(user.getUsername(), user.getPassword(), response -> {
            try {
                progress = (int) response.get(ConstantValues.Verification.PERCENT);
                nextTask = response.getString(ConstantValues.Verification.TASK);
                taskNumber = response.getInt(ConstantValues.Verification.TASK_NUMBER);

                binding.verificationStatus.getRoot().setVisibility(progress < 100 ? View.VISIBLE : View.GONE);
                ((LinearProgressIndicator) view.findViewById(R.id.verification_progress)).setProgress(progress);
                ((TextView) view.findViewById(R.id.percent)).setText(MessageFormat.format("{0}% Complete", progress));
                ((TextView) view.findViewById(R.id.next_task)).setText(MessageFormat.format("Next Task: {0}", nextTask));
                loadBalanceInfo();

            } catch (Exception e) {
                e.printStackTrace();
            } /*finally {

            }*/
        }));
    }

    private void loadBalanceInfo() {
        co.wm21.https.FHelper.API api = ConstantValues.getAPI();
        MySingleton.getInstance(getContext()).addToRequestQueue(api.allBalances(user.getUsername(), response -> {
            try {
                netBalance = Double.valueOf(response.getString(ConstantValues.balance.NetBalance));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

    }
    @SuppressLint("SetTextI18n")
    private void loadBalanceInfo2() {
        co.wm21.https.FHelper.API api = ConstantValues.getAPI();
        MySingleton.getInstance(getContext()).addToRequestQueue(api.allBalances(user.getUsername(), response -> {
            try {
                netBalance = Double.valueOf(response.getString(ConstantValues.balance.NetBalance));
                binding.tapForBalanceTxt.setText("Tk"+netBalance);
                binding.animationView.setVisibility(View.GONE);
                binding.tapForBalanceTxt.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable,3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

    }

    public class thread extends Thread {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            handler.post(() -> {
                binding.tapForBalance.setOnClickListener(view -> {
                    try {
                        binding.animationView.setVisibility(View.VISIBLE);
                        binding.tapForBalanceTxt.setVisibility(View.GONE);
                        MlmFragment.thread.sleep(3000);
                        binding.tapForBalanceTxt.setText("balllll");
                        binding.animationView.setVisibility(View.GONE);
                        binding.tapForBalanceTxt.setVisibility(View.VISIBLE);
                        MlmFragment.thread.sleep(3000);
                        binding.tapForBalanceTxt.setText("Tap for balance");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
    }

    public void switchFragment(Fragment fragment,String tag) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment,tag).addToBackStack(tag).commit();
    }
}