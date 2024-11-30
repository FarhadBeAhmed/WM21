package co.wm21.https.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.RechargeHistoryData;
import co.wm21.https.R;
import co.wm21.https.view.adapters.RechargeHistoryAdapter;
import co.wm21.https.databinding.ActivityMobileRechargeBinding;

public class MobileRechargeActivity extends AppCompatActivity {
    ActivityMobileRechargeBinding binding;

    String number;
    double operatorCode = 0, balance = 0, newBalance = 0, charge = 0;
    String NumberType = "", operator = "",user_id,nmp="";
    int numberValidation = 0;
    RechargeHistoryAdapter adapter;
    String bal;

    ArrayList<RechargeHistoryData> historyResponseList = new ArrayList<>();

   // KProgressHUD kProgressHUD;
    String packAmount="",packDescription="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMobileRechargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if( intent.getStringExtra("conVal")!=null){
            number =  intent.getStringExtra("conVal");
            binding.numberId.setText(number);
        }
        //bal=CommonTask.getPreferences(MobileRechargeActivity.this, ConstantValues.Flexiload.BALANCE);
        //balance=Double.parseDouble(bal);

   /*     binding.flexiloadToolbarId.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        binding.flexiloadToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileRechargeActivity.super.onBackPressed();
            }
        });*/


        if (intent.getStringExtra(ConstantValues.Flexiload.AMOUNT)!=null){
            binding.packDescription.setVisibility(View.VISIBLE);
            packAmount=intent.getStringExtra(ConstantValues.Flexiload.AMOUNT);
            packDescription=intent.getStringExtra(ConstantValues.Flexiload.PACK);
            binding.operatorEditLogoId.setVisibility(View.GONE);
            binding.AmountId.setClickable(false);
            binding.AmountId.setFocusable(false);
            binding.AmountId.setText(packAmount);
            binding.packDescription.setText(packDescription);

        }else {
            binding.packDescription.setVisibility(View.GONE);
        }

        binding.radioPrepaidBtn.setChecked(true);
        NumberType = binding.radioPrepaidBtn.getText().toString();


        binding.mLogosId.setOnClickListener(this::onClick);
        binding.flexiSubmitId.setOnClickListener(this::onClick);
        binding.contactBtnLogoId.setOnClickListener(this::onClick);
        NumberType = binding.radioPrepaidBtn.getText().toString();


        initial();
        rechargeHistory();




    }

    @SuppressLint("SetTextI18n")
    private void initial() {
      /*  homeInfoViewModel.getHomeInfo().observe(this, homeInfoResponse -> {
            if (homeInfoResponse!=null){
                binding.idNetBalance.setText(homeInfoResponse.getBalance() + "৳");
            }
        });*/


        binding.numberId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String num = binding.numberId.getText().toString();
                char c;
                if (num.length() != 0) {
                    if (num.length() == 3) {
                        if (charSequence.charAt(0) == '0' && charSequence.charAt(1) == '1') {
                            c = charSequence.charAt(2);
                            if (c == '8') {
                                numberValidation = 1;
                                binding.operatorLogoId.setBackgroundResource(R.drawable.robi_logo2);
                                binding.operatorEditLogoId.setBackgroundResource(R.drawable.choperator);

                                operatorCode = 18;
                                operator = "robi";
                                nmp="RB";
                                binding.radioSkittoBtn.setVisibility(View.GONE);
                            } else if (c == '4' || c == '9') {
                                numberValidation = 1;
                                binding.operatorLogoId.setBackgroundResource(R.drawable.banglalink_logo2);
                                binding.operatorEditLogoId.setBackgroundResource(R.drawable.choperator);
                                binding.radioSkittoBtn.setVisibility(View.GONE);
                                nmp="BL";
                                if (c == '4') {
                                    operatorCode = 14;
                                } else {
                                    operatorCode = 19;
                                }

                                operator = "bl";
                            } else if (c == '3' || c == '7') {
                                numberValidation = 1;
                                binding.operatorLogoId.setBackgroundResource(R.drawable.gp_logo);
                                binding.operatorEditLogoId.setBackgroundResource(R.drawable.choperator);
                                binding.radioSkittoBtn.setVisibility(View.VISIBLE);
                                nmp="GP";
                                if (c == '3') {
                                    operatorCode = 13;
                                } else {
                                    operatorCode = 17;
                                }
                                operator = "gp";
                            } else if (c == '6') {
                                numberValidation = 1;
                                binding.operatorLogoId.setBackgroundResource(R.drawable.airtel_logo2);
                                binding.operatorEditLogoId.setBackgroundResource(R.drawable.choperator);
                                operatorCode = 16;
                                operator = "air";
                                nmp="AT";
                                binding.radioSkittoBtn.setVisibility(View.GONE);
                            } else if (c == '5') {
                                numberValidation = 1;
                                binding.operatorLogoId.setBackgroundResource(R.drawable.teletalk_logo2);
                                binding.operatorEditLogoId.setBackgroundResource(R.drawable.choperator);
                                operatorCode = 15;
                                operator = "tel";
                                nmp="TT";
                                binding.radioSkittoBtn.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(MobileRechargeActivity.this, "This is not Valid Number", Toast.LENGTH_SHORT).show();
                                numberValidation = 0;
                                binding.radioSkittoBtn.setVisibility(View.GONE);
                            }
                        } else {
                            numberValidation = 0;
                            binding.radioSkittoBtn.setVisibility(View.GONE);
                            Toast.makeText(MobileRechargeActivity.this, "This is not Valid Number", Toast.LENGTH_SHORT).show();
                        }


                    } else if (num.length() < 3) {
                        operatorCode = 0;
                        binding.radioSkittoBtn.setVisibility(View.GONE);
                        binding.operatorLogoId.setBackgroundResource(R.color.transparent);
                        binding.operatorEditLogoId.setBackgroundResource(R.color.transparent);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.operatorsId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioPrepaidBtn:
                        // do operations specific to this selection
                        NumberType = "prepaid";

                        break;
                    case R.id.radioPostBtn:
                        // do operations specific to this selection
                        NumberType = "postpaid";
                        break;
                    case R.id.radioSkittoBtn:
                        // do operations specific to this selection
                        NumberType = "skitto";
                        break;
                }
            }
        });
        binding.AmountId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    double amnt = Integer.parseInt(charSequence.toString());
                    newBalance = balance - (amnt + charge);
                    if (newBalance < 0) {
                        binding.idShortageView.setText(Math.abs(newBalance) + "");
                        binding.idShortageViewLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.idShortageViewLayout.setVisibility(View.GONE);
                    }
                } else {
                    binding.idShortageViewLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    private void rechargeHistory() {
        binding.rechargesRecView.setLayoutManager(new LinearLayoutManager(MobileRechargeActivity.this));
        adapter = new RechargeHistoryAdapter(MobileRechargeActivity.this, historyResponseList, R.layout.recharge_history_single_row);
        binding.rechargesRecView.setAdapter(adapter);


       // historyViewModel = new ViewModelProvider(this).get(RechargeHistoryViewModel.class);
//        historyViewModel.getData().observe(this, packageResponse -> {
//            controlProgressBar(false);
//            if (packageResponse.getData()!=null){
//                historyResponseList.clear();
//                binding.notFound.setVisibility(View.GONE);
//                historyResponseList.addAll(packageResponse.getData());
//                adapter.notifyDataSetChanged();
//            }else {
//                binding.rechargesRecView.setVisibility(View.GONE);
//
//            }
//
//
//        });
    }





    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        int id = view.getId();
        String num1 = binding.numberId.getText().toString();


        switch (id) {
            case R.id.mLogosId:
                if (num1.length() >= 3 && numberValidation == 1&&packAmount.equals("")) {
                    showDialog();
                }
                break;
            case R.id.flexiSubmitId:
                //user_id= CommonTask.getPreferences(getApplicationContext(), ConstantValues.user.USER_ID);
                String flexiAmount = binding.AmountId.getText().toString();
                String flexiNumber = binding.numberId.getText().toString();
                if (flexiNumber.length() == 11 && !flexiAmount.equals("")) {
                    if (Integer.parseInt(flexiAmount)>0){
                        submitDialog();
                    }else {
                        binding.AmountId.setError("Invalid amount");
                    }

                }else {
                    binding.numberId.setError("Invalid Number ");
                }
                break;
            case R.id.contactBtnLogoId:
                checkPermission();
                break;
        }

    }

    private void submitDialog() {
        String flexiAmount =  binding.AmountId.getText().toString();
        String flexiNumber = binding.numberId.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileRechargeActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.recharge_submit_dialogue, null);

        AppCompatButton submitBtn = dialogView.findViewById(R.id.id_submitBtn);
        ImageView closeDialogueBtn = dialogView.findViewById(R.id.id_closeDialogueBtn);
        TextView dialogueTotal = dialogView.findViewById(R.id.id_totalViewInDialogue);
        TextView dialogueBalance = dialogView.findViewById(R.id.id_newBalance);
        TextView dialogueType = dialogView.findViewById(R.id.id_typeInDialogue);
        TextView dialogueOperator = dialogView.findViewById(R.id.id_operatorInDialogue);
        TextView dialogueName = dialogView.findViewById(R.id.id_nameDialogue);
        TextView dialogueNumber = dialogView.findViewById(R.id.id_numberDialogue);

        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        dialogueTotal.setText(flexiAmount+"৳");
        dialogueType.setText(NumberType);
        dialogueBalance.setText(String.valueOf(newBalance));

        dialogueOperator.setText(operator);
        dialogueName.setText(flexiNumber);
        dialogueNumber.setText(flexiNumber);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
              //  mobileRechargeViewModel.recharge(user_id,flexiNumber,flexiAmount,NumberType,nmp);



            }
        });

        closeDialogueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }


    private void showDialog() {

        RelativeLayout dialogGPBtn;
        RelativeLayout dialogRobiBtn;
        RelativeLayout dialogBLBtn;
        RelativeLayout dialogAirBtn;
        RelativeLayout dialogTelBtn;

        AlertDialog.Builder builder = new AlertDialog.Builder(MobileRechargeActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.mobile_operators_dialog, null);

        dialogGPBtn = dialogView.findViewById(R.id.dialogGPBtnId);
        dialogRobiBtn = dialogView.findViewById(R.id.dialogRobiBtnId);
        dialogBLBtn = dialogView.findViewById(R.id.dialogBLBtnId);
        dialogAirBtn = dialogView.findViewById(R.id.dialogairBtnId);
        dialogTelBtn = dialogView.findViewById(R.id.dialogTelBtnId);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogGPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                binding.operatorLogoId.setBackgroundResource(R.drawable.gp_logo);
                operatorCode = 17;
                nmp="GP";

            }
        });
        dialogRobiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                binding.operatorLogoId.setBackgroundResource(R.drawable.robi_logo2);
                operatorCode = 18;
                nmp="RB";
            }
        });
        dialogBLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                binding.operatorLogoId.setBackgroundResource(R.drawable.banglalink_logo2);
                operatorCode = 19;
                nmp="BL";
            }
        });
        dialogAirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                binding.operatorLogoId.setBackgroundResource(R.drawable.airtel_logo2);
                operatorCode = 16;
                nmp="AT";
            }
        });
        dialogTelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                binding.operatorLogoId.setBackgroundResource(R.drawable.teletalk_logo2);
                operatorCode = 15;
                nmp="TT";
            }
        });


    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MobileRechargeActivity.this
                , Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MobileRechargeActivity.this
                    , new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            //startActivity(new Intent(MobileRechargeActivity.this, ContactListActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

           // startActivity(new Intent(MobileRechargeActivity.this, ContactListActivity.class));

        } else {
            Toast.makeText(MobileRechargeActivity.this, "Permission Denied.", Toast.LENGTH_LONG).show();
            checkPermission();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


}
