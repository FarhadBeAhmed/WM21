package co.wm21.https.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";
    private static final String KEY_REMEMBERED = "remember";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_MEMBER_TYPE = "mem_type";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private static final String USER_IS_LOGIN = "IsLoggedIn";
    public static final String KEY_CATEGORYTYPE = "categoryType";
    public static final String KEY_USERFULLNAME = "userFullName";
    public static final String KEY_USERADDRESS = "userAddress";
    public static final String KEY_COMPANYNAME = "company_name";
    public static final String KEY_COMPANYLOGO = "company_logo";
    public static final String KEY_COMPANYFULLADDRESS = "full_address";
    public static final String KEY_PHONEDETAILS = "phone_details";
    public static final String KEY_COMPANYEMAIL = "company_email";
    public static final String KEY_PROFILEIMAGEURL = "profileImageURL";
    public static final String KEY_PHONE_NUMBER = "userPhoneNumber";
    public static final String KEY_TREE_ID = "userTreeId";
    public static final String KEY_USER_ID = "userId";
    public static final String USER_ID = "user_id";


    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param username
     * @param password
     */
    public void loginUser(String username, String password, boolean remember, String memType, String userPhoneNumber) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.putBoolean(KEY_REMEMBERED, remember);
        mEditor.putString(KEY_MEMBER_TYPE, memType);
        mEditor.putString(KEY_PHONE_NUMBER, userPhoneNumber);
        Date date = new Date();

        //Set user session for next 24 days 20 hours 31 minute 12 seconds
        long millis = date.getTime() + 2147483647;
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }
    public void loginUser(String username, String password, boolean remember, String memType, String userPhoneNumber,String treeId,String userId) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.putBoolean(KEY_REMEMBERED, remember);
        mEditor.putString(KEY_MEMBER_TYPE, memType);
        mEditor.putString(KEY_PHONE_NUMBER, userPhoneNumber);
        mEditor.putString(KEY_TREE_ID, treeId);
        mEditor.putString(KEY_USER_ID, userId);
        Date date = new Date();

        //Set user session for next 24 days 20 hours 31 minute 12 seconds
        long millis = date.getTime() + 2147483647;
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public User getUserDetails() {
        return new User(mPreferences.getString(KEY_USERNAME, KEY_EMPTY),
                new Date(mPreferences.getLong(KEY_EXPIRES, 0)),
                mPreferences.getBoolean(KEY_REMEMBERED, true),
                mPreferences.getString(KEY_PASSWORD, KEY_EMPTY),
                mPreferences.getString(KEY_MEMBER_TYPE, KEY_EMPTY),
                mPreferences.getString(KEY_PHONE_NUMBER, KEY_EMPTY),
                mPreferences.getString(KEY_TREE_ID, KEY_EMPTY),
                mPreferences.getString(KEY_USER_ID, KEY_EMPTY)
        );
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }

    public void storeProfileImageUrl(String url) {
        mEditor.putString(KEY_PROFILEIMAGEURL, url);
        mEditor.commit();
    }
}
