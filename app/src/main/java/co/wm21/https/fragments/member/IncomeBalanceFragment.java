package co.wm21.https.fragments.member;


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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.adapters.AccountIncomeAdapter;
import co.wm21.https.databinding.FragmentIncomeBalanceBinding;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.model.IncomeBalaceReportDataListModel;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("NonConstantResourceId")
public class IncomeBalanceFragment extends Fragment {

    CheckInternetConnection checkInternetConnection;
    SessionHandler appSessionManager;
    FragmentIncomeBalanceBinding binding;
    API fApi;
    User user;


    private List<IncomeBalaceReportDataListModel> iList = new ArrayList<>();
    private AccountIncomeAdapter accountAdapter;
    private List<IncomeBalaceReportDataListModel> incomeBalanceReport = new ArrayList<>();
    private AccountIncomeAdapter incomeBalaceReportAdapter;

    private List<IncomeBalaceReportDataListModel> expenseBalanceReportList = new ArrayList<>();
    private AccountIncomeAdapter expenseBalaceReportAdapter;


    public IncomeBalanceFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncomeBalanceBinding.inflate(getLayoutInflater());
        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();

        fApi = ConstantValues.getAPI();
        user = new User(getContext());

        binding.incomeTitle.setText("E-WALLET BALANCE STATUS");
      /*  Glide.with(getActivity()).load(ConstantValues.URL
                + appSessionManager.getUserDetails().get(appSessionManager.KEY_COMPANYLOGO)).apply(new RequestOptions().error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher).fitCenter()).into(imageViewCompanyLogo);
        textViewCompanyName.setText(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERFULLNAME));
        */
        binding.txtIncomeStatusDate.setText("FOR THE MONTH OF " + currentDate());
        loadIncomeBalanceData();
        loadExpenseBalanceData();
        loadAccountBalanceData();
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
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources().getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            MySingleton.getInstance(getContext()).addToRequestQueue(fApi.getIncomeBalanceReportStatus(user.getUsername(), response -> {
                try {
                    if (response.getString(ConstantValues.SUCCESS).equals("1")) {
                        dialog.dismiss();
                        JSONArray array = response.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            incomeBalanceReport.add(new IncomeBalaceReportDataListModel(
                                    object.getString(ConstantValues.account_status.SERIAL),
                                    object.getString(ConstantValues.account_status.BOLD),
                                    object.getString(ConstantValues.account_status.TITLE),
                                    object.getString(ConstantValues.account_status.MONEY)
                            ));
                        }

                        incomeBalaceReportAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }));

          /*  APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getIncomeBalanceReportStatus(appSessionManager.getUserDetails().getUsername())
                    .enqueue(new Callback<AccountIncomeDataModel>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<AccountIncomeDataModel> call, Response<AccountIncomeDataModel> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getError() == 0) {
                                    incomeBalanceReport.clear();
                                    incomeBalanceReport.addAll(response.body().getIncomes());
                                    incomeBalaceReportAdapter.notifyDataSetChanged();
                                } else {
                                    Log.e("AccountBalance", "Balance empty!");
                                }
                            } else {
                                Log.e("AccountBalance", "Balance not found!");
                            }

                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<AccountIncomeDataModel> call, Throwable t) {
                            dialog.dismiss();
                            Log.d("INCOME_BAL", "onFailure: " + t.getMessage());
                            Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });*/

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

    @SuppressLint("NotifyDataSetChanged")
    private void loadExpenseBalanceData() {
        expenseBalaceReportAdapter = new AccountIncomeAdapter(expenseBalanceReportList, getActivity(), 0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvExpenseBlance.setLayoutManager(layoutManager);
        binding.rvExpenseBlance.setItemAnimator(new DefaultItemAnimator());
        binding.rvExpenseBlance.setAdapter(expenseBalaceReportAdapter);

        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources().getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();

            MySingleton.getInstance(getContext()).addToRequestQueue(fApi.getExpenseBalanceReportStatus(user.getUsername(), response -> {
                try {
                    if (response.getString(ConstantValues.SUCCESS).equals("1")) {
                        dialog.dismiss();
                        JSONArray array = response.getJSONArray("expenses");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            expenseBalanceReportList.add(new IncomeBalaceReportDataListModel(
                                    object.getString(ConstantValues.account_status.SERIAL),
                                    object.getString(ConstantValues.account_status.BOLD),
                                    object.getString(ConstantValues.account_status.TITLE),
                                    object.getString(ConstantValues.account_status.MONEY)
                            ));
                        }

                        expenseBalaceReportAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }));


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


    @SuppressLint("NotifyDataSetChanged")
    private void loadAccountBalanceData() {
        accountAdapter = new AccountIncomeAdapter(iList, getActivity(), 0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvAccountBlance.setLayoutManager(layoutManager);
        binding.rvAccountBlance.setItemAnimator(new DefaultItemAnimator());
        binding.rvAccountBlance.setAdapter(accountAdapter);

        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                    .content(getResources().getString(R.string.pleaseWait))
                    .progress(true, 0)
                    .cancelable(false)
                    .show();

            MySingleton.getInstance(getContext()).addToRequestQueue(fApi.getIncomeStatus(user.getUsername(), response -> {
                try {
                    if (response.getString(ConstantValues.SUCCESS).equals("1")) {
                        dialog.dismiss();
                        JSONArray array = response.getJSONArray("balance");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            iList.add(new IncomeBalaceReportDataListModel(
                                    object.getString(ConstantValues.account_status.SERIAL),
                                    object.getString(ConstantValues.account_status.BOLD),
                                    object.getString(ConstantValues.account_status.TITLE),
                                    object.getString(ConstantValues.account_status.MONEY)
                            ));
                        }

                        accountAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }));
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadAccountBalanceData();
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

}
