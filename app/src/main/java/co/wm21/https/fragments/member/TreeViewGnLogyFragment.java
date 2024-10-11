package co.wm21.https.fragments.member;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentTreeViewGnLogyBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnTreeDataView;
import co.wm21.https.interfaces.OnTreesListView;
import co.wm21.https.presenter.TreeDataPresenter;
import co.wm21.https.presenter.TreesListPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreeViewGnLogyFragment extends Fragment implements View.OnClickListener, OnTreesListView, OnTreeDataView {
    private static final String TAG = "treetrack";
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    View mView;
  //  private BridgeDatabaseAdapter dbAdapter;
    FragmentTreeViewGnLogyBinding binding;
    TreesListPresenter treesListPresenter;
    TreeDataPresenter treeDataPresenter;
    API api;
    User user;
    String userID,userIdForPopUp;
    int apiCallStatus=0;

    LoadingDialog loadingDialog;
    private ArrayList<String> IDs = new ArrayList<>();
    private ArrayList<String> subIDs = new ArrayList<>();
    private ArrayList<String> handPosition = new ArrayList<>();
    private ArrayList<String> gender = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentTreeViewGnLogyBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        api=ConstantValues.getAPI();
        user=new User(getContext());

        appSessionManager = new SessionHandler(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        String currentUserID = appSessionManager.getUserDetails().getUserId();

        treesListPresenter=new TreesListPresenter(this);
        treeDataPresenter=new TreeDataPresenter(this);
        loadingDialog=new LoadingDialog(getActivity());
       // dbAdapter = new BridgeDatabaseAdapter(getContext());
        loadTreeData(currentUserID);
        binding.imgTreeOne.setOnClickListener(this);
        binding.imgTreeTwo.setOnClickListener(this);
        binding.imgTreeThree.setOnClickListener(this);
        binding.imgTreeFour.setOnClickListener(this);
        binding.imgTreeFive.setOnClickListener(this);
        binding.imgTreeSix.setOnClickListener(this);
        binding.imgTreeSeven.setOnClickListener(this);
        binding.imgTreeEight.setOnClickListener(this);
        binding.imgTreeNine.setOnClickListener(this);
        binding.imgTreeTen.setOnClickListener(this);
        binding.imgTreeEleven.setOnClickListener(this);
        binding.imgTreeTwelve.setOnClickListener(this);
        binding.imgTreeThirteen.setOnClickListener(this);
        binding.imgTreeFifteen.setOnClickListener(this);
        binding.imgTreeFifteen.setOnClickListener(this);


    }


    private void showTreeData() {

        //for client one
        if (IDs.get(0).equals("0") && null != IDs.get(0)) {
            binding.nameOne.setText(names.get(0));
            if (gender.get(0).equals("Male") || gender.get(0).equals("male")) {
                Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
            } else if (gender.get(0).equals("Female") || gender.get(0).equals("female")) {
                Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
            }
        } else if (!IDs.get(0).equals("0")) {
            binding.nameOne.setText(names.get(0));
            if (gender.get(0).equals("Male") || gender.get(0).equals("male")) {
                Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
            } else if (gender.get(0).equals("Female") || gender.get(0).equals("female")) {
                Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
            }
        }

        //for client tow
        if (!IDs.get(1).equals("") && null != IDs.get(1)) {
            binding.nameTwo.setText(names.get(1));
            if (!subIDs.get(1).equals("") && null != subIDs.get(1)) {
                if (gender.get(1).equals("Male") || gender.get(1).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
                } else if (gender.get(1).equals("Female") || gender.get(1).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
            }
        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
        }

        //for client three
        if (!IDs.get(2).equals("") && null != IDs.get(2)) {

            binding.nameThree.setText(names.get(3));
            if (!subIDs.get(2).equals("") && null != subIDs.get(2)) {
                if (gender.get(2).equals("Male") || gender.get(2).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
                } else if (gender.get(2).equals("Female") || gender.get(2).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
            }
        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
        }

        //for client four
        if (!IDs.get(3).equals("") && null != IDs.get(3)) {
            if (!subIDs.get(3).equals("") && null != subIDs.get(3)) {
                if (gender.get(3).equals("Male") || gender.get(3).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
                } else if (gender.get(3).equals("Female") || gender.get(3).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
            }
        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
        }

        //for client five
        if (!IDs.get(4).equals("") && null != IDs.get(4)) {
            if (!subIDs.get(4).equals("") && null != subIDs.get(4)) {
                if (gender.get(4).equals("Male") || gender.get(4).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
                } else if (gender.get(4).equals("Female") || gender.get(4).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
            }
        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
        }

        //for client six
        if (!IDs.get(5).equals("") && null != IDs.get(5)) {
            if (!subIDs.get(5).equals("") && null != subIDs.get(5)) {
                if (gender.get(5).equals("Male") || gender.get(5).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
                } else if (gender.get(5).equals("Female") || gender.get(5).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
        }

        //for client seven
        if (!IDs.get(6).equals("") && null != IDs.get(6)) {
            if (!subIDs.get(6).equals("") && null != subIDs.get(6)) {
                if (gender.get(6).equals("Male") || gender.get(6).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
                } else if (gender.get(6).equals("Female") || gender.get(6).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
        }

        //for client eight
        if (!IDs.get(7).equals("") && null != IDs.get(7)) {
            if (!subIDs.get(7).equals("") && null != subIDs.get(7)) {
                if (gender.get(7).equals("Male") || gender.get(7).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
                } else if (gender.get(7).equals("Female") || gender.get(7).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
        }

        //for client nine
        if (!IDs.get(8).equals("") && null != IDs.get(8)) {
            if (!subIDs.get(8).equals("") && null != subIDs.get(8)) {
                if (gender.get(8).equals("Male") || gender.get(8).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
                } else if (gender.get(8).equals("Female") || gender.get(8).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
        }

        //for client ten
        if (!IDs.get(9).equals("") && null != IDs.get(9)) {
            if (!subIDs.get(9).equals("") && null != subIDs.get(9)) {
                if (gender.get(9).equals("Male") || gender.get(9).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
                } else if (gender.get(9).equals("Female") || gender.get(9).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
        }

        //for client eleven
        if (!IDs.get(10).equals("") && null != IDs.get(10)) {
            if (!subIDs.get(10).equals("") && null != subIDs.get(10)) {
                if (gender.get(10).equals("Male") || gender.get(10).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
                } else if (gender.get(10).equals("Female") || gender.get(10).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
        }

        //for client twelve
        if (!IDs.get(11).equals("") && null != IDs.get(11)) {
            if (!subIDs.get(11).equals("") && null != subIDs.get(11)) {
                if (gender.get(11).equals("Male") || gender.get(11).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
                } else if (gender.get(11).equals("Female") || gender.get(11).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
        }
        //for client thirteen
        if (!IDs.get(12).equals("") && null != IDs.get(12)) {
            if (!subIDs.get(12).equals("") && null != subIDs.get(12)) {
                if (gender.get(12).equals("Male") || gender.get(12).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
                } else if (gender.get(12).equals("Female") || gender.get(12).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
        }

        //for client fourteen
        if (!IDs.get(13).equals("") && null != IDs.get(13)) {
            if (!subIDs.get(13).equals("") && null != subIDs.get(13)) {
                if (gender.get(13).equals("Male") || gender.get(13).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
                } else if (gender.get(13).equals("Female") || gender.get(13).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
        }

        //for client fifteen
        if (!IDs.get(14).equals("") && null != IDs.get(14)) {
            if (!subIDs.get(14).equals("") && null != subIDs.get(14)) {
                if (gender.get(14).equals("Male") || gender.get(14).equals("male")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
                } else if (gender.get(14).equals("Female") || gender.get(14).equals("female")) {
                    Glide.with(getActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
                }
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
            }

        } else {
            Glide.with(getActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
        }
    }


    public TreeViewGnLogyFragment() {

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_tree_one:
                if (!IDs.get(0).equals("0")) {
                    loadRootProfileInfoPopup(IDs.get(0), subIDs.get(0));
                }
                break;
            case R.id.img_tree_two:
                if (!IDs.get(1).equals("") && null != IDs.get(1)) {
                    if (!subIDs.get(1).equals("") && null != subIDs.get(1)) {
                        loadProfileInfoPopup(subIDs.get(1));
                    } else {
                        getUpperInfoForNew(subIDs.get(0), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_three:
                if (!IDs.get(2).equals("") && null != IDs.get(2)) {
                    if (!subIDs.get(2).equals("") && null != subIDs.get(2)) {
                        loadProfileInfoPopup(subIDs.get(2));
                    } else {
                        getUpperInfoForNew(subIDs.get(0), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_Four:
                if (!IDs.get(3).equals("") && null != IDs.get(3)) {
                    if (!subIDs.get(3).equals("") && null != subIDs.get(3)) {
                        loadProfileInfoPopup(subIDs.get(3));
                    } else {
                        getUpperInfoForNew(subIDs.get(1), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_Five:
                if (!IDs.get(4).equals("") && null != IDs.get(4)) {
                    if (!subIDs.get(4).equals("") && null != subIDs.get(4)) {
                        loadProfileInfoPopup(subIDs.get(4));
                    } else {
                        getUpperInfoForNew(subIDs.get(1), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_Six:
                if (!IDs.get(5).equals("") && null != IDs.get(5)) {
                    if (!subIDs.get(5).equals("") && null != subIDs.get(5)) {
                        loadProfileInfoPopup(subIDs.get(5));
                    } else {
                        getUpperInfoForNew(subIDs.get(2), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_Seven:
                if (!IDs.get(6).equals("") && null != IDs.get(6)) {
                    if (!subIDs.get(6).equals("") && null != subIDs.get(6)) {
                        loadProfileInfoPopup(subIDs.get(6));
                    } else {
                        getUpperInfoForNew(subIDs.get(2), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_eight:
                if (!IDs.get(7).equals("") && null != IDs.get(7)) {
                    if (!subIDs.get(7).equals("") && null != subIDs.get(7)) {
                        loadProfileInfoPopup(subIDs.get(7));
                    } else {
                        getUpperInfoForNew(subIDs.get(3), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_nine:
                if (!IDs.get(8).equals("") && null != IDs.get(8)) {
                    if (!subIDs.get(8).equals("") && null != subIDs.get(8)) {
                        loadProfileInfoPopup(subIDs.get(8));
                    } else {
                        getUpperInfoForNew(subIDs.get(3), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }


                break;
            case R.id.img_tree_ten:
                if (!IDs.get(9).equals("") && null != IDs.get(9)) {
                    if (!subIDs.get(9).equals("") && null != subIDs.get(9)) {
                        loadProfileInfoPopup(subIDs.get(9));
                    } else {
                        getUpperInfoForNew(subIDs.get(4), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_eleven:
                if (!IDs.get(10).equals("") && null != IDs.get(10)) {
                    if (!subIDs.get(10).equals("") && null != subIDs.get(10)) {
                        loadProfileInfoPopup(subIDs.get(10));
                    } else {
                        getUpperInfoForNew(subIDs.get(4), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_twelve:
                if (!IDs.get(11).equals("") && null != IDs.get(11)) {
                    if (!subIDs.get(11).equals("") && null != subIDs.get(11)) {
                        loadProfileInfoPopup(subIDs.get(11));
                    } else {
                        getUpperInfoForNew(subIDs.get(5), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_thirteen:
                if (!IDs.get(12).equals("") && null != IDs.get(12)) {
                    if (!subIDs.get(12).equals("") && null != subIDs.get(12)) {
                        loadProfileInfoPopup(subIDs.get(12));
                    } else {
                        getUpperInfoForNew(subIDs.get(5), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_fourteen:
                if (!IDs.get(13).equals("") && null != IDs.get(13)) {
                    if (!subIDs.get(13).equals("") && null != subIDs.get(13)) {
                        loadProfileInfoPopup(subIDs.get(13));
                    } else {
                        getUpperInfoForNew(subIDs.get(6), "1");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
            case R.id.img_tree_fifteen:
                if (!IDs.get(14).equals("") && null != IDs.get(14)) {
                    if (!subIDs.get(14).equals("") && null != subIDs.get(14)) {
                        loadProfileInfoPopup(subIDs.get(14));
                    } else {
                        getUpperInfoForNew(subIDs.get(6), "2");
                    }

                } else {
                    Log.d(TAG, "onClick: no track.");
                }

                break;
           /* case R.id.binaryTreeSearch:
                Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                intent.putExtra("FUNDTYPE", "1");
                startActivityForResult(intent, 420);
                break;*/
        }
    }

    private void loadRootProfileInfoPopup(final String userProfileID, String subID) {
        apiCallStatus=1;
        if (checkInternetConnection.isInternetAvailable(getActivity())) {

            treesListPresenter.TreesDataLoad(subID);
            userID=userProfileID;

        }
    }
    //go for create new account from tree fragment
    private void getUpperInfoForNew(String upID, String upHand) {
        apiCallStatus=2;
        Log.d(TAG, "getUpperInfoForNew: upID:" + upID + " upHand:" + upHand);
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            treesListPresenter.TreesDataLoad(upID);
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }



    private void loadTreeData(String loadUserID) {
        IDs.clear();
        subIDs.clear();
        handPosition.clear();
        gender.clear();
        names.clear();
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            treeDataPresenter.TreeDataLoad(loadUserID,loadUserID);
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void loadProfileInfoPopup(final String userProfileID) {
        apiCallStatus = 3;
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            userIdForPopUp=userProfileID;
            treesListPresenter.TreesDataLoad(userProfileID);
        }
    }


    @SuppressLint("SetTextI18n")
    private void showProfileInfoPopup(final String userProfileID, String user, String name, String gender, String join, String rank,
                                      String status, String refer, String aMember, String bMember, String aCarry, String bCarry,
                                      String aPoint, String bPoint, String profileImage, String userType, String sponsorsAteam, String sponsorsBteam) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog adProfileInfo = builder.create();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View customView = layoutInflater.inflate(R.layout.popup_tree_profile_info, null);
        LinearLayout linearLayoutCancel = (LinearLayout) customView.findViewById(R.id.lnr_popup_profile_Cancel);
        LinearLayout linearLayoutUserTree = (LinearLayout) customView.findViewById(R.id.lnr_popup_profile_UserTree);

        LinearLayout linearLayoutCarry = (LinearLayout) customView.findViewById(R.id.lnr_popup_profile_CarryPoint);
        LinearLayout linearLayoutPoint = (LinearLayout) customView.findViewById(R.id.lnr_popup_profile_CurrentPoint);

        ImageView imageViewProfile = (ImageView) customView.findViewById(R.id.img_popup_profile_Image);
        TextView textViewName = (TextView) customView.findViewById(R.id.tv_popup_profile_Name);
        TextView textViewUser = (TextView) customView.findViewById(R.id.tv_popup_profile_User);
        TextView textViewGender = (TextView) customView.findViewById(R.id.tv_popup_profile_Gender);
        TextView textViewJoin = (TextView) customView.findViewById(R.id.tv_popup_profile_Join);
        TextView textViewRank = (TextView) customView.findViewById(R.id.tv_popup_profile_Rank);
        TextView textViewStatus = (TextView) customView.findViewById(R.id.tv_popup_profile_IDStatus);
        TextView textViewRefer = (TextView) customView.findViewById(R.id.tv_popup_profile_ReferID);
        TextView textViewAMember = (TextView) customView.findViewById(R.id.tv_popup_profile_LeftMember);
        //TextView textViewBMember = (TextView) customView.findViewById(R.id.tv_popup_profile_RightMember);
        TextView textViewACarry = (TextView) customView.findViewById(R.id.tv_popup_profile_CarryPointTeamA);
        //TextView textViewBCarry = (TextView) customView.findViewById(R.id.tv_popup_profile_CarryPointTeamB);
        TextView textViewAPoint = (TextView) customView.findViewById(R.id.tv_popup_profile_CurrentPointTeamA);
        // TextView textViewBPoint = (TextView) customView.findViewById(R.id.tv_popup_profile_CurrentPointTeamB);
        TextView textViewShowTree = (TextView)customView.findViewById(R.id.txt_popup_profile_showTree);
        TextView textViewSponsors = (TextView)customView.findViewById(R.id.tv_popup_profile_sponsors);

        if (userType.equals("root")){
            textViewShowTree.setText("Upper Tree");
        } else {
            textViewShowTree.setText("User's Tree");
        }

        textViewName.setText("Name: " + name);
        textViewUser.setText("User: " + user);
        textViewGender.setText("Gender: " + gender);
        textViewJoin.setText("Join: " + join);
        textViewRank.setText("Rank: " + rank);
        textViewStatus.setText("ID Status: " + status);
        textViewRefer.setText("Refer ID: " + refer);
        textViewSponsors.setText("Sponsor's: " + sponsorsAteam+" : "+sponsorsBteam);
        textViewAMember.setText("Team Member: " + aMember+" : "+bMember);
        textViewACarry.setText("Carry Point: " + aCarry+" : "+bCarry);
        textViewAPoint.setText("Today's Point: " + aPoint+" : "+bPoint);

        linearLayoutUserTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTreeData(userProfileID);
                adProfileInfo.dismiss();
            }
        });

        linearLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adProfileInfo.dismiss();
            }
        });

        if (gender.equals("Male") || gender.equals("male")) {
            Glide.with(getActivity()).load(ConstantValues.URL + profileImage).apply(new RequestOptions().error((R.drawable.ic_user_male)).fitCenter()).into(imageViewProfile);
        } else if (gender.equals("Female") || gender.equals("female")) {
            Glide.with(getActivity()).load(ConstantValues.URL + profileImage).apply(new RequestOptions().error((R.drawable.ic_user_female)).fitCenter()).into(imageViewProfile);
        }

        adProfileInfo.setCancelable(true);
        adProfileInfo.setView(customView);
        adProfileInfo.show();
    }

    @Override
    public void onTreesListDataLoad(TreesModel treesModel) {
        if (apiCallStatus==1){
            String treeUser = treesModel.getData().get(0).getUser();
            String treeUserName = treesModel.getData().get(0).getName();
            String treeGender = treesModel.getData().get(0).getGender();
            String treeJoin = treesModel.getData().get(0).getJoin();
            String treeRank = treesModel.getData().get(0).getRank();
            String treeStatus = treesModel.getData().get(0).getStatus();
            String treeRefer = treesModel.getData().get(0).getRefer();
            String treeATeam = treesModel.getData().get(0).getATeamMember();
            String treeBTeam = treesModel.getData().get(0).getBTeamMember();
            String treeACarry = treesModel.getData().get(0).getATeamCarry();
            String treeBCarry = treesModel.getData().get(0).getBTeamCarry();
            String treeAPoint = treesModel.getData().get(0).getATeamPoint();
            String treeBPoint = treesModel.getData().get(0).getBTeamPoint();
            String treeProfileImage = treesModel.getData().get(0).getPhoto();
            String sponsorsAteam = treesModel.getData().get(0).getATeamSpot();
            String sponsorsBteam = treesModel.getData().get(0).getBTeamSpot();


            showProfileInfoPopup(userID, treeUser, treeUserName, treeGender, treeJoin, treeRank,
                    treeStatus, treeRefer, treeATeam, treeBTeam, treeACarry, treeBCarry, treeAPoint, treeBPoint,
                    treeProfileImage, "root",sponsorsAteam,sponsorsBteam);
        } else if (apiCallStatus==2) {



        } else if (apiCallStatus == 3) {
            //JsonArray treeArr = response.body().get("tree_info").getAsJsonArray();
            //JsonObject treeInfo = treeArr.get(0).getAsJsonObject();
            String treeUser = treesModel.getData().get(0).getUser();
            String treeUserName = treesModel.getData().get(0).getName();
            String treeGender = treesModel.getData().get(0).getGender();
            String treeJoin = treesModel.getData().get(0).getJoin();
            String treeRank = treesModel.getData().get(0).getRank();
            String treeStatus = treesModel.getData().get(0).getStatus();
            String treeRefer = treesModel.getData().get(0).getRefer();
            String treeATeam = treesModel.getData().get(0).getATeamMember();
            String treeBTeam = treesModel.getData().get(0).getBTeamMember();
            String treeACarry = treesModel.getData().get(0).getATeamCarry();
            String treeBCarry = treesModel.getData().get(0).getBTeamCarry();
            String treeAPoint = treesModel.getData().get(0).getATeamPoint();
            String treeBPoint = treesModel.getData().get(0).getBTeamPoint();
            String treeProfileImage = treesModel.getData().get(0).getPhoto();
            String sponsorsAteam = treesModel.getData().get(0).getATeamSpot();
            String sponsorsBteam = treesModel.getData().get(0).getBTeamSpot();

            showProfileInfoPopup(userIdForPopUp, treeUser, treeUserName, treeGender, treeJoin, treeRank,
                    treeStatus, treeRefer, treeATeam, treeBTeam, treeACarry, treeBCarry, treeAPoint, treeBPoint,
                    treeProfileImage, "normal",sponsorsAteam,sponsorsBteam);

        }


    }

    @Override
    public void onTreesListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onTreesListStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onTreesListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTreeDataLoad(TreeModel treeModel) {
        if (!treeModel.getTree().isEmpty()) {


            for (int i = 0; i < treeModel.getTree().size(); i++) {

                String rootsIDs = treeModel.getTree().get(i).getId();
                String subID = treeModel.getTree().get(i).getSubId();
                String handPos = treeModel.getTree().get(i).getHand();
                String userGender = treeModel.getTree().get(i).getGender();
                String name = treeModel.getTree().get(i).getName();
                IDs.add(rootsIDs);
                subIDs.add(subID);
                handPosition.add(handPos);
                gender.add(userGender);
                names.add(name);
            }

            //for client one
            if (IDs.get(0).equals("0") && null != IDs.get(0)) {
                binding.nameOne.setText(names.get(0));
                if (gender.get(0).equals("Male") || gender.get(0).equals("male")) {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
                } else if (gender.get(0).equals("Female") || gender.get(0).equals("female")) {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
                }
            } else if (!IDs.get(0).equals("0")) {

                binding.nameOne.setText(names.get(0));
                if (gender.get(0).equals("Male") || gender.get(0).equals("male")) {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
                } else if (gender.get(0).equals("Female") || gender.get(0).equals("female")) {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeOne);
                }
            }

            //for client tow
            if (!IDs.get(1).equals("") && null != IDs.get(1)) {

                binding.nameTwo.setText(names.get(1));
                if (!subIDs.get(1).equals("") && null != subIDs.get(1)) {
                    if (gender.get(1).equals("Male") || gender.get(1).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
                    } else if (gender.get(1).equals("Female") || gender.get(1).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
                }
            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwo);
            }

            //for client three
            if (!IDs.get(2).equals("") && null != IDs.get(2)) {

                binding.nameThree.setText(names.get(2));
                if (!subIDs.get(2).equals("") && null != subIDs.get(2)) {
                    if (gender.get(2).equals("Male") || gender.get(2).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
                    } else if (gender.get(2).equals("Female") || gender.get(2).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
                }
            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThree);
            }

            //for client four
            if (!IDs.get(3).equals("") && null != IDs.get(3)) {
                if (!subIDs.get(3).equals("") && null != subIDs.get(3)) {
                    if (gender.get(3).equals("Male") || gender.get(3).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
                    } else if (gender.get(3).equals("Female") || gender.get(3).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
                }
            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFour);
            }

            //for client five
            if (!IDs.get(4).equals("") && null != IDs.get(4)) {
                if (!subIDs.get(4).equals("") && null != subIDs.get(4)) {
                    if (gender.get(4).equals("Male") || gender.get(4).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
                    } else if (gender.get(4).equals("Female") || gender.get(4).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
                }
            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFive);
            }

            //for client six
            if (!IDs.get(5).equals("") && null != IDs.get(5)) {
                if (!subIDs.get(5).equals("") && null != subIDs.get(5)) {
                    if (gender.get(5).equals("Male") || gender.get(5).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
                    } else if (gender.get(5).equals("Female") || gender.get(5).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSix);
            }

            //for client seven
            if (!IDs.get(6).equals("") && null != IDs.get(6)) {
                if (!subIDs.get(6).equals("") && null != subIDs.get(6)) {
                    if (gender.get(6).equals("Male") || gender.get(6).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
                    } else if (gender.get(6).equals("Female") || gender.get(6).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeSeven);
            }

            //for client eight
            if (!IDs.get(7).equals("") && null != IDs.get(7)) {
                if (!subIDs.get(7).equals("") && null != subIDs.get(7)) {
                    if (gender.get(7).equals("Male") || gender.get(7).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
                    } else if (gender.get(7).equals("Female") || gender.get(7).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEight);
            }

            //for client nine
            if (!IDs.get(8).equals("") && null != IDs.get(8)) {
                if (!subIDs.get(8).equals("") && null != subIDs.get(8)) {
                    if (gender.get(8).equals("Male") || gender.get(8).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
                    } else if (gender.get(8).equals("Female") || gender.get(8).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeNine);
            }

            //for client ten
            if (!IDs.get(9).equals("") && null != IDs.get(9)) {
                if (!subIDs.get(9).equals("") && null != subIDs.get(9)) {
                    if (gender.get(9).equals("Male") || gender.get(9).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
                    } else if (gender.get(9).equals("Female") || gender.get(9).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTen);
            }

            //for client eleven
            if (!IDs.get(10).equals("") && null != IDs.get(10)) {
                if (!subIDs.get(10).equals("") && null != subIDs.get(10)) {
                    if (gender.get(10).equals("Male") || gender.get(10).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
                    } else if (gender.get(10).equals("Female") || gender.get(10).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeEleven);
            }

            //for client twelve
            if (!IDs.get(11).equals("") && null != IDs.get(11)) {
                if (!subIDs.get(11).equals("") && null != subIDs.get(11)) {
                    if (gender.get(11).equals("Male") || gender.get(11).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
                    } else if (gender.get(11).equals("Female") || gender.get(11).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeTwelve);
            }
            //for client thirteen
            if (!IDs.get(12).equals("") && null != IDs.get(12)) {
                if (!subIDs.get(12).equals("") && null != subIDs.get(12)) {
                    if (gender.get(12).equals("Male") || gender.get(12).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
                    } else if (gender.get(12).equals("Female") || gender.get(12).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeThirteen);
            }

            //for client fourteen
            if (!IDs.get(13).equals("") && null != IDs.get(13)) {
                if (!subIDs.get(13).equals("") && null != subIDs.get(13)) {
                    if (gender.get(13).equals("Male") || gender.get(13).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
                    } else if (gender.get(13).equals("Female") || gender.get(13).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFourteen);
            }

            //for client fifteen
            if (!IDs.get(14).equals("") && null != IDs.get(14)) {
                if (!subIDs.get(14).equals("") && null != subIDs.get(14)) {
                    if (gender.get(14).equals("Male") || gender.get(14).equals("male")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_male).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
                    } else if (gender.get(14).equals("Female") || gender.get(14).equals("female")) {
                        Glide.with(requireActivity()).load(R.drawable.ic_user_female).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
                    }
                } else {
                    Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
                }

            } else {
                Glide.with(requireActivity()).load(R.drawable.ic_user_cancel).apply(new RequestOptions().error((R.drawable.ic_user_cancel)).fitCenter()).into(binding.imgTreeFifteen);
            }
        }

    }

    @Override
    public void onTreeDataStartLoading() {
        loadingDialog.startLoadingAlertDialog();

    }

    @Override
    public void onTreeDataStopLoading() {
        loadingDialog.dismissDialog();

    }

    @Override
    public void onTreeDataShowMessage(String errMsg) {

    }
}