package co.wm21.https.view.activities.mlm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.ImageHelper;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.R;
import co.wm21.https.databinding.ActivityProfileBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.utils.dialog.TransientDialog;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnProfileDetailsView;
import co.wm21.https.presenter.ProfileDetailsPresenter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements OnProfileDetailsView {

    ActivityProfileBinding binding;

    API api;
    User user;
    private static final int SELECT_PICTURE = 3999;
    private Bitmap bitmap;
    private SessionHandler sessionHandler;
    String imEx;
    int verifyPercent = 0;
    LoadingDialog loadingDialog;
    ProfileDetailsPresenter profileDetailsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = ConstantValues.getAPI();
        user = new User(this);
        sessionHandler = user.getSession();

        binding.infoExpandableLayout.expand();
        binding.infExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
        binding.contactInfoExpandableLayout.expand();
        binding.contactInfoExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
        binding.gatewayExpandableLayout.expand();
        binding.gatewayExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
        binding.docExpandableLayout.expand();
        binding.docExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
        binding.basicInfo.setOnClickListener(v -> {
            if (binding.infoExpandableLayout.isExpanded()) {
                binding.infoUpdateButton.setClickable(false);
                binding.infoUpdateButton.setFocusable(false);
                binding.infoUpdateButton.setText("Details");
                binding.infoUpdateButton.setTextColor(Color.parseColor("#FE0000"));
                binding.infoUpdateButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.infoExpandableLayout.collapse();
                binding.infExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
            } else {
                binding.infoUpdateButton.setClickable(true);
                binding.infoUpdateButton.setFocusable(true);
                binding.infoUpdateButton.setText("Update");
                binding.infoUpdateButton.setTextColor(Color.WHITE);
                binding.infoUpdateButton.setBackgroundColor(Color.parseColor("#f6ac00"));
                binding.infoUpdateButton.setOnClickListener(view2 -> {
                    //switchFragment(new ProfileInfoFragment(), "ProfileInfoFragment");
                    Toast.makeText(this, "Update Button Clicked", Toast.LENGTH_SHORT).show();
                });
                binding.infoExpandableLayout.expand();
                binding.infExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
            }
        });
        binding.contactInfo.setOnClickListener(v -> {
            if (binding.contactInfoExpandableLayout.isExpanded()) {
                binding.contactInfoUpdateButton.setClickable(false);
                binding.contactInfoUpdateButton.setFocusable(false);
                binding.contactInfoUpdateButton.setText("Details");
                binding.contactInfoUpdateButton.setTextColor(Color.parseColor("#FE0000"));
                binding.contactInfoUpdateButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.contactInfoExpandableLayout.collapse();
                binding.contactInfoExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
            } else {
                binding.contactInfoUpdateButton.setClickable(true);
                binding.contactInfoUpdateButton.setFocusable(true);
                binding.contactInfoUpdateButton.setText("Update");
                binding.contactInfoUpdateButton.setTextColor(Color.WHITE);
                binding.contactInfoUpdateButton.setBackgroundColor(Color.parseColor("#f6ac00"));
                binding.contactInfoUpdateButton.setOnClickListener(view2 -> {
                    Toast.makeText(this, "Update Button Clicked", Toast.LENGTH_SHORT).show();
                });
                binding.contactInfoExpandableLayout.expand();
                binding.contactInfoExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
            }
        });
        binding.gatewayInfo.setOnClickListener(v -> {
            if (binding.gatewayExpandableLayout.isExpanded()) {
                binding.gatewayUpdateButton.setClickable(false);
                binding.gatewayUpdateButton.setFocusable(false);
                binding.gatewayUpdateButton.setText("Details");
                binding.gatewayUpdateButton.setTextColor(Color.parseColor("#FE0000"));
                binding.gatewayUpdateButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.gatewayExpandableLayout.collapse();
                binding.gatewayExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
            } else {
                binding.gatewayUpdateButton.setClickable(true);
                binding.gatewayUpdateButton.setFocusable(true);
                binding.gatewayUpdateButton.setText("Update");
                binding.gatewayUpdateButton.setTextColor(Color.WHITE);
                binding.gatewayUpdateButton.setBackgroundColor(Color.parseColor("#f6ac00"));
                binding.gatewayUpdateButton.setOnClickListener(view2 -> {
                    Toast.makeText(this, "Update Button Clicked", Toast.LENGTH_SHORT).show();
                });
                binding.gatewayExpandableLayout.expand();
                binding.gatewayExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
            }
        });
        binding.docInfo.setOnClickListener(v -> {
            if (binding.docExpandableLayout.isExpanded()) {
                binding.docUpdateButton.setClickable(false);
                binding.docUpdateButton.setFocusable(false);
                binding.docUpdateButton.setText("Details");
                binding.docUpdateButton.setTextColor(Color.parseColor("#FE0000"));
                binding.docUpdateButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.docExpandableLayout.collapse();
                binding.docExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
            } else {
                binding.docUpdateButton.setClickable(true);
                binding.docUpdateButton.setFocusable(true);
                binding.docUpdateButton.setText("Update");
                binding.docUpdateButton.setTextColor(Color.WHITE);
                binding.docUpdateButton.setBackgroundColor(Color.parseColor("#f6ac00"));
                binding.docUpdateButton.setOnClickListener(view2 -> {

                    Toast.makeText(this, "Update Button Clicked", Toast.LENGTH_SHORT).show();
                });
                binding.docExpandableLayout.expand();
                binding.docExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
            }
        });
        binding.updatePassInfo.setOnClickListener(v -> {
            if (binding.updatePassExpandableLayout.isExpanded()) {
                binding.updatePassExpandableLayout.collapse();
                binding.updatePassExpandIcon.setImageResource(R.drawable.ic_arrow_forward_red);
            } else {
                binding.updatePassExpandableLayout.expand();
                binding.updatePassExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
            }
        });

        binding.backButtonId.setOnClickListener(view -> {
            this.onBackPressed();
        });


        binding.passSubmitBtn.setOnClickListener(this::submitBtnClicked);

        binding.profileImage.setOnClickListener(this::changeImage);
        binding.uploadImage.setOnClickListener(this::UpdateProfileImage);
        binding.signView.setOnClickListener(this::signature);

        loadingDialog=new LoadingDialog(this);
        profileDetailsPresenter=new ProfileDetailsPresenter(this);

        getUserDetails();

    }

    private void signature(View view) {
      /*  final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog adChangeImage = builder.create();
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View customView = layoutInflater.inflate(R.layout.signature_pad, null);
        LinearLayout submit = (LinearLayout) customView.findViewById(R.id.submit);
        LinearLayout cancel = (LinearLayout) customView.findViewById(R.id.cancel);
        adChangeImage.setCancelable(true);
        adChangeImage.setView(customView);
        adChangeImage.show();
        submit.setOnClickListener(v -> {
            adChangeImage.dismiss();
        });

        cancel.setOnClickListener(v -> {
            adChangeImage.dismiss();
        });*/
    }

    private void submitBtnClicked(View view) {
        if (ConstantValues.validation(binding.confirmPass, binding.newPass, binding.currentPass)) {
            if (binding.newPass.getEditText().getText().toString().equals(binding.confirmPass.getEditText().getText().toString())) {
                MySingleton.getInstance(this).addToRequestQueue(api.updatePassword(user.getUsername(), binding.currentPass.getEditText().getText().toString(), binding.newPass.getEditText().getText().toString(), response -> {
                    try {
                        if (response.getString("error").equals("0")) {

                        }
                        showSnackBar(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
            } else {
                showSnackBar("confirm Password does not Match");
            }
        }
    }

    public void UpdateProfileImage(View view) {
        uploadProfileImage(bitmap);
    }

    public void changeImage(View view) {
        changeImage();
    }

    void changeImage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog adChangeImage = builder.create();
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View customView = layoutInflater.inflate(R.layout.dialog_layout_for_profile_image_source, null);
        LinearLayout galery = (LinearLayout) customView.findViewById(R.id.lnr_ChoseLoaction_Gallery);
        LinearLayout camera = (LinearLayout) customView.findViewById(R.id.lnr_ChoseLoaction_Camera);

        galery.setOnClickListener(v -> {
            getGalleryImage();
            adChangeImage.dismiss();
        });

        camera.setOnClickListener(v -> {
            getCameraImage();
            adChangeImage.dismiss();
        });
        adChangeImage.setCancelable(true);
        adChangeImage.setView(customView);
        adChangeImage.show();
    }

    void getGalleryImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    21);
            Log.e("pick image", "not permitted");
        } else {
            uploadImage();
        }
    }

    void getCameraImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    103);
            Log.e("pick image", "not permitted");
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 709);
        }
    }

    void uploadImage() {
        Log.e("permission accepted", "permission accepted");
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 103) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 709);
            } else {
                //  Toast.makeText(UserProfileActivity.this, "Camera Permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 21) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uploadImage();

            } else {
                //Toast.makeText(UserProfileActivity.this, "You have to grant permission to upload image..",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("activityResultcalled", "called");
        if (resultCode == RESULT_OK) {
            Log.e("resultCode", "RESULT_OK");
            if (requestCode == 709) {
                Log.e("requestCode", "709");
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    binding.profileImage.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        bitmap = getResizedBitmap(bitmap, 200);
                    }
                    binding.uploadImage.setVisibility(View.VISIBLE);

                    // uploadProfileImage (bitmap);
                }
            } else if (requestCode == SELECT_PICTURE) {

                Uri selectedImage = data.getData();
                String imageAbsolutePath = getRealPathFromUri(this, selectedImage);

                Log.e("received image uri ", "" + selectedImage);
                Log.e("received absoluteUrl ", "" + imageAbsolutePath);
                String[] imNa = imageAbsolutePath.split("/");

                imEx = imNa[imNa.length - 1];

                bitmap = BitmapFactory.decodeFile(imageAbsolutePath);
                if (bitmap != null) {
                    bitmap = getResizedBitmap(bitmap, 200);
                }
                binding.profileImage.setImageBitmap(bitmap);
                binding.uploadImage.setVisibility(View.VISIBLE);
                // uploadProfileImage(bmp);
            }
        }
    }


    private void uploadProfileImage(Bitmap bm) {

        Bitmap bmp = getResizedBitmap(bm, 200);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String imageName = timestamp.getTime() + imEx;

        MultipartBody.Part body = null;
        try {

            File f = new File(this.getCacheDir(), imageName);
            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(byteArray);
            fos.flush();
            fos.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), f);
            body = MultipartBody.Part.createFormData("image", f.getName(), requestFile);
            Log.e("exception ", "not exception here");

        } catch (Exception e) {
            Log.e("exception ", "exception here");
            e.printStackTrace();
        }

        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), sessionHandler.getUserDetails().getUsername());
        RequestBody userPass = RequestBody.create(MediaType.parse("text/plain"), sessionHandler.getUserDetails().getPassword());
        RequestBody userCategory = RequestBody.create(MediaType.parse("text/plain"), sessionHandler.getUserDetails().getMemberType());

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.Uploading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();


        //  APIService mService = ApiUtils.getApiService(ConstantValues.URL);
        APIService mApiService = ApiUtils.getApiService();
        Call<JsonObject> call = mApiService.uploadProfileImage(
                userName, userPass, body, userCategory
        );
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.e("send profile image", "success");
                    dialog.dismiss();

                    // if ( response.body().get("error").equals("0")) {
                    binding.uploadImage.setVisibility(View.INVISIBLE);
                    new TransientDialog(getApplicationContext())
                            .showTransientDialogWithOutAction("Report...", response.body().get("error_report").toString());
                    saveImage();
                    //}
                } else {
                    int statusCode = response.code();
                    Log.e("send_image", statusCode + "! stuck up ..");
                    dialog.dismiss();
                    new TransientDialog(getApplicationContext())
                            .showTransientDialogWithOutAction("Error ...", "Fail To change Profile Image ");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("send_image", t.getLocalizedMessage() + "//" + t.toString());
                dialog.dismiss();
                // new TransientDialog(getContext()).showTransientDialogWithOutAction("Success...", response.body().get("error_report").toString());
            }
        });
    }

    private void saveImage() {
        ImageHelper imgHelper = new ImageHelper();
        String uri = imgHelper.saveToInternalStorage(bitmap, getApplicationContext(), sessionHandler.getUserDetails().getUsername());
        sessionHandler.storeProfileImageUrl(uri);
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void showSnackBar(String msg) {

        Snackbar snackbar = Snackbar.make(binding.contextView, msg, Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void getUserDetails() {
        profileDetailsPresenter.profileDetailsDataLoad(user.getUsername());
     /*   if (user.getUsername() != null && user.getPassword() != null) {
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
                try {
                    if (response.getString("error").equals("0")) {
                        binding.name.setText(response.getString(ConstantValues.profile.NAME));
                        binding.yourName.setText(response.getString(ConstantValues.profile.NAME).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.NAME));
                        binding.designation.setText(response.getString(ConstantValues.profile.DESIGNATION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.DESIGNATION));
                        verifyPercent = Integer.parseInt(response.getString(ConstantValues.profile.PERCENT));
                        if (verifyPercent < 100) {
                            binding.verifiedIcon.setVisibility(View.GONE);
                            binding.verificationStat.setVisibility(View.VISIBLE);
                        } else {
                            binding.verifiedIcon.setVisibility(View.VISIBLE);
                            binding.verificationStat.setVisibility(View.GONE);
                        }
                        binding.verificationStat.setText(response.getString(ConstantValues.profile.PERCENT).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.PERCENT) + "% Verified");
                        Constant.setImageToImageView(binding.profileImage, "user", "photo", response.getString(ConstantValues.profile.IMAGE));
                        binding.packageName.setText(response.getString(ConstantValues.profile.PACKAGE).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.PACKAGE));
                        binding.userID.setText(response.getString(ConstantValues.profile.USER_ID).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.USER_ID));
                        binding.fatherName.setText(response.getString(ConstantValues.profile.FATHER).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.FATHER));
                        binding.motherName.setText(response.getString(ConstantValues.profile.MOTHER).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.MOTHER));
                        binding.birthDate.setText(response.getString(ConstantValues.profile.BIRTH).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.BIRTH));
                        binding.nid.setText(response.getString(ConstantValues.profile.NID).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.NID));
                        binding.gender.setText(response.getString(ConstantValues.profile.GENDER).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.GENDER));
                        binding.bloodGroup.setText(response.getString(ConstantValues.profile.BLOOD).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.BLOOD));
                        binding.profession.setText(response.getString(ConstantValues.profile.PROFESSION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.PROFESSION));
                        binding.education.setText(response.getString(ConstantValues.profile.EDUCATION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.EDUCATION));
                        binding.religion.setText(response.getString(ConstantValues.profile.RELIGION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.RELIGION));
                        binding.nominee.setText(response.getString(ConstantValues.profile.NOMINEE).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.NOMINEE));
                        binding.nomineeRelation.setText(response.getString(ConstantValues.profile.NOMINEE_RELATION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.NOMINEE_RELATION));
                        binding.email.setText(response.getString(ConstantValues.profile.EMAIL).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.EMAIL));
                        binding.mobileNumber.setText(response.getString(ConstantValues.profile.MOBILE).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.MOBILE));
                        binding.fatherName.setText(response.getString(ConstantValues.profile.FATHER).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.FATHER));
                        binding.village.setText(response.getString(ConstantValues.profile.ADDRESS).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.ADDRESS));
                        binding.word.setText(response.getString(ConstantValues.profile.WORD).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.WORD));
                        binding.union.setText(response.getString(ConstantValues.profile.UNION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.UNION));
                        binding.thana.setText(response.getString(ConstantValues.profile.THANA).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.THANA));
                        binding.district.setText(response.getString(ConstantValues.profile.DISTRICT).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.DIVISION));
                        binding.division.setText(response.getString(ConstantValues.profile.DIVISION).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.DIVISION));
                        binding.country2.setText(response.getString(ConstantValues.profile.COUNTRY).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.COUNTRY));
                        binding.bkashAccountNumber.setText(response.getString(ConstantValues.profile.BKASH).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.BKASH));
                        binding.rocketAccountNumber.setText(response.getString(ConstantValues.profile.ROCKET).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.ROCKET));
                        binding.nagadAccountNumber.setText(response.getString(ConstantValues.profile.NAGAD).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.NAGAD));
                        binding.bankName.setText(response.getString(ConstantValues.profile.BANK_NAME).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.BANK_NAME));
                        binding.accountName.setText(response.getString(ConstantValues.profile.ACCOUNT_NAME).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.ACCOUNT_NAME));
                        binding.accountNumber.setText(response.getString(ConstantValues.profile.ACCOUNT_NUMBER).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.ACCOUNT_NUMBER));
                        binding.branchName.setText(response.getString(ConstantValues.profile.BRANCH_NAME).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.BRANCH_NAME));
                        binding.routing.setText(response.getString(ConstantValues.profile.ROUTING).equals("") ? "{Not Found}" : response.getString(ConstantValues.profile.ROUTING));

                        Constant.setImageToImageView(binding.uploadedPhoto, "new", "sys", "stores", response.getString(ConstantValues.profile.IMAGE));
                        Constant.setImageToImageView(binding.uploadedSign, "member", "sign", response.getString(ConstantValues.profile.SIGN));
                        Constant.setImageToImageView(binding.uploadedKyc1, "member", "kyc", response.getString(ConstantValues.profile.KYC));
                        Constant.setImageToImageView(binding.uploadedKyc2, "member", "kyc", response.getString(ConstantValues.profile.KYC1));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));
        }*/
    }


    @Override
    public void onProfileDetailsDataLoad(ProfileDetailsHead profileDetailsHead) {
        binding.name.setText(profileDetailsHead.getData().getName());
        binding.yourName.setText(profileDetailsHead.getData().getName().equals("") ? "{Not Available}" :profileDetailsHead.getData().getName());
        binding.designation.setText(profileDetailsHead.getData().getDesignation().equals("") ? "{Not Available}" : profileDetailsHead.getData().getDesignation());
        verifyPercent = profileDetailsHead.getPercent();
        if (verifyPercent < 100) {
            binding.verifiedIcon.setVisibility(View.GONE);
            binding.verificationStat.setVisibility(View.VISIBLE);
        } else {
            binding.verifiedIcon.setVisibility(View.VISIBLE);
            binding.verificationStat.setVisibility(View.GONE);
        }
        binding.verificationStat.setText(profileDetailsHead.getPercent()==null ? "{Not Available}" : profileDetailsHead.getPercent()+ "% Verified");
        Constant.setImageToImageView(binding.profileImage, "user", "photo", (profileDetailsHead.getData().getImage()));
        binding.packageName.setText((profileDetailsHead.getData().getPackage().equals("") ? "{Not Available}" :profileDetailsHead.getData().getPackage()));
        binding.userID.setText(profileDetailsHead.getData().getId().equals("") ? "{Not Available}" : profileDetailsHead.getData().getId());
        binding.fatherName.setText(profileDetailsHead.getData().getFather().equals("") ? "{Not Available}" : profileDetailsHead.getData().getFather());
        binding.motherName.setText(profileDetailsHead.getData().getMother().equals("") ? "{Not Available}" :profileDetailsHead.getData().getMother());
        binding.birthDate.setText(profileDetailsHead.getData().getBirth().equals("") ? "{Not Available}" : profileDetailsHead.getData().getBirth());
        binding.nid.setText(profileDetailsHead.getData().getNid().equals("") ? "{Not Available}" : profileDetailsHead.getData().getNid());
        binding.gender.setText(profileDetailsHead.getData().getGender().equals("") ? "{Not Available}" : profileDetailsHead.getData().getGender());
        binding.bloodGroup.setText(profileDetailsHead.getData().getBlood().equals("") ? "{Not Available}" : profileDetailsHead.getData().getBlood());
        binding.profession.setText(profileDetailsHead.getData().getProf().equals("") ? "{Not Available}" : profileDetailsHead.getData().getProf());
        binding.education.setText(profileDetailsHead.getData().getEducation().equals("") ? "{Not Available}" : profileDetailsHead.getData().getEducation());
        binding.religion.setText(profileDetailsHead.getData().getReligion().equals("") ? "{Not Available}" : profileDetailsHead.getData().getReligion());
        binding.nominee.setText(profileDetailsHead.getData().getNominee().equals("") ? "{Not Found}" : profileDetailsHead.getData().getNominee());
        binding.nomineeRelation.setText(profileDetailsHead.getData().getNomiRelation().equals("") ? "{Not Available}" : profileDetailsHead.getData().getNomiRelation());
        binding.email.setText(profileDetailsHead.getData().getEmail().equals("") ? "{Not Available}" :profileDetailsHead.getData().getEmail());
        binding.mobileNumber.setText(profileDetailsHead.getData().getMobile().equals("") ? "{Not Available}" :profileDetailsHead.getData().getMobile());
        binding.fatherName.setText(profileDetailsHead.getData().getFather().equals("") ? "{Not Available}" : profileDetailsHead.getData().getFather());
        binding.village.setText(profileDetailsHead.getData().getAddress().equals("") ? "{Not Available}" : profileDetailsHead.getData().getAddress());
        binding.word.setText(profileDetailsHead.getData().getWord().equals("") ? "{Not Available}" : profileDetailsHead.getData().getWord());
        binding.union.setText(profileDetailsHead.getData().getUnion().equals("") ? "{Not Available}" : profileDetailsHead.getData().getUnion());
        binding.thana.setText(profileDetailsHead.getData().getThana().equals("") ? "{Not Available}" : profileDetailsHead.getData().getThana());
        binding.district.setText(profileDetailsHead.getData().getDistrict().equals("") ? "{Not Available}" : profileDetailsHead.getData().getDistrict());
        binding.division.setText(profileDetailsHead.getData().getDivision().equals("") ? "{Not Available}" : profileDetailsHead.getData().getDivision());
        binding.country2.setText(profileDetailsHead.getData().getCountry().equals("") ? "{Not Available}" :profileDetailsHead.getData().getCountry());
        binding.bkashAccountNumber.setText(profileDetailsHead.getData().getBkash().equals("") ? "{Not Available}" : profileDetailsHead.getData().getBkash());
        binding.rocketAccountNumber.setText(profileDetailsHead.getData().getRocket().equals("") ? "{Not Available}" : profileDetailsHead.getData().getRocket());
        binding.nagadAccountNumber.setText(profileDetailsHead.getData().getNagad().equals("") ? "{Not Available}" : profileDetailsHead.getData().getNagad());
        binding.bankName.setText(profileDetailsHead.getData().getBankName().equals("") ? "{Not Available}" : profileDetailsHead.getData().getBankName());
        binding.accountName.setText(profileDetailsHead.getData().getAccountName().equals("") ? "{Not Available}" : profileDetailsHead.getData().getAccountName());
        binding.accountNumber.setText(profileDetailsHead.getData().getAccountNo().equals("") ? "{Not Available}" : profileDetailsHead.getData().getAccountNo());
        binding.branchName.setText(profileDetailsHead.getData().getBranchName().equals("") ? "{Not Available}" : profileDetailsHead.getData().getBranchName());
        binding.routing.setText(profileDetailsHead.getData().getRouting().equals("") ? "{Not Available}" : profileDetailsHead.getData().getRouting());

        Constant.setImageToImageView(binding.uploadedPhoto, "new", "sys", "stores", profileDetailsHead.getData().getImage());
        Constant.setImageToImageView(binding.uploadedSign, "member", "sign", profileDetailsHead.getData().getSign());
        Constant.setImageToImageView(binding.uploadedKyc1, "member", "kyc", profileDetailsHead.getData().getKyc());
        Constant.setImageToImageView(binding.uploadedKyc2, "member", "kyc", profileDetailsHead.getData().getKyc1());


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

    }
}