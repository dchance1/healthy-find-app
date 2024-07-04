package com.example.healthyfind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.healthyfind.ui.home.HomeFragment;
import com.example.healthyfind.ui.recipes.RecipesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.healthyfind.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    private ActivityMainBinding binding;
    private SharedViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recipes, R.id.navigation_settings, R.id.navigation_maps, R.id.navigation_food_pantry)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationBarView barView = (NavigationBarView) binding.navView;
        Spinner spinner = findViewById(R.id.spinnerChoice);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();Bundle result = new Bundle();
               result.putString("spinner_data",item);
                RecipesFragment fragment = new RecipesFragment();
                fragment.setArguments(result);
               // viewModel.setData(item);


                getSupportFragmentManager().beginTransaction().replace(R.id.navigation_recipes,new HomeFragment()).commit();
                //getParentFragmentManager().setFragmentResult("data_pulled",result);

            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    // Method to check if state is saved
    public boolean isStateSaved() {
        return getSupportFragmentManager().isStateSaved();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

