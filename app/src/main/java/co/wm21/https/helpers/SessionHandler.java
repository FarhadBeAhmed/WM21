package co.wm21.https.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import co.wm21.https.helpers.User;

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
    public void loginUser(String username, String password, boolean remember, String memType) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.putBoolean(KEY_REMEMBERED, remember);
        mEditor.putString(KEY_MEMBER_TYPE, memType);
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
        return new User(mPreferences.getString(KEY_USERNAME, KEY_EMPTY), new Date(mPreferences.getLong(KEY_EXPIRES, 0)),
                        mPreferences.getBoolean(KEY_REMEMBERED, true), mPreferences.getString(KEY_PASSWORD, KEY_EMPTY),
                        mPreferences.getString(KEY_MEMBER_TYPE, KEY_EMPTY));
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }
}
