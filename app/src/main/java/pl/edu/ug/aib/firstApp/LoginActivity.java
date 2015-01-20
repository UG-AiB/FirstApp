package pl.edu.ug.aib.firstApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;

import pl.edu.ug.aib.firstApp.data.EmailAndPassword;
import pl.edu.ug.aib.firstApp.data.User;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {

    @Bean
    @NonConfigurationInstance
    RestLoginBackgroundTask restLoginBackgroundTask;

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Loading...");
        ringProgressDialog.setIndeterminate(true);
    }

    @Click
    void loginClicked()    {
        ringProgressDialog.show();
        EmailAndPassword emailAndPassword = new EmailAndPassword();
        emailAndPassword.email = email.getText().toString(); //"example@example.com";
        emailAndPassword.password = password.getText().toString(); //"test00";
        restLoginBackgroundTask.login(emailAndPassword);
    }

    public void loginSuccess(User user) {
        ringProgressDialog.dismiss();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user", user);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

}
