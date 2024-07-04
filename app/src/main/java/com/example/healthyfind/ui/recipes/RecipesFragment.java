package com.example.healthyfind.ui.recipes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthyfind.MainActivity;
import com.example.healthyfind.R;
import com.example.healthyfind.SharedViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipesFragment extends Fragment {

    private SharedViewModel viewModel;

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private List<Meal> mealList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        String data = String.valueOf(viewModel.getData().getValue());




        searchInput = view.findViewById(R.id.searchInput);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        if(!data.isEmpty()){
            searchInput.setText(data);



        }else {

        }


        mealList = new ArrayList<>();

        mealAdapter = new MealAdapter(mealList);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getRootView().getContext(), 2));
        recyclerView.setAdapter(mealAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ;


                String searchQuery = searchInput.getText().toString().trim();
                if (!searchQuery.isEmpty()) {
                    callTheApi(searchQuery);
                } else {
                    Toast.makeText(getContext(), "Please enter search term!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchButton.performClick();


        return view;
    }

    private void callTheApi(String searchWord) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + searchWord;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mealList.clear();
                            JSONArray mealsArray = response.getJSONArray("meals");
                            for (int i = 0; i < mealsArray.length(); i++) {

                                JSONObject mealObject = mealsArray.getJSONObject(i);

                                String mealName = mealObject.getString("strMeal");
                                String category = mealObject.getString("strCategory");
                                String instructions = mealObject.getString("strInstructions");
                                String mealTags = mealObject.getString("strTags");
                                String mealImageURL = mealObject.getString("strMealThumb");
                                String mealArea = mealObject.getString("strArea") ;
                                List<String> ingredients = Meal.extractApiIngredients(mealObject);

                                Meal meal = new Meal(mealName, category, instructions, mealImageURL, mealTags, mealArea, ingredients);
                                mealList.add(meal);
                            }
                            mealAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Recipe Not Found!.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error while fetching the data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(jsonObjectRequest);
    }

    public static class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

        private List<Meal> mealList;

        public MealAdapter(List<Meal> mealList) {
            this.mealList = mealList;
        }

        @NonNull
        @Override
        public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
            return new MealViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
            Meal meal = mealList.get(position);
            holder.bind(meal);
        }

        @Override
        public int getItemCount() {
            return mealList.size();
        }

        public static class MealViewHolder extends RecyclerView.ViewHolder {

            private final Context context;
            private final ImageView mealImageView;
            private final TextView categoryTextView;
            //private final TextView tagsTextView;
            private final TextView mealNameButton;
            private final TextView areaTextView;

            public MealViewHolder(@NonNull View itemView) {
                super(itemView);

                context = itemView.getContext();
                mealImageView = itemView.findViewById(R.id.mealImageView);
                categoryTextView = itemView.findViewById(R.id.categoryTextView);
                //tagsTextView = itemView.findViewById(R.id.tagsTextView);
                mealNameButton = itemView.findViewById(R.id.mealNameButton);
                areaTextView = itemView.findViewById(R.id.areaTextView);
            }

            public void bind(Meal meal) {
                String mealTags = meal.getMealTags();
                Picasso.get().load(meal.getMealImageURL()).into(mealImageView);
                categoryTextView.setText("Category: " + meal.getCategory());


                //Commenting out for now, tags generally don't show up
//                if (mealTags.equalsIgnoreCase("null")) {
//                    tagsTextView.setText("Tags: No Tags Available");
//                } else {
//                    tagsTextView.setText("Tags: " + mealTags);
//                }

                areaTextView.setText("Cuisine: " + meal.getMealArea());
                mealNameButton.setText(meal.getMealName());

                //TODO - May need to rework this click listener to display the recipe details
                //take itemView and add Click Listener to show recipe details fragment
                itemView.setOnClickListener(view -> {
                    if (context instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) context;

                        /*
                        check the state of the activity. Was getting an error about the state when
                        showing details. This is to help stop a state if already running
                        Have fragment replacing the recipes layout because when I try to
                        display the recipe details, an state issue occurs.
                         */
                        if (!mainActivity.isStateSaved()) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("meal", meal);
                            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                            recipeDetailFragment.setArguments(bundle);

                            //FIXME
                            // - "Find" search button shows in the recipe details
                            /*
                            recipe details fragment replaces the recipes fragment. I went with this
                            because I had issues displaying the recipe details. The error showed
                            something about a "state" activity already active. Also, when I try to
                            click back on the Recipes page, all the recipes go away and would need
                            to search again.

                            I currently have the recipes details fragment replacing
                            the "recipes" fragment and added a "close details" button. This allows
                            for the recipes to stay without needing to do another search.
                            */
                            mainActivity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.recipes, recipeDetailFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
            }
        }
    }

    public static class Meal implements java.io.Serializable {
        private final String mealName;
        private final String category;
        private final String instructions;
        private final String mealImageURL;
        private final String mealTags;
        private final String mealArea;
        private final List<String> ingredients;

        public Meal(String mealName, String category, String instructions, String mealImageURL, String mealTags, String mealArea, List<String> ingredients) {
            this.mealName = mealName;
            this.category = category;
            this.instructions = instructions;
            this.mealImageURL = mealImageURL;
            this.mealTags = mealTags;
            this.mealArea = mealArea;
            this.ingredients = ingredients;

        }

        public String getMealName() {
            return mealName;
        }

        public String getCategory() {
            return category;
        }

        public String getInstructions() {
            return instructions;
        }

        public String getMealImageURL() {
            return mealImageURL;
        }

        public String getMealTags() {
            return mealTags;
        }

        public String getMealArea() {
            return mealArea;
        }
        public List<String> getIngredients() {
            return ingredients;
        }

        // API returns 20 ingredient objects for each meal. Some of these 20 objects dont even contain any values.
        // this is a function to go through all 20 strIngredients and adds them to an ArrayList if they contain values.
        public static List<String> extractApiIngredients(JSONObject mealObject) throws JSONException {
            List<String> ingredients = new ArrayList<>();

            for (int i = 0 ; i<20 ; i++){

                String ingredient = mealObject.optString("strIngredient"+i);

                // checking if any of the ingredient objects from the API is empty
                // and adding only the ones that contain values to our meal Array.

                if(!ingredient.equals("null") && !ingredient.equals("")){
                    ingredients.add(ingredient) ;
                }
            }
            return ingredients;
        }
    }



}
