package https.outsourcingvilla.com;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import https.outsourcingvilla.com.Annotations.*;

public final class ConstantValues {
    public static final String web_url = "https://www.outsourcingvilla.com/";
    public static final String macuva_url = "https://www.macuva.com/";
    public static final @Nullable String KEY_EMPTY = "";
    public static final String APP_VER = "";
    public static final String ID = "id";
    public static final String USER_ID = "userid";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String PASSWORD = "password";
    public static final String PACK = "package";
    public static final String MOBILE = "mobile";
    public static final String NOTICE = "notice";
    public static ArrayList<String> YOUTUBE_ADS = new ArrayList<>(Arrays.asList("Hjjzx2Ga4Bw", "NACy30fIu54"));
    public static int earnings = 0;

    public interface Login {
        String STATUS = "error";
        String MESSAGE = "msg";
        String FULL_NAME = "name";
        String USERNAME = "username";
        String PASSWORD = "password";
        String IMAGE = "img";
        String MOBILE = "mobile";
        String BKASHW = "bkash";
        String ROCKETW = "rocket";
        String NAGADW = "nagad";
    }

    public interface SignUp {
        String COUNTRY = "country";
        String PIN_CODE = "pin_code";
        String REFERRAL = "referral";
        String PLACEMENT = "placement";
        String POSITION = "position";
        String USER_ID = "user_id";
        String NAME = "name";
        String PASSWORD = "password";
        String PIN = "pin";
        String MOBILE = "mobile";
        String EMAIL = "email";
    }

    public interface Verification {
        String TASK = "task";
        String PERCENT = "percent";
    }

    public interface Balance {
        String FREELANCING_DAILY_WORK = "freelancing_daily_work";
        String REFFERED_COMMISSION = "reffered_commission";
        String AFFILIATE_GENERATION = "affiliate_generation";
        String EQUAL_BONUS = "equal_bonus";
        String GLOBAL_POOL_INCOME = "global_pool_income";
        String MONTHLY_SALARY = "monthly_salary";
        String EID_BONUS = "eid_bonus";
        String CHARITY_FUND = "charity_fund";
        String WITHDRAW_BALANCE = "withdraw_balance";
        String CONVERT_TO_SHOPPING = "convert_to_shopping";
        String NET_BALANCE = "net_bal";
        String INCOME = "income";

        String PROMOTION = "income";
        String PRODUCT = "product";
        String SHOPPING = "shopping";
        String CURRENT = "current";

        String PURCHASE_INCOME = "purchase_income";
        String REFERRAL_INCOME = "referral_income";
        String ECOMMERCE_REFERRAL = "ecommerce_referral";
        String GEMINATED_INCOME = "geminated_income";
        String GLOBAL_PROFIT_INCOME = "global_profit_income";
        String REFERRAL_PROFIT_INCOME = "referral_profit_income";
        String SALARY_INCOME = "salary_income";
        String REWARD_INCOME = "reward_income";
        String MARKETING_INCENSIVE = "marketing_incensive";
        String LEADERSHIP_INCOME = "leadership_income";
        String PROMOTIONAL_INCOME = "promotional_income";
        String AGENT_REFER_INCOME = "agent_refer_income";
    }

    public interface Transfer {
        String USER_ID = "userID";
        String AMOUNT = "amount";
        String PIN = "pin";
        String TYPE = "type";
    }

    public interface Withdraw {
        String METHOD = "method";
        String BANK = "bank";
        String BANK_NAME = "bank_name";
        String ACCOUNT_NAME = "account_name";
        String ACCOUNT_NO = "account_num";
        String BRANCH_NAME = "branch_name";
        String AGENT = "agent";
        String BKASH = "bkash";
        String ROCKET = "rocket";
        String NAGAD = "nagad";
        String AMOUNT = "amount";
        String PIN = "pin";
        String MESSAGE = "msg";
    }

    public interface Profile {
        String FULL_NAME = "full_name";
        String FATHER_NAME = "father_name";
        String MOTHER_NAME = "mother_name";
        String MOBILE_NUMBER = "mobile_number";
        String EMAIL_ADDRESS = "email_address";
        String COUNTRY = "country";
        String DIVISION = "division";
        String DISTRICT = "district";
        String THANA = "thana";
        String UNION = "union";
        String POST_CODE = "post_code";
        String ADDRESS = "address";
        String BIRTH = "birth";
        String TYPE = "type";
        String ID_CARD = "id_card";
        String BANK_NAME = "bank_name";
        String BRANCH_NAME = "bank_name";
        String BANK_ACCOUNT_NO = "bank_account_no";
        String BANK_ACCOUNT_NAME = "bank_account_name";
        String BKASH = "bkash";
        String ROCKET = "rocket";
        String NAGAD = "nagad";

        String NAME = "name";
        String IMAGE = "image";

        String VEF = "vef";
        String MOB = "mob";
        String CODE2 = "code2";

        String PASSWORD_0 = "Password0";
        String PASSWORD_1 = "Password1";
        String PASSWORD_2 = "Password2";
    }

    public interface AddFund {
        String PIN = "pin";
        String METHOD = "method";
        String GATEWAY = "gateway";
        String AMOUNT = "amount";
        String PDATE = "pdate";
        String BANK_NAME = "bank_name";
        String BRANCH_NAME = "branch_name";
        String ACCOUNT_NO = "account_num";
        String ACCOUNT_NAME = "account_name";
        String BKASH = "bkash_no";
        String ROCKET = "rocket_no";
        String NAGAD = "nagad_no";
        String NETTLER = "nettler_id";
        String PAYZA = "payza_id";
        String SKRILL = "skrill_id";
        String PAYPAL = "paypal_id";
        String RECEIPT = "recet_num";
        String TRX = "trx";
    }

    public interface Flexiload {
        String TYPE = "type";
        String MOBILE = "mobile";
        String AMOUNT = "amount";
        String PIN = "pincode";
    }

    public interface Product {
        String IMAGE = "img";
        String NAME = "name";
        String PRICE = "price";
        String TOTAL = "total";
        String PRODUCT = "product";
        String SERIAL = "serial";
        String SHOPPING = "shopping";
        String DISCOUNT = "discount";
        String AFFILIATE = "affiliate";

        String PIN = "pin";
        String QUANTITY = "qty";
    }

    public interface EcommerceInvite {
        String INVITE_MSG = "invite_msg";
        String GUEST_NAME = "name";
        String GUEST_MOBILE = "mobile";
        String PIN = "pincode";
        String RECEIVER = "receiver";
        String ACTION = "action";
        String DATE = "date";
        String MESSAGE = "message";
    }

    public interface EcommerceCommission {
        String CUSTOMER = "customer";
        String PRODUCT = "product";
        String CASHBACK = "cashback";
        String COMMISSION = "commission";
    }

    public interface Gallery {
        String URL = "url";
        String TITLE = "title";
    }

    public interface Report {
        interface Received {
            String SEND_ID = "send_id";
            String TYPE = "type";
            String AMOUNT = "amount";
            String DATE = "date";
        }

        interface Affiliate {
            String USER = "user";
            String NAME = "name";
            String DATE = "date";
            String POINT = "point";
        }
        interface AgentReport {
                    String USER = "user";
                    String NAME = "name";
                    String DATE = "date";
                    String FEE = "amount";
                    String STATUS = "status";
                    String TYPE = "type";
                    String ADDRESS = "address";
                }

        interface Gateway {
            String DATE = "date";
            String GATEWAY = "gateway";
            String PAYMENT_INFORMATION = "note";
            String IMAGE = "image";
            String AMOUNT = "amount";
            String STATUS = "status";
        }

        interface Transfer {
            String REC_ID = "rec_id";
            String TYPE = "type";
            String AMOUNT = "amount";
            String CHARGE = "tax";
            String RECEIVED = "received";
            String DATE = "date";
        }

        interface Referral_Commission {
            String PRICE = "price";
            String DIRECT = "direct";
        }

        interface Withdrawal {
            String PAID_FROM = "rec_id";
            String PAID_TO = "send_id";
            String TRX_ID = "trxid";
            String AMOUNT = "amount";
            String DETAILS = "note";
            String TIME = "time";
            String STATUS = "status";
        }

        interface Flexiload {
            String START_DATE = "start_date";
            String END_DATE = "end_date";
            String SEARCH = "search";
            String DATE = "date";
            String TIME = "time";
            String OPERATOR = "operator";
            String TYPE = "type";
            String STATUS = "status";
            String STATUS_COLOR = "status_color";
            String MOBILE = "mobile";
            String TRX_ID = "trx_id";
            String AMOUNT = "amount";
        }

        interface Salary {
            String RANK = "rank";
            String AMOUNT = "amount";
            String DATE = "date";
        }

        interface Referral_Income {
            String LOG_ID = "log_id";
            String PRICE = "price";
            String DATE = "date";
            String DIRECT = "direct";
        }

        interface Referral_Product_Income {
            String CID = "user_id";
            String NAME = "name";
            String DATE = "date";
            String AMOUNT = "amount";
        }

        interface Rank_Reward {
            String RANK = "rank";
            String NEED = "need";
            String INCENTIVE = "incentive";
            String SALARY_TARGET = "salary_target";
            String SALARY_MONTHLY = "salary_monthly";
            String STATUS = "status";
        }

        interface Geminate_Income {
            String LEFT_POINT = "left_point";
            String RIGHT_POINT = "right_point";
            String LEFT_CARRY = "left_cary";
            String RIGHT_CARRY = "right_cary";
            String MATCH = "match";
            String DATE = "date";
            String AMOUNT = "amount";
        }

        interface Promotional_Incentive {
            String CID = "user_id";
            String NAME = "name";
            String DATE = "date";
            String AMOUNT = "amount";
        }

        interface Market_Incentive {
            String ADS_TYPE = "ads_type";
            String DATE = "date";
            String AMOUNT = "amount";
        }

    }

    public static void addMultipleClickListener(View.OnClickListener v, @NonNull View... views) {
        for (View b : views) b.setOnClickListener(v);
    }

    /**
     * If you write blank string, It can return only <b>website</b>.<br>
     * We've create a only website value, So use that.
     * @param strings Use ("api", "*.php") instead of ("api/*.php") in this inner method.
     * @return Get File Name As Host Name Shortcut.
     */

    @NotNull
    public static String getFileNameAsHost(@NonNull String @NotNull ... strings) {
        String result = web_url;
        for (String f : strings) {
            result += f + "/";
        }
        result += "/";
        result = result.replace("//", "");
        return result;
    }

    @NotNull
    public static String getImageUrl(@NonNull String @NotNull ... strings) {
        String result = macuva_url;
        for (String f : strings) {
            result += f + "/";
        }
        result += "/";
        result = result.replace("//", "");
        return result;
    }

    /**
     * What all of {@link TextInputLayout} checked is text completed or not.
     * @param textInputLayouts enter the all {@link TextInputLayout}
     * @return is {@link TextInputLayout}(s) text completed or not.
     */
    public static boolean validation(TextInputLayout @NotNull ... textInputLayouts) {
        boolean result = true;
        for (TextInputLayout til : textInputLayouts) {
            if (!(til.getEditText().getText().toString().length() <0)) {
                til.setError("");
            }
            if (til.getEditText().getText().toString().isEmpty()) {
                til.setError("Field can't be empty.");
                result = false;
            }
        }
        return result;
    }

    public static boolean passwordValidation(TextInputLayout @NotNull ... textInputLayouts) {
        boolean result = true;
        for (TextInputLayout til : textInputLayouts) {
            if (!(til.getEditText().getText().toString().length() < 6)) {
                til.setError("");
            }
            if (til.getEditText().getText().toString().length() < 6) {
                til.setError("Minimum 6 character");
                result = false;
            }
        }
        return result;
    }

    @NonNull
    public static String getTextFromTextInputLayout(@NonNull TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }

    @NonNull
    public static String getRealStringEscape(String s) {
        String result = s;
        if (result == null) result = "";
        result = result.replace("`", "");
        result = result.replace("'", "");
        result = result.replace("\"", "");
        result = result.replace("null", "");
        return result;
    }

    /**
     * You get the url with submit value.
     * @param directory enter your directory on getFileNameAsHost()
     * @param variable enter your variable name on String ... [0] <br>
     *                 Then enter your variable on String ... [1]
     * @return you get the url with submit value.
     */
    @NonNull
    public static String GetMethodURL(String directory, @NonNull String[] ... variable) {
        String result = directory + "?&";
        for (String[] var : variable)
            result += "&" + var[0] + "=" + var[1];
        result = result.replace("&&", "");
        return result;
    }

    /**
     * Requires this method for using {@link API}.
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public static API getAPI() {
        return (API) Proxy.newProxyInstance(API.class.getClassLoader(), new Class<?>[] { API.class }, (proxy, method, args) -> {
            JSONObject jsonObject = new JSONObject();
            try { for (int i = 0; i < args.length - 1; i++) jsonObject.put(((Field) method.getParameterAnnotations()[i][0]).value(), args[i]);
            } catch (JSONException e) { e.printStackTrace(); }
            return new JsonObjectRequest(method.getAnnotation(SendMethod.class).value(), web_url + method.getAnnotation(RequestUrl.class).value(),
                    jsonObject, (Response.Listener<JSONObject>) args[args.length - 1], Throwable::printStackTrace);
        });
    }
}