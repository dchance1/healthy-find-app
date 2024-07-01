package com.example.healthyfind.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.healthyfind.R;

public class RecipeDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ImageView recipeImage = view.findViewById(R.id.recipe_image);
        TextView recipeName = view.findViewById(R.id.recipe_name);
        TextView recipeDescription = view.findViewById(R.id.recipe_description);
        TextView recipeIngredients = view.findViewById(R.id.recipe_ingredients);
        TextView recipeSteps = view.findViewById(R.id.recipe_steps);
        TextView recipeTags = view.findViewById(R.id.recipe_tags);
        Button closeButton = view.findViewById(R.id.close_button);


        if (getArguments() != null) {
            RecipesFragment.Meal meal = (RecipesFragment.Meal) getArguments().getSerializable("meal");
            String mealTags = meal.getMealTags();
            if (meal != null) {
                Glide.with(this).load(meal.getMealImageURL()).into(recipeImage);
                recipeName.setText(meal.getMealName());
                recipeDescription.setText("Category: "+meal.getCategory()); // or any other description you have


                if (mealTags.equalsIgnoreCase("null")) {
                    recipeTags.setText("Tags: No Tags Available");
                } else {
                    recipeTags.setText("Tags: " + mealTags);
                }

                // Appending the ingredients
                StringBuilder ingredientsText = new StringBuilder("Ingredients:\n" );

                for(String singleIngredient: meal.getIngredients()){
                    ingredientsText.append(singleIngredient).append("\n");
                }
                // and then displayed here
                recipeIngredients.setText(ingredientsText.toString());

                recipeSteps.setText("Steps:\n "+meal.getInstructions());
            }
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .remove(RecipeDetailFragment.this)
                        .commit();
            }
        });

        return view;
    }
}
