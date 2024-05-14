package co.wm21.https.fragments.member;

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

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentTreeViewGnLogyBinding;
import co.wm21.https.helpers.CheckInternetConnection;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreeViewGnLogyFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "treetrack";
    SessionHandler appSessionManager;
    CheckInternetConnection checkInternetConnection;
    View mView;
  //  private BridgeDatabaseAdapter dbAdapter;
    FragmentTreeViewGnLogyBinding binding;
    API api;
    User user;


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
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Sending ....")
                    .content("Please Wait")
                    .progress(true, 0)
                    .show();
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTreesData(subID).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().get("error").getAsInt();
                        if (errCount == 0) {
                            JsonArray treeArr = response.body().get("tree_info").getAsJsonArray();
                            JsonObject treeInfo = treeArr.get(0).getAsJsonObject();
                            String treeUser = treeInfo.get("user").getAsString();
                            String treeUserName = treeInfo.get("name").getAsString();
                            String treeGender = treeInfo.get("gender").getAsString();
                            String treeJoin = treeInfo.get("join").getAsString();
                            String treeRank = treeInfo.get("rank").getAsString();
                            String treeStatus = treeInfo.get("status").getAsString();
                            String treeRefer = treeInfo.get("refer").getAsString();
                            String treeATeam = treeInfo.get("A_Team_Member").getAsString();
                            String treeBTeam = treeInfo.get("B_Team_Member").getAsString();
                            String treeACarry = treeInfo.get("A_team_carry").getAsString();
                            String treeBCarry = treeInfo.get("B_team_carry").getAsString();
                            String treeAPoint = treeInfo.get("A_Team_Point").getAsString();
                            String treeBPoint = treeInfo.get("B_Team_Point").getAsString();
                            String treeProfileImage = treeInfo.get("photo").getAsString();
                            String sponsorsAteam = treeInfo.get("A_Team_spot").getAsString();
                            String sponsorsBteam = treeInfo.get("B_Team_spot").getAsString();


                            showProfileInfoPopup(userProfileID, treeUser, treeUserName, treeGender, treeJoin, treeRank,
                                    treeStatus, treeRefer, treeATeam, treeBTeam, treeACarry, treeBCarry, treeAPoint, treeBPoint,
                                    treeProfileImage, "root",sponsorsAteam,sponsorsBteam);
                        } else {
                            Toast.makeText(getActivity(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("BINARY TREE", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    dialog.dismiss();
                    Log.e("BINARY TREE", "Error :" + t.getMessage().toString());
                }
            });
        }
    }
    //go for create new account from tree fragment
    private void getUpperInfoForNew(String upID, String upHand) {
        Log.d(TAG, "getUpperInfoForNew: upID:" + upID + " upHand:" + upHand);
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Sending ....")
                    .content("Please Wait")
                    .progress(true, 0)
                    .show();

            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTreesData(upID).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().get("error").getAsInt();
                        if (errCount == 0) {
                            JsonArray treeArr = response.body().get("tree_info").getAsJsonArray();
                            JsonObject treeInfo = treeArr.get(0).getAsJsonObject();
                            String treeUser = treeInfo.get("user").getAsString();
                            //goForNewAcc(treeUser, upHand);
                        } else {
                            Toast.makeText(getActivity(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog.dismiss();
                        Log.e("BINARY TREE", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    dialog.dismiss();
                    Log.e("BINARY_TREE", "Something went wrong!");
                }
            });
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
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Sending ....")
                    .content("Please Wait")
                    .cancelable(false)
                    .progress(true, 0)
                    .show();
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTreeData(loadUserID,appSessionManager.getUserDetails().getUsername()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                    if (response.isSuccessful()) {
                        int errCount = response.body().get("error").getAsInt();
                        if (errCount == 0) {
                            JsonArray dataArr = response.body().get("tree").getAsJsonArray();
                            //JsonObject firstObj = dataArr.get(0).getAsJsonObject();

                            for (int i = 0; i < dataArr.size(); i++) {
                                JsonObject dataOBj = dataArr.get(i).getAsJsonObject();
                                String rootsIDs = dataOBj.get("id").getAsString();
                                String subID = dataOBj.get("sub_id").getAsString();
                                String handPos = dataOBj.get("hand").getAsString();
                                String userGender = dataOBj.get("gender").getAsString();
                                String name = dataOBj.get("name").getAsString();
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

                                binding.nameTwo.setText(names.get(2));
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
                        } else {
                            Toast.makeText(getActivity(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Log.e("BINARY TREE", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("LOG", t.toString());
                    dialog.dismiss();
                    Log.e("BINARY_TREE", "Something went wrong!");
                }
            });
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void loadProfileInfoPopup(final String userProfileID) {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Sending ....")
                    .content("Please Wait")
                    .progress(true, 0)
                    .show();
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTreesData(userProfileID).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().get("error").getAsInt();
                        if (errCount == 0) {
                            JsonArray treeArr = response.body().get("tree_info").getAsJsonArray();
                            JsonObject treeInfo = treeArr.get(0).getAsJsonObject();
                            String treeUser = treeInfo.get("user").getAsString();
                            String treeUserName = treeInfo.get("name").getAsString();
                            String treeGender = treeInfo.get("gender").getAsString();
                            String treeJoin = treeInfo.get("join").getAsString();
                            String treeRank = treeInfo.get("rank").getAsString();
                            String treeStatus = treeInfo.get("status").getAsString();
                            String treeRefer = treeInfo.get("refer").getAsString();
                            String treeATeam = treeInfo.get("A_Team_Member").getAsString();
                            String treeBTeam = treeInfo.get("B_Team_Member").getAsString();
                            String treeACarry = treeInfo.get("A_team_carry").getAsString();
                            String treeBCarry = treeInfo.get("B_team_carry").getAsString();
                            String treeAPoint = treeInfo.get("A_Team_Point").getAsString();
                            String treeBPoint = treeInfo.get("B_Team_Point").getAsString();
                            String treeProfileImage = treeInfo.get("photo").getAsString();
                            String sponsorsAteam = treeInfo.get("A_Team_spot").getAsString();
                            String sponsorsBteam = treeInfo.get("B_Team_spot").getAsString();

                            showProfileInfoPopup(userProfileID, treeUser, treeUserName, treeGender, treeJoin, treeRank,
                                    treeStatus, treeRefer, treeATeam, treeBTeam, treeACarry, treeBCarry, treeAPoint, treeBPoint,
                                    treeProfileImage, "normal",sponsorsAteam,sponsorsBteam);
                        } else {
                            Toast.makeText(getActivity(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("BINARY TREE", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    dialog.dismiss();
                    Log.e("BINARY TREE", "Error :" + t.getMessage().toString());
                }
            });
        }
    }

  /*  private void loadRootProfileInfoPopup(final String userProfileID, String subID) {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Sending ....")
                    .content("Please Wait")
                    .progress(true, 0)
                    .show();
            APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
            mApiService.getTreesData(subID).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        int errCount = response.body().get("error").getAsInt();
                        if (errCount == 0) {
                            JsonArray treeArr = response.body().get("tree_info").getAsJsonArray();
                            JsonObject treeInfo = treeArr.get(0).getAsJsonObject();
                            String treeUser = treeInfo.get("user").getAsString();
                            String treeUserName = treeInfo.get("name").getAsString();
                            String treeGender = treeInfo.get("gender").getAsString();
                            String treeJoin = treeInfo.get("join").getAsString();
                            String treeRank = treeInfo.get("rank").getAsString();
                            String treeStatus = treeInfo.get("status").getAsString();
                            String treeRefer = treeInfo.get("refer").getAsString();
                            String treeATeam = treeInfo.get("A_Team_Member").getAsString();
                            String treeBTeam = treeInfo.get("B_Team_Member").getAsString();
                            String treeACarry = treeInfo.get("A_team_carry").getAsString();
                            String treeBCarry = treeInfo.get("B_team_carry").getAsString();
                            String treeAPoint = treeInfo.get("A_Team_Point").getAsString();
                            String treeBPoint = treeInfo.get("B_Team_Point").getAsString();
                            String treeProfileImage = treeInfo.get("photo").getAsString();

                            showProfileInfoPopup(userProfileID, treeUser, treeUserName, treeGender, treeJoin, treeRank,
                                    treeStatus, treeRefer, treeATeam, treeBTeam, treeACarry, treeBCarry, treeAPoint, treeBPoint,
                                    treeProfileImage, "root");
                        } else {
                            Toast.makeText(getActivity(), response.body().get("error_report").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("BINARY TREE", "Error :" + response.code());
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    dialog.dismiss();
                    Log.e("BINARY TREE", "Error :" + t.getMessage().toString());
                }
            });
        }
    }*/

    private void showProfileInfoPopup(final String userProfileID, String user, String name, String gender, String join, String rank,
                                      String status, String refer, String aMember, String bMember, String aCarry, String bCarry,
                                      String aPoint, String bPoint, String profileImage, String userType,String sponsorsAteam,String sponsorsBteam) {

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
       /* if (aMember.equals("0") || aMember.equals("") && bMember.equals("0") || bMember.equals("")) {
            //textViewAMember.setVisibility(View.GONE);
            //textViewBMember.setVisibility(View.GONE);
        } else {
            //textViewAMember.setText("Team Member: " + aMember+" : "+bMember);
            //textViewBMember.setText("B Team Member: " + bMember);
        }

        if (aCarry.equals("0") || aCarry.equals("") && bCarry.equals("0") || bCarry.equals("")) {
            //linearLayoutCarry.setVisibility(View.GONE);
        } else {
           // textViewACarry.setText("Carry Point: " + aCarry+" : "+bCarry);
           // textViewBCarry.setText("Team B: " + bCarry);
        }

        if (aPoint.equals("0") || aPoint.equals("") && aPoint.equals("")) {
           // linearLayoutPoint.setVisibility(View.GONE);
        } else {
            //textViewAPoint.setText("Current Point: " + aPoint+" : "+bPoint);
           // textViewBPoint.setText("Team B: " + bPoint);
        }*/

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

}