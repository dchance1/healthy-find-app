package com.example.healthyfind.ui.recipes;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyfind.R;
import com.example.healthyfind.RecipeModel;
import com.example.healthyfind.Recipe_RecyclerViewAdapter;
import com.example.healthyfind.databinding.FragmentRecipesBinding;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class RecipesFragment extends Fragment {

    private FragmentRecipesBinding binding;
    ArrayList<RecipeModel> recipeModels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipesViewModel recipesViewModel =
                new ViewModelProvider(this).get(RecipesViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.myRecyclerView);

        setUpRecipeModels();

        Recipe_RecyclerViewAdapter adapter = new Recipe_RecyclerViewAdapter(root.getContext(),
                recipeModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        return root;
    }

    private void setUpRecipeModels() {

        //Getting resources from strings.xml
        String[] recipeNames = getResources().getStringArray(R.array.recipe_full_txt);
        String[] recipeAbbrs = getResources().getStringArray(R.array.recipe_three_letter_txt);
        String[] recipesmall = getResources().getStringArray(R.array.recipe_one_letter_txt);
        String[] recipeImages = getResources().getStringArray(R.array.recipe_image_url);

        //Adding resources to the models to array
        for (int i = 0; i < recipeNames.length; i++) {
            recipeModels.add(new RecipeModel(recipeNames[i], recipeAbbrs[i], recipesmall[i],
                    recipeImages[i]));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}