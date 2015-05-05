package pl.edu.ug.aib.firstApp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.activity_notification_demo)
public class NotificationDemoActivity extends Activity {

    private final String LOG_TAG = getClass().getSimpleName();

    @ViewById
    TextView notifWelcomeText;

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    String SENDER_ID = "388320807422"; // Project ID from Google Developer Console > Overview

    GoogleCloudMessaging gcm;
    Context context;

    String registrationId;

    @AfterViews
    void init() {
        context = getApplicationContext();
        if (!checkPlayServices()) {
            Log.i(LOG_TAG, "Nie odnaleziono poprawnej wersji pakietu APK Google Play Services.");
            return;
        }

        gcm = GoogleCloudMessaging.getInstance(this);
        registrationId = getRegistrationId(context);

        if (registrationId.isEmpty()) {
            registerInBackground();
        } else {
            showMessage("Id rejestracji w dalszym ciągu ważne: " + registrationId);
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences();
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(LOG_TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LOG_TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences() {
        return getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @Background
    void registerInBackground() {
        String msg;
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            registrationId = gcm.register(SENDER_ID);
            msg = "Urządzenie zarejestrowane, ID rejestracji=" + registrationId;
            sendRegistrationIdToBackend();
            storeRegistrationId(context, registrationId);
        } catch (IOException ex) {
            msg = "Błąd:" + ex.getMessage();
        }
        showMessage(msg);
    }

    @UiThread
    void showMessage(String msg) {
        notifWelcomeText.setText(msg + "\n");
        Log.i(LOG_TAG, msg);
    }

    private void sendRegistrationIdToBackend() {
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences();
        int appVersion = getAppVersion(context);
        Log.i(LOG_TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
}