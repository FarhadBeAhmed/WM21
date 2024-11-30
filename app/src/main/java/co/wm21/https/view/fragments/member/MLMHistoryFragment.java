package co.wm21.https.view.fragments.member;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceData;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;
import co.wm21.https.R;
import co.wm21.https.view.adapters.AccountIncomeAdapter;
import co.wm21.https.view.adapters.item_menu.EarningMenuAdapter;
import co.wm21.https.view.adapters.item_menu.ItemMenuView;
import co.wm21.https.databinding.FragmentMLMHistoryBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnBalancesView;
import co.wm21.https.model.IncomeBalaceReportDataListModel;
import co.wm21.https.presenter.BalancesPresenter;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("NonConstantResourceId")
public class MLMHistoryFragment extends Fragment implements OnBalancesView {

    CheckInternetConnection checkInternetConnection;
    FragmentMLMHistoryBinding binding;
    User user;
    private List<IncomeBalaceReportDataListModel> incomeBalanceReport = new ArrayList<>();
    private AccountIncomeAdapter incomeBalaceReportAdapter;
    ArrayList<ItemMenuView> earningTypeList;
    BalancesPresenter balancesPresenter;
    LoadingDialog loadingDialog;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMLMHistoryBinding.inflate(getLayoutInflater());
        checkInternetConnection = new CheckInternetConnection();
        user = new User(getContext());
        balancesPresenter=new BalancesPresenter(this);
        loadingDialog = new LoadingDialog(getActivity());
        binding.incomeTitle.setText("E-WALLET BALANCE STATUS");
        binding.txtIncomeStatusDate.setText("FOR THE MONTH OF " + currentDate());

        earningTypeList=new ArrayList<>(
                Arrays.asList(
                        new ItemMenuView("Social Timeline", R.drawable.ic_social_timelines, "#6979E0"),
                        new ItemMenuView("Online News", R.drawable.ic_online_news, "#f6ac00"),
                        new ItemMenuView("Live TV", R.drawable.ic_live_tv, "#e13a7b"),
                        new ItemMenuView("Invite Friends", R.drawable.ic_invite_friends, "#b524eb"),
                        new ItemMenuView("Business Live", R.drawable.ic_business_live, "#2e83df"),
                        new ItemMenuView("Important Links", R.drawable.ic_important_link, "#a438cc"),
                        new ItemMenuView("Important Links", R.drawable.ic_important_link, "#a438cc"),
                        new ItemMenuView("Important Links", R.drawable.ic_important_link, "#a438cc")));
        binding.rvIncomeBlance.setAdapter(new EarningMenuAdapter(getContext(), earningTypeList, R.layout.layout_item_socials)
        );
        loadIncomeBalanceData();


        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadIncomeBalanceData() {
        incomeBalaceReportAdapter = new AccountIncomeAdapter(incomeBalanceReport, getActivity(), 0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvIncomeBlance.setLayoutManager(layoutManager);
        binding.rvIncomeBlance.setItemAnimator(new DefaultItemAnimator());
        binding.rvIncomeBlance.setAdapter(incomeBalaceReportAdapter);

        if (checkInternetConnection.isInternetAvailable(getActivity())) {

            balancesPresenter.BalancesDataLoad(user.getUsername());

        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadIncomeBalanceData();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }




    private String currentDate() {
        String formattedDate = "";
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
        return formattedDate = df.format(c);
    }

    @Override
    public void onBalancesDataLoad(BalanceModel balanceModel) {
        if (balanceModel!=null){
            BalanceData data=balanceModel.getBalanceData();
            binding.BrandAmbassadorRoyaltyBal.setText(data.getBar());
            binding.BrandPromoterIncentiveBal.setText(data.getBpi());
            binding.BusinessDevelopBal.setText(data.getBdp());
            binding.DailyMatchingCommissionBal.setText(data.getMatch());
            binding.derRefComBalBtnBal.setText(data.getDirect());
            binding.ExecutiveRoyaltyBal.setText(data.getExr());
            binding.FlexiloadBal.setText(data.getFlexi());
            binding.netBalId.setText(data.getNetBalance());
            binding.pastBal.setText(data.getPast());
            binding.ShopReferCommissionBal.setText(data.getShopReferCommission());
            binding.PromotionalIncentiveBal.setText(data.getInc());
            binding.TopEarnerIncentiveBal.setText(data.getTopEarnerIncentive());
            binding.TotalExpensetBal.setText(data.getTotalExpense());
            binding.TotalIncomeBal.setText(data.getTotalIncome());
            binding.WithdrawalBal.setText(data.getWithdraw());
            binding.CenterTransferBal.setText(data.getCenTrx());
        }
    }

    @Override
    public void onBalancesStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onBalancesStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onBalancesShowMessage(String errMsg) {

    }
}
