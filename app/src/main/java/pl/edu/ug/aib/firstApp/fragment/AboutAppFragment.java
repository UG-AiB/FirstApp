package pl.edu.ug.aib.firstApp.fragment;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import pl.edu.ug.aib.firstApp.R;

@EFragment(R.layout.fragment_aboutapp)
public class AboutAppFragment extends Fragment {

    @AfterViews
    void init() {
        getActivity().setTitle(R.string.title_aboutapp);
    }

}
