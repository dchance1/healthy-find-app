package com.example.healthyfind;

public class RecipeModel {
    String recipeName;
    String recipeAbbr;
    String recipeSmall;
    String imageUrl;

    public RecipeModel(String recipeName, String recipeAbbr, String recipeSmall, String imageUrl) {
        this.recipeName = recipeName;
        this.recipeAbbr = recipeAbbr;
        this.recipeSmall = recipeSmall;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeAbbr() {
        return recipeAbbr;
    }

    public String getRecipeSmall() {
        return recipeSmall;
    }

}
