package com.example.healthyfind.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyfind.R;
import com.example.healthyfind.RecipeModel;
import com.example.healthyfind.Recipe_RecyclerViewAdapter;
import com.example.healthyfind.databinding.FragmentRecipesBinding;

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
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        return root;
    }

    private void setUpRecipeModels() {

        //Getting resources from strings.xml
        String[] recipeInfo1 = getResources().getStringArray(R.array.recipe_info_1);
        String[] recipeInfo2 = getResources().getStringArray(R.array.recipe_info_2);
        String[] recipeName = getResources().getStringArray(R.array.recipe_name);
        String[] recipeImages = getResources().getStringArray(R.array.recipe_image_url);

        //Adding resources to the models to array
        for (int i = 0; i < recipeInfo1.length; i++) {
            recipeModels.add(new RecipeModel(recipeName[i], recipeInfo1[i], recipeInfo2[i],
                    recipeImages[i]));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}