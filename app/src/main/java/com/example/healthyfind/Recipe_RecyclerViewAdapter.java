package com.example.healthyfind;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

// TODO: 6/25/24 Need to refactor code and remove commented code. 
public class Recipe_RecyclerViewAdapter extends RecyclerView.Adapter<Recipe_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeModel> recipeModels;
    Drawable dImg = LoadImageFromWebOperations(
            "https://www.themealdb.com/images/media/meals/sbx7n71587673021.jpg");

    String img = "https://pinchofyum.com/wp-content/uploads/Gochujang-Burgers-9-1227x1536.jpg";

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public Recipe_RecyclerViewAdapter(Context context, ArrayList<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Making layout for rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        CardView cardView = (CardView) view.findViewById(R.id.cardView1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "4455";
                TextView textView = (TextView) v.findViewById(R.id.textView1);
                text = text + textView.getText().toString();

                System.out.println(text);
            }
        });

//        TextView textView = (android.widget.TextView) view.findViewById(R.id.textView1);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = "1234";
//                TextView tView = (TextView) v.findViewById(R.id.textView1);
//                text = text + tView.getText().toString();
//                System.out.println(text);
//            }
//        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Giving values to the row views as they appear on screen
        String recipeDescription = recipeModels.get(position).getRecipeName();
        int len = recipeDescription.length();
        String shortDescription = recipeDescription;
        if (len > 100) {
            len = 105;
            recipeDescription = recipeDescription.substring(0, len) + ".....";
        }

        holder.textViewName.setText(recipeDescription);
        holder.textViewSmall.setText(recipeModels.get(position).getRecipeSmall());
        String info = "Some information related to the recipe can go here. ";
        holder.textViewAbbr.setText(info);
        // Will use the below code to load image url's
        // need to add Drawable as a resource instead of int
        Glide.with(this.context).load(recipeModels.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //how may items to display

        return recipeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //Getting views from recycler view file
        ImageView imageView;
        TextView textViewName;
        TextView textViewAbbr;
        TextView textViewSmall;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textView1);
            textViewAbbr = itemView.findViewById(R.id.textView2);
            textViewSmall = itemView.findViewById(R.id.textView3);
            cardView = itemView.findViewById(R.id.cardView1);
        }
    }
}
