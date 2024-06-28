package com.example.healthyfind;

public class RecipeModel {
    String recipeInfo1;
    String recipeInfo2;
    String recipeName;
    String imageUrl;

    public RecipeModel(String recipeName, String recipeInfo1, String recipeInfo2, String imageUrl) {
        this.recipeInfo1 = recipeInfo1;
        this.recipeInfo2 = recipeInfo2;
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRecipeInfo1() {
        return recipeInfo1;
    }

    public String getRecipeInfo2() {
        return recipeInfo2;
    }

    public String getRecipeName() {
        return recipeName;
    }

}
