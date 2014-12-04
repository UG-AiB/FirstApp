package pl.edu.ug.aib.firstApp;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import pl.edu.ug.aib.firstApp.adapter.PersonListAdapter;
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

    @AfterViews
    void init() {
//        String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        list.setAdapter(adapter);
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

        SecondActivity_.intent(this).username(username.getText().toString()).start();
    }

    @OptionsItem
    void settingsSelected() {
    }

}
