package pl.edu.ug.aib.firstApp.navigationDrawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import pl.edu.ug.aib.firstApp.R;
import pl.edu.ug.aib.firstApp.fragment.AboutAppFragment_;
import pl.edu.ug.aib.firstApp.fragment.HomeFragment_;

@EBean
public class DrawerListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    List<DrawerItem> items = new ArrayList<DrawerItem>();

    @AfterInject
    void init() {
        items.clear();
        items.add(new DrawerItem(R.string.title_home, R.drawable.icon_home, HomeFragment_.class));
        items.add(new DrawerItem(R.string.title_aboutapp, R.drawable.icon_about, AboutAppFragment_.class));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemView drawerItemView;
        if (convertView == null) {
            drawerItemView = DrawerItemView_.build(context);
        } else {
            drawerItemView = (DrawerItemView) convertView;
        }

        drawerItemView.bind(getItem(position));

        return drawerItemView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DrawerItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
