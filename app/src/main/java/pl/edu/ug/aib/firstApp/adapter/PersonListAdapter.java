package pl.edu.ug.aib.firstApp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import pl.edu.ug.aib.firstApp.data.Person;
import pl.edu.ug.aib.firstApp.itemView.PersonItemView;
import pl.edu.ug.aib.firstApp.itemView.PersonItemView_;

@EBean
public class PersonListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    List<Person> persons = new ArrayList<Person>();

    public PersonListAdapter() {
        persons.add(new Person("Ala Guć", "Drutex"));
        persons.add(new Person("Stefan But", "Uniwersytet Gdański"));
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Person getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PersonItemView personItemView;
        if (convertView == null) {
            personItemView = PersonItemView_.build(context);
        } else {
            personItemView = (PersonItemView) convertView;
        }

        personItemView.bind(getItem(position));

        return personItemView;
    }

}
