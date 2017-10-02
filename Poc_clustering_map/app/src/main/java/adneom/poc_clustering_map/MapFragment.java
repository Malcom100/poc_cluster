package adneom.poc_clustering_map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import adneom.poc_clustering_map.ClusterRender.TotalRender;
import adneom.poc_clustering_map.model.MyItem;
import adneom.poc_clustering_map.utils.Utils;

/**
 * Created by gtshilombowanticale on 10-07-17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private boolean isSet = true;
    private GoogleMap googleMap;

    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapFrg);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // For showing a move to my location button
        /*if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION}, Utils.requestResult);
            isSet = false;
        }else{
            // For showing a move to my location button
            googleMap.setMyLocationEnabled(true);
            async();
        }*/
        setUpClusterer();
    }

    public void async() {
        if (!isSet &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // For showing a move to my location button
            googleMap.setMyLocationEnabled(true);
        }
        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(50.8894317,4.3243892);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Log.i("Test","here");
    }

    private void setUpClusterer() {
        // Position the map.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        mClusterManager = new ClusterManager<MyItem>(getActivity(), googleMap);

        //custom render
        mClusterManager.setRenderer(new TotalRender(getActivity(),googleMap,mClusterManager));


        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {

            double offset = i / 60d;

            // Set the lat/long coordinates for the marker.
            lat = lat + offset;
            lng = lng + offset;
               //MyItem offsetItem = new MyItem(lat, lng);

            // Set the title and snippet strings.
            String title = "This is the title haha";
            String snippet = "and this is the snippet. :-)";

            // Create a cluster item for the marker and set the title and snippet using the constructor.
            MyItem infoWindowItem = new MyItem(lat, lng, title, snippet);

            //mClusterManager.addItem(offsetItem);
            mClusterManager.addItem(infoWindowItem);
        }
    }
}
