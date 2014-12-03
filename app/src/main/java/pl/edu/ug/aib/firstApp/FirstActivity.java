package pl.edu.ug.aib.firstApp;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_my)
@OptionsMenu(R.menu.my)
public class FirstActivity extends ActionBarActivity {

    @ViewById
    public EditText username;

    @ViewById
    public EditText password;

    @Click
    void loginClicked()    {
        if (username.getText().length() < 3) {
            Toast.makeText(this, getString(R.string.uernameTooShort), Toast.LENGTH_SHORT).show();
            return;
        }

        SecondActivity_.intent(this).username(username.getText().toString()).start();
    }

    @OptionsItem
    void settingsSelected() {
    }

}
