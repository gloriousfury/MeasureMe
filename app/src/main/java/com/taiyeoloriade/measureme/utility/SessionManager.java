package com.taiyeoloriade.measureme.utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ValueMinds on 2/26/2016.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file
    private static final String PREF_NAME = "NyscPref";

    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_IMG_URL = "img_url";
    public static final String KEY_NEW = "first_timer";
    public static final String KEY_ID = "id";
    public static final String KEY_PHONE_NO = "phone_number";
    public static final String KEY_USED = "app_already_used";
    public static final String PREF_COOKIES = "cookies";
    public static final String KEY_ACCOUNT_UPDATE = "updated_account";
    public static final String KEY_PHONE_VERIFIED = "phone_verified";
    public static final String KEY_EMAIL_VERIFIED = "email_verified";

    //public static final String
    public static final String KEY_DOWNLOAD_CLASS = "download_class_name";
    public static final String KEY_DOWNLOAD_CONTENT_TYPE = "download_content_type";
    public static final String KEY_DOWNLOAD_CONTENT_INDEX = "download_content_index";
    public static final String KEY_DOWNLOAD_TOKENS = "download_token_ids";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";



    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /** Create Login Session **/
    public void createLoginSession(String firstname, String lastname,
                                   String img, String email, String id, String phonenumber) {
        // Storing name in pref

        editor.putString(KEY_FIRSTNAME, firstname);
        editor.putString(KEY_LASTNAME, lastname);
        editor.putString(KEY_IMG_URL, img);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_PHONE_NO, phonenumber);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }


    /** Store Cookie **/
    public void setSessionCookie(String set) {
        editor.putString(PREF_COOKIES, set);
        editor.commit();
    }

    /** get Cookie **/
    public String getSessionCookie() {
        return pref.getString(PREF_COOKIES, null);
    }



    /** Create Login Session **/
    public void createAppUsedSession() {
        editor.putBoolean(KEY_USED, true);
        editor.commit();
    }

    public String getFirstName(){
        return pref.getString(KEY_FIRSTNAME, null);

    }
    public String getLastName(){
        String user = pref.getString(KEY_LASTNAME, null);
        return user;
    }
    public String getEmail(){
        return  pref.getString(KEY_EMAIL, null);

    }
    public void setEmail(String user){
        editor.putString(KEY_EMAIL,user);
        editor.commit();
    }
    public String getId(){
        String user = pref.getString(KEY_ID, null);
        return user;
    }
    public void setId(String user){
        editor.putString(KEY_ID,user);
        editor.commit();
    }
    public String getPhoneNo(){
        String user = pref.getString(KEY_PHONE_NO, null);
        return user;
    }
    public void setPhoneNo(String user){
        editor.putString(KEY_PHONE_NO,user);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public String checkLogin(){
        String status = "false";
        // Check login status
        if(!this.isLoggedIn()){
            status = "false";
        }
        else if(this.isLoggedIn()){
            status = "true";
        }
        return status;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));
        user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, null));
        user.put(KEY_IMG_URL, pref.getString(KEY_IMG_URL, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // return user
        return user;
    }



    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clear necessary data from Shared Preferences
        // editor.clear();
        editor.remove(KEY_LASTNAME);
        editor.remove(KEY_IMG_URL);
        editor.remove(KEY_ID);
        editor.remove(KEY_FIRSTNAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_USED);
        editor.remove(IS_LOGIN);
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isApplaunchFirstTime(){
        return pref.getBoolean(KEY_NEW, false);
    }




    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setAsUpdated() {
        editor.putBoolean(KEY_ACCOUNT_UPDATE,true);
        editor.commit();
    }



    public void createAppLaunchSession() {
        editor.putBoolean(KEY_NEW,true);
        editor.commit();
    }


}
