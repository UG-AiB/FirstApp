package pl.edu.ug.aib.firstApp.navigationDrawer;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import pl.edu.ug.aib.firstApp.DrawerActivity;
import pl.edu.ug.aib.firstApp.R;

@EBean
public class DrawerHandler implements ListView.OnItemClickListener {

    private static final String LOG_TAG = DrawerHandler.class.getSimpleName();

    private static final int DEFAULT_MENU_ITEM_POSITION = 0;

    @RootContext
    DrawerActivity drawerActivity;

    @Bean
    DrawerListAdapter drawerListAdapter;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.left_drawer)
    ListView drawerList;

    private ActionBarDrawerToggle drawerToggle;

    public void init() {
        drawerToggle = new ActionBarDrawerToggle(
                drawerActivity,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            @Override
            public void onDrawerClosed(View drawerView) {
                int checkedItemPosition = drawerList.getCheckedItemPosition();
                if (checkedItemPosition < 0) {
                    checkedItemPosition = DEFAULT_MENU_ITEM_POSITION;
                }
                showFragment(checkedItemPosition);
            }

        };
        drawerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerActivity.getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerList.setAdapter(drawerListAdapter);
        drawerList.setOnItemClickListener(this);
        drawerToggle.setDrawerIndicatorEnabled(true);
        showDefaultScreen();
    }

    public void showDefaultScreen() {
        drawerList.setItemChecked(DEFAULT_MENU_ITEM_POSITION, true);
        showFragment(DEFAULT_MENU_ITEM_POSITION);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    private void showFragment(int position) {
        try {
            DrawerItem drawerItem = getDrawerItem(position);
            if (drawerItem == null) {
                return;
            }
            FragmentManager fragmentManager = drawerActivity.getSupportFragmentManager();
            final Class<? extends Fragment> fragmentClass = drawerItem.getFragmentClass();
            Fragment fragment = fragmentClass.newInstance();
            fragmentManager.beginTransaction()
                           .replace(R.id.content_frame, fragment)
                           .commit();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in drawer item selection", e);
        }

    }

    public boolean drawerToggleSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return false;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    private DrawerItem getDrawerItem(int position) {
        return (DrawerItem)drawerList.getAdapter().getItem(position);
    }

}
