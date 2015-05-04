package pl.edu.ug.aib.firstApp;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_map)
public class MapActivity extends Activity {

    @FragmentById
    MapFragment mapFragment;

    @AfterViews
    void init() {
        GoogleMap map = mapFragment.getMap();
        LatLng coordinates = new LatLng(54.399550, 18.568836);
        int zoom = 15;

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));

        map.addMarker(new MarkerOptions()
                .title("Uniwersytet Gdański")
                .snippet("Rektorat.")
                .position(coordinates));
    }

}