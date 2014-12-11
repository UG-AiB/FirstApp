package pl.edu.ug.aib.firstApp;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import pl.edu.ug.aib.firstApp.adapter.PersonListAdapter;
import pl.edu.ug.aib.firstApp.data.FacebookPage;
import pl.edu.ug.aib.firstApp.data.Person;

@EActivity(R.layout.activity_my)
@OptionsMenu(R.menu.my)
public class FirstActivity extends ActionBarActivity {

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @ViewById
    ListView list;

    @Bean
    PersonListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestBackgroundTask restBackgroundTask;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {
        list.setAdapter(adapter);
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Loading...");
        ringProgressDialog.setIndeterminate(true);
    }

    @ItemClick
    void listItemClicked(Person item) {
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show();
    }


    @Click
    void loginClicked()    {
        if (username.getText().length() < 3) {
            Toast.makeText(this, getString(R.string.uernameTooShort), Toast.LENGTH_SHORT).show();
            return;
        }
        ringProgressDialog.show();
        restBackgroundTask.doInBackground(username.getText().toString());
    }

    public void goToSecondActivity(FacebookPage fbPage) {
        ringProgressDialog.dismiss();
        SecondActivity_.intent(this).fbPage(fbPage).start();
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @OptionsItem
    void settingsSelected() {
    }

}
