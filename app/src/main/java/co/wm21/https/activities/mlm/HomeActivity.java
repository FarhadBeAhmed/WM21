package co.wm21.https.activities.mlm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.FHelper.networks.Models.VerificationModel;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.activities.MobileRechargeActivity;
import co.wm21.https.adapters.SliderAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuView;
import co.wm21.https.databinding.ActivityHomeMlmBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnBalancesView;
import co.wm21.https.interfaces.OnProfileDetailsView;
import co.wm21.https.interfaces.OnVerificationView;
import co.wm21.https.presenter.BalancesPresenter;
import co.wm21.https.presenter.ProfileDetailsPresenter;
import co.wm21.https.presenter.VerificationPresenter;

public class HomeActivity extends AppCompatActivity implements OnProfileDetailsView, OnVerificationView, OnBalancesView {
    ActivityHomeMlmBinding binding;
    List<SliderItem> sliderItemList;
    SliderAdapter adapter;

    public static User user;
    View view;
    int progress = 0;
    String nextTask;
    final Handler handler = new Handler();
    final Handler handler2 = new Handler();
    Runnable runnable, runnable2;
    int taskNumber;
    int balanceTaskNumber=0;
    private Double before2021_08_01, direct_Referral_Commission, daily_Matching_Commission, brand_Promoter_Incentive, brand_Ambassador_Royalty;
    private Double executive_Royalty, franchise_Commission, businessDevelop, promotionalIncentive, topEarnerIncentive;
    private Double shopReferCommission, ICTLabCommission, total_Income, withdrawal, expenses, totalExpense;
    private String netBalance;
    ArrayList<ItemMenuView> socialsList, offerList, digitalServiceList, digitalBusinessList, communityWorkList;

    ProfileDetailsPresenter profileDetailsPresenter;
    VerificationPresenter verificationPresenter;
    BalancesPresenter balancesPresenter;
    LoadingDialog loadingDialog;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeMlmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new User();
        view = binding.getRoot();

        sliderItemList = new ArrayList<>();
        adapter = new SliderAdapter(this,sliderItemList );
        sliderItemList.add(new SliderItem("20220921.png", ""));
        sliderItemList.add(new SliderItem("20220928.png", ""));
        adapter.renewItems(sliderItemList);
        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
        binding.backButtonId.setOnClickListener(view -> {
            this.onBackPressed();
        });
        //MainActivity.signOutItem.setVisible(true);
        binding.shimmerImageSlider.setVisibility(View.GONE);
        binding.imageSlider.setVisibility(View.VISIBLE);

        sliderItemList = new ArrayList<>();
        adapter = new SliderAdapter(this,sliderItemList);
        sliderItemList.add(new SliderItem("20220921.png", ""));
        sliderItemList.add(new SliderItem("20220928.png", ""));
        adapter.renewItems(sliderItemList);
        binding.imageSlider2.setSliderAdapter(adapter);
        binding.imageSlider2.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider2.startAutoCycle();

        binding.shimmerImageSlider2.setVisibility(View.GONE);
        binding.imageSlider2.setVisibility(View.VISIBLE);

        profileDetailsPresenter=new ProfileDetailsPresenter(this);
        verificationPresenter=new VerificationPresenter(this);
        balancesPresenter= new BalancesPresenter(this);


        loadingDialog=new LoadingDialog(this);
        binding.userInfo.setText(user.getUsername());

        binding.goToProfile.setOnClickListener(view -> {

            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

        });
        socialsList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Social Timeline", R.drawable.ic_social_timelines, "#6979E0"),
                new ItemMenuView("Online News", R.drawable.ic_online_news, "#f6ac00"),
                new ItemMenuView("Live TV", R.drawable.ic_live_tv, "#e13a7b"),
                new ItemMenuView("Invite Friends", R.drawable.ic_invite_friends, "#b524eb"),
                new ItemMenuView("Business Live", R.drawable.ic_business_live, "#2e83df"),
                new ItemMenuView("Important Links", R.drawable.ic_important_link, "#a438cc")));
        binding.socialsRecyclerView.setAdapter(new ItemMenuAdapter(this, socialsList, R.layout.layout_item_socials));

        digitalServiceList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Mobile Recharge", R.drawable.ic_mobile_recharge, "#a438cc"),
                new ItemMenuView("Utility Bill", R.drawable.ic_bill, "#2e83df"),
                new ItemMenuView("Bus Ticket", R.drawable.ic_bus_ticket, "#f6ac00"),
                new ItemMenuView("Air Ticket", R.drawable.ic_ticket_flight, "#b524eb"),
                new ItemMenuView("Hotel Booking", R.drawable.ic_hotel, "#6979E0"),
                new ItemMenuView("Bank Accounts", R.drawable.ic_bank, "#f6ac00"),
                new ItemMenuView("Hello Doctors", R.drawable.ic_doctor, "#a438cc"),
                new ItemMenuView("Expert Finder", R.drawable.ic_expert_finder, "#e13a7b")
        ));

        binding.digitalServiceRecyclerView.setAdapter(new ItemMenuAdapter(this, digitalServiceList, R.layout.layout_item_digital_service_category).addOnClickListener((View, position) -> {
            if (position==0){
                startActivity(new Intent(this, MobileRechargeActivity.class));
            }
        }));

        offerList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Dhamaka Offer", R.drawable.offer_1, "up To 50% off", "up To 50% off"),
                new ItemMenuView("Darun Offer", R.drawable.offer_2, "up To 50% off", "up To 50% off")
        ));
        binding.offersRecyclerView.setAdapter(new ItemMenuAdapter(this, offerList, R.layout.item_offers_layout));



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



        communityWorkList = new ArrayList<>(Arrays.asList(
                new ItemMenuView("Blood Bank", R.drawable.ic_blood_bank, "#FE0000"),
                new ItemMenuView("Donation", R.drawable.ic_donation, "#f6ac00"),
                new ItemMenuView("Scholarship", R.drawable.ic_scholarship, "#b524eb"),
                new ItemMenuView("Idea Tracking", R.drawable.ic_idea_tracking, "#6979E0"),
                new ItemMenuView("Royal Fortune Club", R.drawable.ic_royal_fortune_club, "#2e83df"),
                new ItemMenuView("Volunteer Club", R.drawable.ic_volunteer_club, "#e13a7b")));


        binding.digitalBusinessRecyclerView.setAdapter(new ItemMenuAdapter(this, digitalBusinessList, R.layout.layout_item_digital_business_category));
        binding.communityWorkRecyclerView.setAdapter(new ItemMenuAdapter(this, communityWorkList, R.layout.layout_item_community_work));

        binding.tapForBalance.setOnClickListener(view -> {
            binding.animationView.setVisibility(View.VISIBLE);
            binding.tapForBalanceTxt.setVisibility(View.GONE);
            handler2.postDelayed(runnable2, 500);


        });
        runnable = () -> {
            binding.tapForBalanceTxt.setText("Tap for balance");
        };
        runnable2 = this::loadBalanceInfo2;
        binding.verificationStatus.nextTask.setOnClickListener(v -> {
           /* Fragment fragment = new VerifyAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("TabInvoke", taskNumber);
            fragment.setArguments(bundle);
            switchFragment(fragment, "VerifyAccountFragment");*/
            Intent intent = new Intent(this, VerifyAccountActivity.class);
            intent.putExtra("TabInvoke", taskNumber);
            startActivity(intent);


        });

        binding.upgradeAccountLayout.setVisibility(View.GONE);
      //  binding.downgradeAccountLayout.setOnClickListener(view -> switchFragment(new AffiliateFragment(), "AffiliateFragment"));


        profileDetailsPresenter.profileDetailsDataLoad(user.getUsername());

    }

    public void loadVerificationData() {
        verificationPresenter.verificationDataLoad(user.getUsername());
    }

    private void loadBalanceInfo() {
        balanceTaskNumber=1;
        balancesPresenter.BalancesDataLoad(user.getUsername());


    }

    @SuppressLint("SetTextI18n")
    private void loadBalanceInfo2() {
        balanceTaskNumber=2;
        balancesPresenter.BalancesDataLoad(user.getUsername());
     /*   co.wm21.https.FHelper.API api = ConstantValues.getAPI();
        MySingleton.getInstance(this).addToRequestQueue(api.allBalances(user.getUsername(), response -> {
            try {
                netBalance = response.getString(ConstantValues.balance.NetBalance);
                binding.tapForBalanceTxt.setText("Tk" + netBalance);
                binding.animationView.setVisibility(View.GONE);
                binding.tapForBalanceTxt.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable, 3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));*/

    }

    @Override
    public void onProfileDetailsDataLoad(ProfileDetailsHead profileDetailsHead) {
        binding.usernameInfo.setText(profileDetailsHead.getData().getName().equals("") ? "Unavailable" : profileDetailsHead.getData().getName());
       // Constant.setImageToImageView(binding.userImage, "user", "photo", profileDetailsHead.getData().getImg());
        Picasso.get().load(ConstantValues.URL+"user/photo/"+profileDetailsHead.getData().getImage()).into(binding.userImage);
        loadVerificationData();
    }

    @Override
    public void onProfileDetailsStartLoading() {
        loadingDialog.startLoadingAlertDialog();

    }

    @Override
    public void onProfileDetailsStopLoading() {
        loadingDialog.dismissDialog();

    }

    @Override
    public void onProfileDetailsShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationDataLoad(VerificationModel verificationModel) {
        progress = (int) verificationModel.getPercent();
        if (progress < 100) {
            nextTask =verificationModel.getTask();
            taskNumber = verificationModel.getTaskNo();

        }
        ((LinearProgressIndicator) view.findViewById(R.id.verification_progress)).setProgress(progress);
        ((TextView) view.findViewById(R.id.percent)).setText(MessageFormat.format("{0}% Complete", progress));
        ((TextView) view.findViewById(R.id.next_task)).setText(MessageFormat.format("Next Task: {0}", nextTask));
        loadBalanceInfo();

        binding.verificationStatus.varif.setVisibility(progress < 100 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onVerificationStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onVerificationStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onVerificationShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBalancesDataLoad(BalanceModel balanceModel) {
        //netBalance = response.getString(ConstantValues.balance.NetBalance);
        if (balanceTaskNumber == 1) {
            netBalance = balanceModel.getBalanceData().getNetBalance();
        } else if (balanceTaskNumber==2) {
            netBalance =  balanceModel.getBalanceData().getNetBalance();
            binding.tapForBalanceTxt.setText("Tk" + netBalance);
            binding.animationView.setVisibility(View.GONE);
            binding.tapForBalanceTxt.setVisibility(View.VISIBLE);
            handler.postDelayed(runnable, 3000);
        }

    }

    @Override
    public void onBalancesStartLoading() {

    }

    @Override
    public void onBalancesStopLoading() {

    }

    @Override
    public void onBalancesShowMessage(String errMsg) {

    }


   /* public class thread extends Thread {
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
    }*/
}