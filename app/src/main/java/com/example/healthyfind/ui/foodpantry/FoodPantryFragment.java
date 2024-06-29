package com.example.healthyfind.ui.foodpantry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthyfind.databinding.FragmentFoodPantryBinding;

public class FoodPantryFragment extends Fragment {

    private FragmentFoodPantryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FoodPantryViewModel foodPantryViewModel = new ViewModelProvider(this).get(FoodPantryViewModel.class);

        binding = FragmentFoodPantryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ImageView imageView = binding.imageView;
        final TextView textView = binding.headerTxt;

        Button webButton = binding.getRecipesBTN;
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opening link in app web browser
                Uri uri = Uri.parse("https://valenciacollege.edu/resources/grants/vcentials.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}