package com.example.healthyfind.ui.maps;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import com.example.healthyfind.R;
import com.example.healthyfind.databinding.FragmentMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapsBinding binding;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(binding.getRoot().getContext());
        //fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocation();

        //Getting the Map fragment by id
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //Assigning google map to GoogleMap obj

        //Latitude and longitude
        LatLng orlando = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        //creating marker and to map point sand setting a comfortable zoom
        googleMap.addMarker(new MarkerOptions().position(orlando).title("Current Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(orlando,12));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    private void getCurrentLocation(){

        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , REQUEST_CODE);
            return;
        }
        Task<Location> task= fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location !=null){
                currentLocation= location;
                Toast.makeText(requireContext().getApplicationContext(), (int) currentLocation.getLatitude(),Toast.LENGTH_LONG)
                        .show();
                SupportMapFragment supportMapFragment=(SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);
                assert supportMapFragment!=null;
                supportMapFragment.getMapAsync(MapsFragment.this);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (REQUEST_CODE){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    getCurrentLocation();
                }
                break;
        }
    }
}