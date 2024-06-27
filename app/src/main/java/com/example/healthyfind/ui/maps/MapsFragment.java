package com.example.healthyfind.ui.maps;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthyfind.MainActivity;
import com.example.healthyfind.R;
import com.example.healthyfind.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapsBinding binding;
    private GoogleMap myMap;
    private SearchView mapSearchView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapsViewModel mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapSearchView = mapSearchView.findViewById(R.id.mapSearch);

        //Getting the Map fragment by id
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        //final TextView textView = binding.textMaps;
        //mapsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;



        //setting the text listener here, this is where I want to substitute for an auto-search feature.
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;
                if(location != null) {
                    //issues with this line
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                    try{
                        addressList =geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //getting address below if we want multiple we can add a for loop here
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    //took hard code from onMapReady method and applied to search box
                    myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //Assigning google map to GoogleMap obj
        myMap = googleMap;

        //Latitude and longitude
        //LatLng orlando = new LatLng(28.538336, -81.379234);

        //creating marker and to map point sand setting a comfortable zoom
        //myMap.addMarker(new MarkerOptions().position(orlando).title("Orlando"));
        //myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(orlando, 12.0f));

        //None of the commented out code is needed due to the on query text submit method addition.

    }
}