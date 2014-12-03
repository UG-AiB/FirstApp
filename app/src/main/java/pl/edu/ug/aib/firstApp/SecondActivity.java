package pl.edu.ug.aib.firstApp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_second)
public class SecondActivity extends ActionBarActivity {

    @Extra
    String username;

    @ViewById
    TextView welcomeText;

    @ViewById
    EditText phoneNumber;

    @AfterViews
    void init() {
        welcomeText.setText(String.format(getString(R.string.welcomeWithName), username));
    }

    @Click
    void dialClicked() {
        String tel = phoneNumber.getText().toString();
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + tel));
        startActivity(i);
    }

}