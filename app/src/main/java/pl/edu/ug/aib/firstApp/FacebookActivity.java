package pl.edu.ug.aib.firstApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

@EActivity(R.layout.activity_facebook)
public class FacebookActivity extends FragmentActivity implements UserInfoChangedCallback {

    @ViewById
    LoginButton loginButton;

    @ViewById
    TextView userName;

    @AfterViews
    void init() {
        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.setUserInfoChangedCallback(this);
    }

    @Override
    public void onUserInfoFetched(GraphUser user) {
        if (user != null) {
            userName.setText(user.getName() + ", witaj!");
        } else {
            userName.setText("");
        }
    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                Log.d(getClass().getSimpleName(), "Facebook session opened");
            } else if (state.isClosed()) {
                Log.d(getClass().getSimpleName(), "Facebook session closed");
            }
        }
    };

    private UiLifecycleHelper uiHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

}